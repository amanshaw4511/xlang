package xlang.parser.statement;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class CompositeStatement implements Statement {
    private final List<Statement> statements = new ArrayList<>();

    public void addStatement(@NonNull Statement statement) {
        this.statements.add(statement);
    }

    @Override
    public void execute() {
        statements.forEach(it -> it.execute());
    }
}
