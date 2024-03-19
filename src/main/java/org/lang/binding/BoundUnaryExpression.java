package org.lang.binding;

import java.lang.reflect.Type;

public class BoundUnaryExpression extends BoundExpression {

    private final BoundUnaryOperatorKind operatorKind;
    private final BoundExpression operand;

    public BoundUnaryExpression(BoundUnaryOperatorKind operatorKind, BoundExpression operand) {
        this.operatorKind = operatorKind;
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

    public BoundUnaryOperatorKind getOperatorKind() {
        return operatorKind;
    }

    public BoundExpression getOperand() {
        return operand;
    }
}
