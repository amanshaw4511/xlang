package xlang.parser.expression;

import xlang.memory.MemoryContext;
import xlang.parser.expression.value.Value;

public record Variable(String name) implements Expression {
    @Override
    public Value<?> evaluate() {
        return MemoryContext.get(name);
    }

}
