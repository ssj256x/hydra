package org.lang.binding;

public class BoundBinaryExpression extends BoundExpression{

    private final BoundExpression left;
    private final BoundBinaryOperatorKind operatorKind;
    private final BoundExpression right;

    public BoundBinaryExpression(BoundExpression left,
                                 BoundBinaryOperatorKind operatorKind,
                                 BoundExpression right) {
        this.left = left;
        this.operatorKind = operatorKind;
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

    public BoundBinaryOperatorKind getOperatorKind() {
        return operatorKind;
    }

    public BoundExpression getRight() {
        return right;
    }
}
