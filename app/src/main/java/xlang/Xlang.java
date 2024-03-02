package xlang;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import xlang.lexer.Token;
import xlang.lexer.Tokenizer;
import xlang.memory.MemoryContext;
import xlang.parser.StatementParser;
import xlang.parser.expression.value.Value;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Xlang {

    @SneakyThrows
    public void execute(Path sourceCodePath) {
        execute(Files.readString(sourceCodePath), System.out);
    }

    public void execute(String sourceCode, PrintStream outputStream) {
        List<Token> tokens = Tokenizer.tokenize(sourceCode);
        log.debug("parsed tokens : {}", tokens);


        StatementParser statementParser = new StatementParser(tokens, outputStream);

        var statement = statementParser.parse();

        MemoryContext.newScope();
        statement.execute();
        MemoryContext.endScope();
    }
}
