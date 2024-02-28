package xlang.parser.expression.operator.unary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.expression.Expression;
import xlang.parser.expression.operator.Operator;

@AllArgsConstructor
@Getter
public abstract class UnaryOperator implements Operator {
    private final Expression arg;

}
