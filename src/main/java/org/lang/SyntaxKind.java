package org.lang;

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
    BinaryExpression,
    ParenthesizedExpression;

    public int getBinaryOperatorPrecedence() {
        return switch (this) {
            case StarToken, SlashToken -> 2;
            case PlusToken, MinusToken -> 1;
            default -> 0;
        };
    }
}
