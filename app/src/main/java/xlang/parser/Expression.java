package xlang.parser;

import xlang.parser.value.Value;

public interface Expression {
    Value<?> evaluate();
}
