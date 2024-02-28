package xlang.parser.statement;

import java.util.function.BiConsumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xlang.parser.Expression;
import xlang.parser.value.Value;

@AllArgsConstructor
@Getter
public class AssignStatement implements Statement {
    private final String variableName;
    private final Expression expression;

    private final BiConsumer<String, Value<?>> variableSetter;

    @Override
    public void execute() {
        variableSetter.accept(variableName, expression.evaluate());
    }

}
