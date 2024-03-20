package org.lang.syntax;

import java.util.Collections;
import java.util.List;

public class LiteralExpressionSyntax extends ExpressionSyntax {

    private final SyntaxToken literalToken;
    private final Object value;

    public LiteralExpressionSyntax(SyntaxToken literalToken) {
        this(literalToken, literalToken.getValue());
    }

    public LiteralExpressionSyntax(SyntaxToken literalToken, Object value) {
        this.literalToken = literalToken;
        this.value = value;
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

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return STR."LiteralExpressionSyntax{numberToken=\{literalToken}}";
    }
}
