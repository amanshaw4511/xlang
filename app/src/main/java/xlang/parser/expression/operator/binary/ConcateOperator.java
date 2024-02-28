package xlang.parser.expression.operator.binary;

import xlang.parser.expression.Expression;
import xlang.parser.expression.value.StringValue;
import xlang.parser.expression.value.Value;

public class ConcateOperator extends BinaryOperator {

    public ConcateOperator(Expression leftArg, Expression rightArg) {
        super(leftArg, rightArg);
    }

    @Override
    public Value<?> evaluate() {
        var leftValue = (StringValue) this.getLeftArg().evaluate();
        var rightValue = (StringValue) this.getRightArg().evaluate();
        return new StringValue(leftValue.getValue() + rightValue.getValue());
    }

}
