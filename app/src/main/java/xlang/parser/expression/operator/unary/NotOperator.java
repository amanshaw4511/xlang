package xlang.parser.expression.operator.unary;

import xlang.parser.expression.Expression;
import xlang.parser.expression.value.BooleanValue;
import xlang.parser.expression.value.Value;

public class NotOperator extends UnaryOperator {

    public NotOperator(Expression arg) {
        super(arg);
    }

    @Override
    public Value<?> evaluate() {
        var value = (BooleanValue) this.getArg().evaluate();
        return new BooleanValue(!value.getValue());
    }

}
