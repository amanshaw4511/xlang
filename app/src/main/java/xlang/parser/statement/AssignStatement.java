package xlang.parser.statement;

import xlang.memory.MemoryContext;
import xlang.parser.expression.Expression;

public record AssignStatement(String variableName, Expression expression, boolean reAssign) implements Statement {
    @Override
    public void execute() {
        if (reAssign) {
            MemoryContext.set(variableName, expression.evaluate());
        } else {
            MemoryContext.add(variableName, expression.evaluate());
        }
    }

}
