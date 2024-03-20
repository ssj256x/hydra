package org.lang.binding;

public class BoundBinaryExpression extends BoundExpression{

    private final BoundExpression left;
    private final BoundBinaryOperator operator;
    private final BoundExpression right;

    public BoundBinaryExpression(BoundExpression left,
                                 BoundBinaryOperator operator,
                                 BoundExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Class<?> getType() {
        return left.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.BinaryExpression;
    }

    public BoundExpression getLeft() {
        return left;
    }

    public BoundBinaryOperator getOperator() {
        return operator;
    }

    public BoundExpression getRight() {
        return right;
    }
}
