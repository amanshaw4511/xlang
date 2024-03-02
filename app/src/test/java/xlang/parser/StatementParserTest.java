package xlang.parser;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;
import xlang.Xlang;
import xlang.lexer.Tokenizer;
import xlang.memory.MemoryContext;
import xlang.parser.expression.value.Value;

public class StatementParserTest {
    Map<String, Value<?>> storage = new HashMap<>();

    @SneakyThrows
    String run(String code) {
        var xlang = new Xlang();

        var outputCaptor = new ByteArrayOutputStream();
        var printStream = new PrintStream(outputCaptor);

        xlang.execute(code, printStream);
        return outputCaptor.toString().strip();
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
        void testIfElseStatementWhenTrue() {
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
        void testIfElseStatementWhenFalse() {
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


    @Nested
    class MemoryScopeTest {
        @Test
        void canAccessParentScopeVariable() {
            var code = """
                    var x = "it is true"
                    if true {
                        print x
                    } else {
                        print "it is false"
                    }
                        """;
            assertThat(run(code))
                    .isEqualTo("it is true");
        }

        @Test
        void canModifyParentScopeVariable() {
            var code = """
                    var x = "it is true"
                    if true {
                        x = "modified to false"
                    } else {
                        print "it is false"
                    }
                    print x
                        """;

            assertThat(run(code))
                    .isEqualTo("modified to false");
        }
        @Test
        void cannotAccessChildScopeVariable() {
            var code = """
                    if true {
                        var y = 4
                    } else {
                        print "it is false"
                    }
                    print y
                        """;

            assertThatThrownBy(() -> run(code))
                    .isInstanceOf(SyntaxError.class);
        }

        @Test
        void cannotModifyChildScopeVariable() {
            var code = """
                    if true {
                        var y = 4
                    } else {
                        print "it is false"
                    }
                    y = 2
                    print y
                        """;

            assertThatThrownBy(() -> run(code))
                    .isInstanceOf(SyntaxError.class);
        }

    }


}
