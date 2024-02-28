package xlang.parser.expression;

import com.google.common.base.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import xlang.parser.expression.value.Value;

@AllArgsConstructor
@Data
public class Variable implements Expression {
    private final String name;
    private final Function<String, Value<?>> getVariableValue;

    @Override
    public Value<?> evaluate() {
        return getVariableValue.apply(name);
    }

}
