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
    BangToken,
    AmpersandAmpersandToken,
    PipePipeToken,
    OpenParenthesisToken,
    CloseParenthesisToken,
    IdentifierToken,

    // Keywords
    TrueKeyword,
    FalseKeyword,

    // Expressions
    LiteralExpression,
    UnaryExpression,
    BinaryExpression,
    ParenthesizedExpression;

    public int getBinaryOperatorPrecedence() {
        return switch (this) {
            case StarToken, SlashToken -> 4;
            case PlusToken, MinusToken -> 3;
            case AmpersandAmpersandToken -> 2;
            case PipePipeToken -> 1;
            default -> 0;
        };
    }

    public int getUnaryOperatorPrecedence() {
        return switch (this) {
            case PlusToken, MinusToken, BangToken -> 5;
            default -> 0;
        };
    }

    public static SyntaxKind getKeyWordKind(String text) {
        return switch (text) {
            case "true" -> SyntaxKind.TrueKeyword;
            case "false" -> SyntaxKind.FalseKeyword;
            default -> SyntaxKind.IdentifierToken;
        };
    }
}
