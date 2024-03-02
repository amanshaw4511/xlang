package xlang.lexer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    Boolean,
    Integer,
    String,
    Variable,
    Operator,
    Keyword,
    BlockStart,
    BlockEnd;
}
