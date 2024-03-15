package org.lang;

import java.util.List;

public class UnaryExpressionSyntax extends ExpressionSyntax{

    private final SyntaxToken operatorToken;
    private final ExpressionSyntax operand;

    public UnaryExpressionSyntax(SyntaxToken operatorToken, ExpressionSyntax operand) {
        this.operatorToken = operatorToken;
        this.operand = operand;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.UnaryExpression;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return List.of(operatorToken, operand);
    }

    public SyntaxToken getOperatorToken() {
        return operatorToken;
    }

    public ExpressionSyntax getOperand() {
        return operand;
    }
}
