package org.lang.binding;

import org.lang.syntax.*;

import java.util.ArrayList;
import java.util.List;

public class Binder {

    private final List<String> diagnostics;

    public Binder() {
        this.diagnostics = new ArrayList<>();
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    public BoundExpression bindExpression(ExpressionSyntax syntax) throws Exception {
        return switch (syntax.getKind()) {
            case LiteralExpression -> bindLiteralExpression((LiteralExpressionSyntax) syntax);
            case UnaryExpression -> bindUnaryExpression((UnaryExpressionSyntax) syntax);
            case BinaryExpression -> bindBinaryExpression((BinaryExpressionSyntax) syntax);
            default -> throw new Exception(STR."Unexpected syntax \{syntax.getKind()}");
        };
    }

    private BoundExpression bindLiteralExpression(LiteralExpressionSyntax syntax) {
        var value = syntax.getValue() != null ? syntax.getValue() : 0;
        return new BoundLiteralExpression(value);
    }

    private BoundExpression bindUnaryExpression(UnaryExpressionSyntax syntax) throws Exception {
        var boundOperand = bindExpression(syntax.getOperand());
        var boundOperator = BoundUnaryOperator.bind(syntax.getOperatorToken().getKind(), boundOperand.getType());

        if (boundOperator == null) {
            var err = STR."Unary operator \{syntax.getOperatorToken().getText()} is not defined for type \{boundOperand.getKind()}";
            diagnostics.add(err);
            return boundOperand;
        }

        return new BoundUnaryExpression(boundOperator, boundOperand);
    }

    private BoundExpression bindBinaryExpression(BinaryExpressionSyntax syntax) throws Exception {
        var boundLeft = bindExpression(syntax.getLeft());
        var boundRight = bindExpression(syntax.getRight());
        var boundOperator = BoundBinaryOperator.bind(syntax.getOperatorToken().getKind(), boundLeft.getType(), boundRight.getType());

        if (boundOperator == null) {
            var err = STR."Unary operator \{syntax.getOperatorToken().getText()} is not defined for types <\{boundLeft.getType()}> and <\{boundRight.getType()}>";
            diagnostics.add(err);
            return boundLeft;
        }

        return new BoundBinaryExpression(boundLeft, boundOperator, boundRight);
    }
}
