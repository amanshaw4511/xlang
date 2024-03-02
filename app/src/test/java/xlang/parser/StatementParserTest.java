package xlang.parser;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import xlang.lexer.Tokenizer;
import xlang.parser.expression.value.Value;

public class StatementParserTest {
    Map<String, Value<?>> storage = new HashMap<>();

    @SneakyThrows
    String run(String code) {
        var parser = new StatementParser(Tokenizer.tokenize(code), storage);
        var ast = parser.parse();

        return tapSystemOut(ast::execute).strip();
    }

    @Nested
    class IfElseStatement {

        @Test
        void testIfStatement() {
            var code = """
                    if true {
                        print "it is true"
                    }
                        """;

            assertThat(run(code))
                    .isEqualTo("it is true");

        }

        @Test
        void testIfElseSatementWhenTrue() {
            var code = """
                    if true {
                        print "it is true"
                    } else {
                        print "it is false"
                    }
                        """;
            assertThat(run(code))
                    .isEqualTo("it is true");
        }

        @Test
        void testIfElseSatementWhenFalse() {
            var code = """
                    if false {
                        print "it is true"
                    } else {
                        print "it is false"
                    }
                        """;
            assertThat(run(code))
                    .isEqualTo("it is false");
        }

    }
}
