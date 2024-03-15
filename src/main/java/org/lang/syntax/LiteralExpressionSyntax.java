package org.lang.syntax;

import java.util.Collections;
import java.util.List;

public class LiteralExpressionSyntax extends ExpressionSyntax {

    private final SyntaxToken literalToken;

    public LiteralExpressionSyntax(SyntaxToken literalToken) {
        this.literalToken = literalToken;
    }

    @Override
    public SyntaxKind getKind() {
        return SyntaxKind.LiteralExpression;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return Collections.singletonList(literalToken);
    }

    public SyntaxToken getLiteralToken() {
        return literalToken;
    }

    @Override
    public String toString() {
        return STR."LiteralExpressionSyntax{numberToken=\{literalToken}}";
    }
}
