package org.lang.binding;

import java.lang.reflect.Type;

public class BoundLiteralExpression extends BoundExpression {

    private final Object value;

    public BoundLiteralExpression(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public BoundNodeKind getKind() {
        return BoundNodeKind.LiteralExpression;
    }

    @Override
    public Class<?> getType() {
        return value.getClass();
    }
}
