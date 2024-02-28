package xlang.parser.expression.operator.binary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.expression.Expression;
import xlang.parser.expression.operator.Operator;

@AllArgsConstructor
@Getter
public abstract class BinaryOperator implements Operator {
    private final Expression leftArg;
    private final Expression rightArg;
}
