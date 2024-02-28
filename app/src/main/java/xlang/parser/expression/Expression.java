package xlang.parser.expression;

import xlang.parser.expression.value.Value;

public interface Expression {
    Value<?> evaluate();
}
