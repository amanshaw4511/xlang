package xlang;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import xlang.lexer.Token;
import xlang.lexer.Tokenizer;
import xlang.parser.StatementParser;
import xlang.parser.expression.value.Value;

@Slf4j
public class Xlang {

    @SneakyThrows
    public void execute(Path sourceCodePath) {
        execute(Files.readString(sourceCodePath));
    }

    public void execute(String sourceCode) {
        List<Token> tokens = Tokenizer.tokenize(sourceCode);
        log.debug("parsed tokens : ", tokens);

        Map<String, Value<?>> store = new HashMap<>();

        StatementParser statementParser = new StatementParser(tokens, store);

        var statement = statementParser.parse();

        statement.execute();
    }

    public static void main(String[] args) {
        var s = "  var i1 = 2\n" + //
                "  var i2 = 5\n" + //
                "  var s1 = \"hello\"\n" + //
                "  var s2 = \" world\"\n" + //
                "  var i3 = + i1 i2\n" + //
                "  print i3\n" + //
                "  var s3 = ++ s1 s2\n" + //
                "  print s3\n" +
                "  print ++ s3 \" one\"";

        new Xlang().execute(s);
    }

}
