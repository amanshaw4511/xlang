package xlang.memory;

import lombok.extern.slf4j.Slf4j;
import xlang.parser.expression.value.Value;

import java.util.ArrayDeque;
import java.util.Deque;

@Slf4j
public class MemoryContext {
    private MemoryContext() {}
    private static final Deque<MemoryScope> scopes = new ArrayDeque<>();

    static MemoryScope currScope() {
        return scopes.peek();
    }

    public static void newScope() {
        var newScope = new MemoryScope(currScope());
        log.debug("Added new SCOPE {}", newScope.hashCode());
        scopes.push(newScope);
    }

    public static void endScope() {
        var removedScope = scopes.pop();
        log.debug("Removed SCOPE {}", removedScope.hashCode());
    }

    public static Value<?> get(String variableName) {
        return currScope().get(variableName);
    }

    public static void set(String variableName, Value<?> value) {
        currScope().set(variableName, value);
    }

    public static void add(String variableName, Value<?> value) {
        currScope().add(variableName, value);
    }
}
