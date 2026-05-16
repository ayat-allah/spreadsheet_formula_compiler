package compiler;

import java.util.*;

public class Lexer {

    private final String input;
    private int pos = 0;
    private int line = 1;
    private int col = 1;

    private final List<Token> tokens = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();

    private static final Set<String> FUNCTIONS =
            Set.of("SUM", "MAX", "MIN", "IF");

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {

        while (pos < input.length()) {

            char c = input.charAt(pos);

            if (Character.isWhitespace(c)) {
                advance();
                continue;
            }

            // formula start
            if (c == '=' && pos == 0) {
                add(TokenType.FORMULA_START, "=");
                advance();
                continue;
            }

            // numbers
            if (Character.isDigit(c)) {
                number();
                continue;
            }

            // identifiers / functions / cell refs
            if (Character.isLetter(c)) {
                identifier();
                continue;
            }

            // two-char operators
            if (match(">=")) continue;
            if (match("<=")) continue;
            if (match("!=")) continue;
            if (match("==")) continue;

            // one-char operators
            switch (c) {

                case '+':
                    add(TokenType.PLUS, "+");
                    break;

                case '-':
                    add(TokenType.MINUS, "-");
                    break;

                case '*':
                    add(TokenType.MULTIPLY, "*");
                    break;

                case '/':
                    add(TokenType.DIVIDE, "/");
                    break;

                case '>':
                    add(TokenType.GT, ">");
                    break;

                case '<':
                    add(TokenType.LT, "<");
                    break;

                case '(':
                    add(TokenType.LPAREN, "(");
                    break;

                case ')':
                    add(TokenType.RPAREN, ")");
                    break;

                case ',':
                    add(TokenType.COMMA, ",");
                    break;

                case ':':
                    add(TokenType.COLON, ":");
                    break;

                default:
                    errors.add(
                            "Invalid char: " + c +
                            " at " + line + ":" + col
                    );
            }

            advance();
        }

        add(TokenType.EOF, "");
        return tokens;
    }

    private void number() {

        int start = pos;

        while (
                pos < input.length() &&
                Character.isDigit(input.charAt(pos))
        ) {
            advance();
        }

        add(
                TokenType.NUMBER,
                input.substring(start, pos)
        );
    }

    private void identifier() {

        int start = pos;

        while (
                pos < input.length() &&
                Character.isLetterOrDigit(input.charAt(pos))
        ) {
            advance();
        }

        String text =
                input.substring(start, pos).toUpperCase();

        if (FUNCTIONS.contains(text)) {

            add(TokenType.FUNCTION, text);

        } else if (isCell(text)) {

            add(TokenType.CELL_REF, text);

        } else {

            errors.add("Invalid identifier: " + text);
        }
    }

    private boolean isCell(String s) {
        return s.matches("^[A-Z]+[0-9]+$");
    }

    private boolean match(String s) {

        if (input.startsWith(s, pos)) {

            add(
                    switch (s) {
                        case ">=" -> TokenType.GE;
                        case "<=" -> TokenType.LE;
                        case "!=" -> TokenType.NEQ;
                        case "==" -> TokenType.EQ;
                        default -> throw new IllegalArgumentException(
                                "Unknown operator: " + s
                        );
                    },
                    s
            );

            pos += s.length();
            col += s.length();

            return true;
        }

        return false;
    }

    private void add(TokenType type, String lexeme) {

        tokens.add(
                new Token(type, lexeme, line, col)
        );
    }

    private void advance() {

        pos++;
        col++;
    }

    public List<String> getErrors() {
        return errors;
    }
}