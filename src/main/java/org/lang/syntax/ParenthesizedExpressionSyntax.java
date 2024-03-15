package org.lang.syntax;

import java.util.List;

public class ParenthesizedExpressionSyntax extends ExpressionSyntax {

    private final SyntaxToken openParenthesisToken;
    private final ExpressionSyntax expression;
    private final SyntaxToken closeParenthesisToken;

    public ParenthesizedExpressionSyntax(SyntaxToken openParenthesisToken,
                                         ExpressionSyntax expression,
                                         SyntaxToken closeParenthesisToken) {
        this.openParenthesisToken = openParenthesisToken;
        this.expression = expression;
        this.closeParenthesisToken = closeParenthesisToken;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.ParenthesizedExpression;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(openParenthesisToken, expression, closeParenthesisToken);
    }

    public SyntaxToken getOpenParenthesisToken() {
        return openParenthesisToken;
    }

    public ExpressionSyntax getExpression() {
        return expression;
    }

    public SyntaxToken getCloseParenthesisToken() {
        return closeParenthesisToken;
    }
}
