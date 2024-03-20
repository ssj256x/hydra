package org.lang.binding;

public class BoundUnaryExpression extends BoundExpression {

    private final BoundUnaryOperator operator;
    private final BoundExpression operand;

    public BoundUnaryExpression(BoundUnaryOperator operator, BoundExpression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public Class<?> getType() {
        return operand.getType();
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.UnaryExpression;
    }

    public BoundUnaryOperator getOperator() {
        return operator;
    }

    public BoundExpression getOperand() {
        return operand;
    }
}
