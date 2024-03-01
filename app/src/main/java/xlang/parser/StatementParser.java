package xlang.parser;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import xlang.lexer.Token;
import xlang.lexer.TokenType;
import xlang.parser.expression.Expression;
import xlang.parser.expression.Variable;
import xlang.parser.expression.operator.binary.AddOperator;
import xlang.parser.expression.operator.binary.ConcateOperator;
import xlang.parser.expression.operator.unary.NotOperator;
import xlang.parser.expression.value.BooleanValue;
import xlang.parser.expression.value.IntegerValue;
import xlang.parser.expression.value.StringValue;
import xlang.parser.expression.value.Value;
import xlang.parser.statement.AssignStatement;
import xlang.parser.statement.CompositeStatement;
import xlang.parser.statement.PrintStatement;
import xlang.parser.statement.Statement;

@Slf4j
public class StatementParser {
    private int pos;
    private final int len;
    private final List<Token> tokens;

    private final Map<String, Value<?>> store;

    public StatementParser(List<Token> tokens, Map<String, Value<?>> store) {
        this.pos = 0;
        this.len = tokens.size();
        this.tokens = tokens;
        this.store = store;
    }

    private Token next() {
        return tokens.get(pos++);
    }

    private boolean hasNext() {
        return pos != len;
    }

    // private Token peek() {
    // return tokens.get(pos + 1);
    // }

    // private Token prev() {
    // return tokens.get(pos - 2);
    // }

    private Token curr() {
        return tokens.get(pos - 1);
    }

    private Expression parseNextExpression() {
        var token = next();
        return switch (token.type()) {
            case Boolean -> new BooleanValue(Boolean.valueOf(token.value()));
            case Integer -> new IntegerValue(Integer.valueOf(token.value()));
            case String -> new StringValue(token.value());
            case Operator -> parseNextOpeartor();
            case Variable -> new Variable(token.value(), store::get);
            case Keyword -> throw new UnsupportedOperationException("Unimplemented case: " + token.type());
            default -> throw new IllegalArgumentException("Unexpected value: " + token.type());
        };
    }

    private Expression parseNextOpeartor() {
        var token = curr();
        return switch (token.value()) {
            case "not" -> new NotOperator(parseNextExpression());
            case "+" -> new AddOperator(parseNextExpression(), parseNextExpression());
            case "++" -> new ConcateOperator(parseNextExpression(), parseNextExpression());
            default -> throw new IllegalArgumentException("invlid oeprator " + token.value());
        };
    }

    private Statement parseNextStatement() {
        var token = next();
        // variable declaration
        if (token.value().equals("var")) {
            var variableName = next();
            next(); // assignment operator
            var expression = parseNextExpression();

            return new AssignStatement(
                    variableName.value(),
                    expression,
                    store::put);

        }

        // variable assignment
        if (token.type().equals(TokenType.Variable)) {
            var variableName = token;
            next(); // assignment operator
            var expression = parseNextExpression();

            return new AssignStatement(
                    variableName.value(),
                    expression,
                    store::put);
        }

        if (token.value().equals("print")) {
            return new PrintStatement(parseNextExpression());
        }

        throw new SyntaxError("unexpected token " + token);
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
