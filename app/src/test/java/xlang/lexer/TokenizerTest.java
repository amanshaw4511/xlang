package xlang.lexer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TokenizerTest {

    @Test
    void ifStatement() {
        var code = """
                if true {
                    print "true"
                }
                    """;
        var tokens = Tokenizer.tokenize(code);
        assertThat(tokens)
                .isEqualTo(List.of(
                        new Token(TokenType.Keyword, "if"),
                        new Token(TokenType.Boolean, "true"),
                        new Token(TokenType.BlockStart, "{"),
                        new Token(TokenType.Keyword, "print"),
                        new Token(TokenType.String, "true"),
                        new Token(TokenType.BlockEnd, "}")

                ));
    }
}
