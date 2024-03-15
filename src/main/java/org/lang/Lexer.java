package org.lang;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String text;
    private int position;
    private final List<String> diagnostics;

    public Lexer(String text) {
        this.text = text;
        this.position = 0;
        this.diagnostics = new ArrayList<>();
    }

    private char current() {
        if (position >= text.length()) return '\0';
        return text.charAt(position);
    }

    private int next() {
        // returns current value and then increments
        return position++;
    }

    public SyntaxToken nextToken() {

        if (position >= text.length()) {
            return new SyntaxToken(SyntaxKind.EndOfFileToken, position, "\0", null);
        }

        if (Character.isDigit(current())) {
            var start = position;

            while (Character.isDigit(current())) {
                next();
            }

            var curText = text.substring(start, position);
//            var value = tryIntParse(curText);

            int value;

            try {
                value = Integer.parseInt(curText);
            } catch (Exception e) {
                var error = STR."ERROR : Text '\{curText}' cannot be represented as integer";
                diagnostics.add(error);
                return new SyntaxToken(SyntaxKind.BadToken, start, curText, null);
            }

            return new SyntaxToken(SyntaxKind.NumberToken, start, curText, value);
        }

        if (Character.isWhitespace(current())) {
            var start = position;

            while (Character.isWhitespace(current())) {
                next();
            }

            var curText = text.substring(start, position);
            return new SyntaxToken(SyntaxKind.WhitespaceToken, start, curText, null);
        }

        if (current() == '+') {
            return new SyntaxToken(SyntaxKind.PlusToken, next(), "+", null);
        }

        if (current() == '-') {
            return new SyntaxToken(SyntaxKind.MinusToken, next(), "-", null);
        }

        if (current() == '*') {
            return new SyntaxToken(SyntaxKind.StarToken, next(), "*", null);
        }

        if (current() == '/') {
            return new SyntaxToken(SyntaxKind.SlashToken, next(), "/", null);
        }

        if (current() == '(') {
            return new SyntaxToken(SyntaxKind.OpenParenthesisToken, next(), "(", null);
        }

        if (current() == ')') {
            return new SyntaxToken(SyntaxKind.CloseParenthesisToken, next(), ")", null);
        }

        var error = STR."ERROR : Bad character in input \{current()}";
        diagnostics.add(error);
        return new SyntaxToken(SyntaxKind.BadToken, next(), text.substring(position - 1), null);
    }

    private Object tryIntParse(String str) {
        int value;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return value;
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }
}
