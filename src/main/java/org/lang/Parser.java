package org.lang;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<SyntaxToken> tokens;
    private int position;
    private final List<String> diagnostics;

    public Parser(String text) {
        position = 0;
        tokens = new ArrayList<>();
        diagnostics = new ArrayList<>();

        var lexer = new Lexer(text);
        SyntaxToken token;

        do {
            token = lexer.nextToken();
            if (token.getKind() != SyntaxKind.BadToken && token.getKind() != SyntaxKind.WhitespaceToken) {
                tokens.add(token);
            }
        } while (token.getKind() != SyntaxKind.EndOfFileToken);

        diagnostics.addAll(lexer.getDiagnostics());
    }

    public SyntaxTree parse() {
        var expression = parseTerm();
        var endOfFileToken = match(SyntaxKind.EndOfFileToken);
        return new SyntaxTree(diagnostics, expression, endOfFileToken);
    }

    public ExpressionSyntax parseTerm() {
        var left = parseFactor();

        while (current().getKind() == SyntaxKind.PlusToken ||
                current().getKind() == SyntaxKind.MinusToken) {

            SyntaxToken operatorToken = nextToken();
            var right = parseFactor();
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }
        return left;
    }

    public ExpressionSyntax parseFactor() {
        var left = parsePrimaryExpression();

        while (current().getKind() == SyntaxKind.StarToken ||
                current().getKind() == SyntaxKind.SlashToken) {

            SyntaxToken operatorToken = nextToken();
            var right = parsePrimaryExpression();
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }
        return left;
    }

    private ExpressionSyntax parsePrimaryExpression() {

        if (current().getKind() == SyntaxKind.OpenParenthesisToken) {
            var left = nextToken();
            var expression = parseExpression();
            var right = match(SyntaxKind.CloseParenthesisToken);
            return new ParenthesizedExpressionSyntax(left, expression, right);
        }

        var numberToken = match(SyntaxKind.NumberToken);
        return new NumberExpressionSyntax(numberToken);
    }

    private ExpressionSyntax parseExpression() {
        return parseTerm();
    }

    private SyntaxToken peek(int offset) {
        if (tokens.isEmpty()) {
            return new SyntaxToken(SyntaxKind.BadToken, position, null, null);
        }

        int idx = position + offset;
        if (idx >= tokens.size()) {
            return tokens.getLast();
        }
        return tokens.get(idx);
    }

    private SyntaxToken current() {
        return peek(0);
    }

    private SyntaxToken nextToken() {
        var cur = current();
        position++;
        return cur;
    }

    private SyntaxToken match(SyntaxKind kind) {
        if (current().getKind() == kind) return nextToken();
        var error = STR."Unexpected token: <\{current().getKind()}>, Expected: <\{kind}>";
        diagnostics.add(error);
        return new SyntaxToken(kind, current().getPosition(), null, null);
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }
}
