package org.lang.syntax;

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
        return peek(0);
    }

    private char lookahead() {
        return peek(1);
    }

    private int next() {
        // returns current value and then increments
        return position++;
    }

    private char peek(int offset) {
        var index = position + offset;
        if (index >= text.length()) {
            return '\0';
        }
        return text.charAt(index);
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

        if (Character.isLetter(current())) {
            var start = position;

            while (Character.isLetter(current())) {
                next();
            }

            var curText = text.substring(start, position);
            var kind = SyntaxKind.getKeyWordKind(curText);
            return new SyntaxToken(kind, start, curText, null);
        }

        switch (current()) {
            case '+':
                return new SyntaxToken(SyntaxKind.PlusToken, next(), "+", null);
            case '-':
                return new SyntaxToken(SyntaxKind.MinusToken, next(), "-", null);
            case '*':
                return new SyntaxToken(SyntaxKind.StarToken, next(), "*", null);
            case '/':
                return new SyntaxToken(SyntaxKind.SlashToken, next(), "/", null);
            case '(':
                return new SyntaxToken(SyntaxKind.OpenParenthesisToken, next(), "(", null);
            case ')':
                return new SyntaxToken(SyntaxKind.CloseParenthesisToken, next(), ")", null);
            case '!':
                return new SyntaxToken(SyntaxKind.BangToken, next(), "!", null);
            case '&':
                if (lookahead() == '&') {
                    return new SyntaxToken(SyntaxKind.AmpersandAmpersandToken, position += 2, "&&", null);
                }
                break;
            case '|':
                if (lookahead() == '|') {
                    return new SyntaxToken(SyntaxKind.PipePipeToken, position += 2, "||", null);
                }
                break;
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
