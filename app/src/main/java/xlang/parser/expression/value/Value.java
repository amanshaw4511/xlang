package xlang.parser.expression.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import xlang.parser.expression.Expression;

@ToString
@AllArgsConstructor
@Getter
public abstract class Value<T extends Comparable<T>> implements Expression {
    private final T value;

    @Override
    public Value<?> evaluate() {
        return this;
    }
}
