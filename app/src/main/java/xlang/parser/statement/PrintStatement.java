package xlang.parser.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.expression.Expression;
import xlang.parser.expression.value.Value;

@AllArgsConstructor
@Getter
public class PrintStatement implements Statement {
    private final Expression expression;

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        System.out.println(value.getValue());
    }

}
