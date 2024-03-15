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
    NumberExpression,
    BinaryExpression,
    ParenthesizedExpression,
}
