package org.lang.syntax;

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

        ExpressionSyntax left;
        var unaryOperatorPrecedence = current().getKind().getUnaryOperatorPrecedence();

        if (unaryOperatorPrecedence != 0 && unaryOperatorPrecedence >= parentPrecedence) {
            var operatorToken = nextToken();
            var operand = parseExpression(unaryOperatorPrecedence);
            left = new UnaryExpressionSyntax(operatorToken, operand);
        } else {
            left = parsePrimaryExpression();
        }

        while (true) {
            var precedence = current().getKind().getBinaryOperatorPrecedence();
            if (precedence == 0 || precedence <= parentPrecedence) {
                break;
            }

            var operatorToken = nextToken();
            var right = parseExpression(precedence);
            left = new BinaryExpressionSyntax(left, operatorToken, right);
        }
        return left;
    }

    private ExpressionSyntax parsePrimaryExpression() {
        return switch (current().getKind()) {
            case OpenParenthesisToken -> {
                var left = nextToken();
                var expression = parseExpression(0);
                var right = matchToken(SyntaxKind.CloseParenthesisToken);
                yield new ParenthesizedExpressionSyntax(left, expression, right);
            }
            case TrueKeyword, FalseKeyword -> {
                var keyWordToken = nextToken();
                var value = keyWordToken    .getKind() == SyntaxKind.TrueKeyword;
                yield new LiteralExpressionSyntax(keyWordToken, value);
            }
            default -> {
                var numberToken = matchToken(SyntaxKind.NumberToken);
                yield new LiteralExpressionSyntax(numberToken);
            }
        };
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
