package xlang.parser;

import java.util.List;

import xlang.lexer.Token;
import xlang.parser.statement.AssignStatement;
import xlang.parser.statement.CompositeStatement;
import xlang.parser.statement.Statement;

public class StatementParser {
    private int pos;
    private final int len;
    private final List<Token> tokens;

    public StatementParser(List<Token> tokens) {
        this.pos = 0;
        this.len = tokens.size();
        this.tokens = tokens;
    }

    private Token next() {
        return tokens.get(pos++);
    }

    private boolean hasNext() {
        return pos != len;
    }

    private Token peek() {
        return tokens.get(pos + 1);
    }

    private Token prev() {
        return tokens.get(pos - 1);
    }

    private Token curr() {
        return tokens.get(pos);
    }

    private Expression parseExpression() {
        var token = curr();

        return null;
    }

    private Statement parseNextStatement() {
        var token = next();
        if (token.value().equals("var")) {
            var varName = next();
            next(); // assignment operator
            var varExpression = parseExpression();

            return new AssignStatement(
                    varName.value(),
                    varExpression,
                    null);

        }
        throw new RuntimeException();
    }

    public Statement parse(List<Token> tokens) {
        var compositeStatement = new CompositeStatement();
        while (hasNext()) {
            compositeStatement.addStatement(parseNextStatement());
        }
        return compositeStatement;
    }

}
