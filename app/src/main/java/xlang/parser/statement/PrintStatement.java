package xlang.parser.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.Expression;
import xlang.parser.value.Value;

@AllArgsConstructor
@Getter
public class PrintStatement implements Statement {
    private final Expression expression;

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        System.out.println(value);
    }

}
