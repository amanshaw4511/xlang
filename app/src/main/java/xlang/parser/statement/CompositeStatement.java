package xlang.parser.statement;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class CompositeStatement implements Statement {
    private final List<Statement> statements = new ArrayList<>();

    public void addStatement(@NonNull Statement statement) {
        this.statements.add(statement);
    }

    @Override
    public void execute() {
        statements.forEach(Statement::execute);
    }
}
