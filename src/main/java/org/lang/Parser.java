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
        var expression = parseExpression(0);
        var endOfFileToken = matchToken(SyntaxKind.EndOfFileToken);
        return new SyntaxTree(diagnostics, expression, endOfFileToken);
    }


    private ExpressionSyntax parseExpression(int parentPrecedence) {
        var left = parsePrimaryExpression();

        while (true) {
            var precedence = getBinaryOperatorPrecedence(current().getKind());
            if (precedence == 0 || precedence <= parentPrecedence) {
                break;
            }

            var operatorToken = nextToken();
            var right = parseExpression(precedence);
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }
        return left;
    }

    private int getBinaryOperatorPrecedence(SyntaxKind kind) {
        return switch (kind) {
            case StarToken, SlashToken -> 2;
            case PlusToken, MinusToken -> 1;
            default -> 0;
        };
    }


    private ExpressionSyntax parsePrimaryExpression() {

        if (current().getKind() == SyntaxKind.OpenParenthesisToken) {
            var left = nextToken();
            var expression = parseExpression(0);
            var right = matchToken(SyntaxKind.CloseParenthesisToken);
            return new ParenthesizedExpressionSyntax(left, expression, right);
        }

        var numberToken = matchToken(SyntaxKind.NumberToken);
        return new LiteralExpressionSyntax(numberToken);
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

    private SyntaxToken matchToken(SyntaxKind kind) {
        if (current().getKind() == kind) return nextToken();
        var error = STR."Unexpected token: <\{current().getKind()}>, Expected: <\{kind}>";
        diagnostics.add(error);
        return new SyntaxToken(kind, current().getPosition(), null, null);
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }
}
