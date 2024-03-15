package org.lang;

import java.util.Collections;
import java.util.List;

public class NumberExpressionSyntax extends ExpressionSyntax {

    private final SyntaxToken numberToken;

    public NumberExpressionSyntax(SyntaxToken numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.NumberExpression;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return Collections.singletonList(numberToken);
    }

    public SyntaxToken getNumberToken() {
        return numberToken;
    }

    @Override
    public String toString() {
        return "NumberExpressionSyntax{" +
                "numberToken=" + numberToken +
                '}';
    }
}
