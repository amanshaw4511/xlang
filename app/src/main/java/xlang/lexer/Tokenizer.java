package xlang.lexer;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.*;
import static xlang.lexer.TokenType.*;

public class Tokenizer {
    private Tokenizer() {}
    private static final List<String> booleans = List.of("true", "false");
    private static final char stringQuote = '"';

    private static final List<String> namedOperator = List.of(
            "and", "or", "not");

    private static final List<String> keywords = List.of(
            "var", "if", "else", "struct", "print");

    private static boolean isKeyword(String text) {
        return keywords.contains(text);
    }

    private static boolean isIdentifierChar(char ch) {
        return isAlphabetic(ch) || isDigit(ch);
    }

    private static boolean isNamedOperator(String text) {
        return namedOperator.contains(text);
    }

    private static void addToken(List<Token> tokens, TokenType tokenType, String value) {
        tokens.add(new Token(tokenType, value));
    }

    private static boolean isBooleanOperator(String text) {
        return booleans.contains(text);
    }

    public static List<Token> tokenize(String source) {
        List<Token> tokens = new ArrayList<>();
        var arr = source.toCharArray();
        int i = 0;
        int len = arr.length;
        while (i < len) {
            if (isWhitespace(arr[i])) {
                i++;
                continue;
            }

            if (isAlphabetic(arr[i])) {
                // it could be either keyword, named-operator, boolean-literal or variable
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(arr[i]);
                    i++;
                } while (i < len && isIdentifierChar(arr[i]));

                String text = sb.toString();
                if (isKeyword(text)) {
                    addToken(tokens, Keyword, text);
                    continue;
                }

                if (isNamedOperator(text)) {
                    addToken(tokens, Operator, text);
                    continue;
                }

                if (isBooleanOperator(text)) {
                    addToken(tokens, Boolean, text);
                    continue;
                }

                addToken(tokens, Variable, text);
                continue;
            }

            if (isDigit(arr[i])) {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(arr[i]);
                    i++;
                } while (i < len && isDigit(arr[i]));

                addToken(tokens, Integer, sb.toString());
                continue;
            }

            if (arr[i] == stringQuote) {
                StringBuilder sb = new StringBuilder();
                i++;
                while (i < len && arr[i] != stringQuote) { // todo handle scape char
                    sb.append(arr[i]);
                    i++;
                }
                addToken(tokens, String, sb.toString());
                i++;
                continue;
            }

            if (arr[i] == '+') {
                i++;
                if (i < len && arr[i] == '+') {
                    addToken(tokens, Operator, "++");
                    i++;
                    continue;
                }
                addToken(tokens, Operator, "+");
                continue;
            }

            if (arr[i] == '=') {
                i++;
                if (i < len && arr[i] == '=') {
                    addToken(tokens, Operator, "==");
                    i++;
                    continue;
                }
                addToken(tokens, Operator, "=");
                continue;
            }
            if (List.of('.', '+', '-', '*', '/', '<', '>').contains(arr[i])) {
                addToken(tokens, Operator, Character.toString(arr[i]));
                i++;
                continue;
            }

            if (arr[i] == '{') {
                addToken(tokens, BlockStart, Character.toString(arr[i]));
                i++;
                continue;
            }
            if (arr[i] == '}') {
                addToken(tokens, BlockEnd, Character.toString(arr[i]));
                i++;
                continue;
            }

        }
        return tokens;

    }
}
