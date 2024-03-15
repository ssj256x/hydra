package org.lang.syntax;

public enum SyntaxKind {

    // Tokens
    BadToken,
    WhitespaceToken,
    EndOfFileToken,
    NumberToken,
    PlusToken,
    MinusToken,
    StarToken,
    SlashToken,
    OpenParenthesisToken,
    CloseParenthesisToken,

    // Expressions
    LiteralExpression,
    UnaryExpression,
    BinaryExpression,
    ParenthesizedExpression;

    public int getBinaryOperatorPrecedence() {
        return switch (this) {
            case StarToken, SlashToken -> 2;
            case PlusToken, MinusToken -> 1;
            default -> 0;
        };
    }

    public int getUnaryOperatorPrecedence() {
        return switch (this) {
            case PlusToken, MinusToken -> 3;
            default -> 0;
        };
    }
}
