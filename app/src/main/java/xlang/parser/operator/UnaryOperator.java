package xlang.parser.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.Expression;
import xlang.parser.value.Value;

@AllArgsConstructor
@Getter
public abstract class UnaryOperator implements OperatorExpression {
    private final Expression arg;

    public abstract Value<?> calc();

}
