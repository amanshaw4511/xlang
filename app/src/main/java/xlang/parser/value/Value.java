package xlang.parser.value;

import lombok.AllArgsConstructor;
import lombok.ToString;
import xlang.parser.Expression;

@ToString
@AllArgsConstructor
public class Value<T extends Comparable<T>> implements Expression {
    private final T value;

    @Override
    public Value<?> evaluate() {
        return this;
    }
}
