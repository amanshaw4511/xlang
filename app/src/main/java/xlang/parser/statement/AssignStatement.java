package xlang.parser.statement;

import java.util.function.BiConsumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import xlang.parser.expression.Expression;
import xlang.parser.expression.value.Value;

@AllArgsConstructor
@Data
public class AssignStatement implements Statement {
    private final String variableName;
    private final Expression expression;

    private final BiConsumer<String, Value<?>> variableSetter;

    @Override
    public void execute() {
        variableSetter.accept(variableName, expression.evaluate());
    }

}
