package xlang.parser;

import lombok.extern.slf4j.Slf4j;
import xlang.lexer.Token;
import xlang.lexer.TokenType;
import xlang.parser.expression.Expression;
import xlang.parser.expression.Variable;
import xlang.parser.expression.operator.binary.AddOperator;
import xlang.parser.expression.operator.binary.ConcatOperator;
import xlang.parser.expression.operator.unary.NotOperator;
import xlang.parser.expression.value.BooleanValue;
import xlang.parser.expression.value.IntegerValue;
import xlang.parser.expression.value.StringValue;
import xlang.parser.expression.value.Value;
import xlang.parser.statement.*;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

@Slf4j
public class StatementParser {
    private int pos;
    private final int len;
    private final List<Token> tokens;
    private final PrintStream outputStream;

    public StatementParser(List<Token> tokens) {
        this(tokens, System.out);
    }
    public StatementParser(List<Token> tokens, PrintStream outputStream) {
        this.pos = 0;
        this.len = tokens.size();
        this.tokens = tokens;
        this.outputStream = outputStream;
    }

    private Token next() {
        return tokens.get(pos++);
    }

    private boolean hasNext() {
        return pos != len;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token curr() {
        return tokens.get(pos - 1);
    }

    private Expression parseNextExpression() {
        var token = next();
        return switch (token.type()) {
            case Boolean -> new BooleanValue(Boolean.valueOf(token.value()));
            case Integer -> new IntegerValue(Integer.valueOf(token.value()));
            case String -> new StringValue(token.value());
            case Operator -> parseNextOperator();
            case Variable -> new Variable(token.value());
            case Keyword -> throw new UnsupportedOperationException(STR."Unimplemented case: \{token.type()}");
            default -> throw new IllegalArgumentException(STR."Unexpected value: \{token.type()}");
        };
    }

    private Expression parseNextOperator() {
        var token = curr();
        return switch (token.value()) {
            case "not" -> new NotOperator(parseNextExpression());
            case "+" -> new AddOperator(parseNextExpression(), parseNextExpression());
            case "++" -> new ConcatOperator(parseNextExpression(), parseNextExpression());
            default -> throw new IllegalArgumentException(STR."invlid oeprator \{token.value()}");
        };
    }

    private Statement parseNextStatement() {
        var token = next();
        // variable declaration
        if (token.value().equals("var")) {
            var variableName = next();
            expectNextToken("="); // assignment operator
            var expression = parseNextExpression();

            return new AssignStatement(
                    variableName.value(),
                    expression,
                    false
            );
        }

        // variable assignment
        if (token.type().equals(TokenType.Variable)) {
            var variableName = token;
            next(); // assignment operator
            var expression = parseNextExpression();

            return new AssignStatement(
                    variableName.value(),
                    expression,
                    true
            );
        }

        if (token.value().equals("print")) {
            return new PrintStatement(parseNextExpression(), outputStream);
        }

        if (token.value().equals("if")) {
            return parseIfElseStatement();
        }
        throw new SyntaxError(STR."unexpected token \{token}");
    }

    public Statement parseIfElseStatement() {
        var condition = parseNextExpression();

        expectNextToken("{");

        var ifBody = new CompositeStatement();
        while (!peek().value().equals("}")) {
            ifBody.addStatement(parseNextStatement());
        }

        expectNextToken("}");

        if (!hasNext() || !peek().value().equals("else")) {
            return IfElse.of(condition, ifBody);
        }

        expectNextToken("else");
        expectNextToken("{");

        var elseBody = new CompositeStatement();
        while (!peek().value().equals("}")) {
            elseBody.addStatement(parseNextStatement());
        }

        expectNextToken("}");
        return IfElse.of(condition, ifBody, elseBody);
    }

    public void expectNextToken(String tokenValue) {
        var token = next();
        if (!token.value().equals(tokenValue)) {
            throw new SyntaxError(STR."expected token : \{tokenValue}");
        }
    }

    public Statement parse() {
        var compositeStatement = new CompositeStatement();
        while (hasNext()) {
            var statement = parseNextStatement();
            log.debug("parsed stmt : {}", statement);
            compositeStatement.addStatement(statement);
        }
        return compositeStatement;
    }

}
