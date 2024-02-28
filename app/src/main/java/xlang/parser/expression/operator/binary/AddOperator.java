package xlang.parser.expression.operator.binary;

import xlang.parser.expression.Expression;
import xlang.parser.expression.value.IntegerValue;
import xlang.parser.expression.value.Value;

public class AddOperator extends BinaryOperator {

    public AddOperator(Expression leftArg, Expression rightArg) {
        super(leftArg, rightArg);
    }

    @Override
    public Value<?> evaluate() {
        var leftValue = (IntegerValue) this.getLeftArg().evaluate();
        var rightValue = (IntegerValue) this.getRightArg().evaluate();
        return new IntegerValue(leftValue.getValue() + rightValue.getValue());
    }

}
