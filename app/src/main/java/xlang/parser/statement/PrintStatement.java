package xlang.parser.statement;

import xlang.parser.expression.Expression;
import xlang.parser.expression.value.Value;

import java.io.PrintStream;

public record PrintStatement(Expression expression, PrintStream outputStream) implements Statement {
    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        outputStream.println(value.getValue());
    }

}
