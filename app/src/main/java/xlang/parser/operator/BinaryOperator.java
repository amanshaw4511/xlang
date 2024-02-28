package xlang.parser.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.Expression;
import xlang.parser.value.Value;

@AllArgsConstructor
@Getter
public abstract class BinaryOperator implements OperatorExpression {
    private final Expression leftArg;
    private final Expression rightArg;

    public abstract Value<?> calc();
}
