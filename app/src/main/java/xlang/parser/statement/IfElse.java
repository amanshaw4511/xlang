package xlang.parser.statement;

import lombok.Data;
import lombok.NonNull;
import xlang.memory.MemoryContext;
import xlang.parser.expression.Expression;
import xlang.parser.expression.value.BooleanValue;

@Data
public class IfElse implements Statement {
    private final Expression condition;
    private final Statement ifBody;
    private final Statement elseBody;

    private IfElse(Expression condition, Statement ifBody, Statement elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    public static IfElse of(@NonNull Expression condition, @NonNull Statement ifBody,
            @NonNull Statement elseBody) {
        return new IfElse(condition, ifBody, elseBody);
    }

    public static IfElse of(@NonNull Expression condition, @NonNull Statement ifBody) {
        return new IfElse(condition, ifBody, new NullStatement());
    }

    @Override
    public void execute() {
        var isTrue = (BooleanValue) condition.evaluate();

        if (isTrue.getValue()) {
            MemoryContext.newScope();
            ifBody.execute();
            MemoryContext.endScope();
        } else {
            MemoryContext.newScope();
            elseBody.execute();
            MemoryContext.endScope();
        }
    }

}
