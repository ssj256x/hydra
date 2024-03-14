package org.lang;

public class Lexer {
    private final String text;
    private int position;

    public Lexer(String text) {
        this.text = text;
        this.position = 0;
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
        // <numbers>
        // + - / * ( )
        // <whitespace>

        if (position >= text.length()) {
            return new SyntaxToken(SyntaxKind.EndOfFileToken, position, "\0", null);
        }

        if (Character.isDigit(current())) {
            var start = position;

            while (Character.isDigit(current())) {
                next();
            }

            var curText = text.substring(start, position);
            var value = tryIntParse(curText);
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
            return new SyntaxToken(SyntaxKind.OpenParenToken, next(), "(", null);
        }

        if (current() == ')') {
            return new SyntaxToken(SyntaxKind.CloseParenToken, next(), ")", null);
        }

        return new SyntaxToken(SyntaxKind.BadToken, next(), text.substring(position - 1), null);
    }

    private Object tryIntParse(String str) {
        int value;
        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return "<int-parse:false>";
        }
        return value;
    }
}
