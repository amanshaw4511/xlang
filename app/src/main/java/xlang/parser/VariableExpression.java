package xlang.parser;

import com.google.common.base.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.value.Value;

@AllArgsConstructor(staticName = "of")
@Getter
public class VariableExpression implements Expression {
    private final String name;
    private final Function<String, Value<?>> getVariableValue;

    @Override
    public Value<?> evaluate() {
        return getVariableValue.apply(name);
    }

}
