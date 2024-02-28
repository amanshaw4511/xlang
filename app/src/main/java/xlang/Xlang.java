package xlang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import lombok.SneakyThrows;
import xlang.lexer.Token;
import xlang.lexer.Tokenizer;

public class Xlang {

    @SneakyThrows
    public void execute(Path sourceCodePath) {
        execute(Files.readString(sourceCodePath));
    }

    public void execute(String sourceCode) {
        List<Token> tokens = Tokenizer.tokenize(sourceCode);

    }

}
