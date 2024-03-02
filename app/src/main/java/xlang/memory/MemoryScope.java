package xlang.memory;

import xlang.parser.SyntaxError;
import xlang.parser.expression.value.Value;

import java.util.HashMap;
import java.util.Map;
class MemoryScope {
    private final Map<String, Value<?>> variables;
    private final MemoryScope parent;

    public MemoryScope(MemoryScope parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public Value<?> get(String variableName) {
        var value = variables.get(variableName);

        // check if present in parent scope
        if (value == null && parent != null) {
            value = this.parent.get(variableName);
        }

        if (value != null) {
            return value;
        }

        throw new SyntaxError(STR."variable \{variableName} not found in memeory");
    }

    public void set(String variableName, Value<?> value) {
        if(variables.containsKey(variableName)){
            variables.put(variableName, value);
            return;
        }
        if (parent == null) {
            throw new SyntaxError(STR."variable \{variableName} not found in memeory");
        }
        this.parent.set(variableName, value);
    }

    public void add(String variableName, Value<?> value) {
        if (variables.containsKey(variableName)) {
            throw new SyntaxError(STR."variable \{variableName} already exist in memory");
        }
        variables.put(variableName, value);
    }

}
