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
        ;
        return new BoundLiteralExpression(value);
    }

    private BoundExpression bindUnaryExpression(UnaryExpressionSyntax syntax) throws Exception {
        var boundOperand = bindExpression(syntax.getOperand());
        var boundOperatorKind = bindUnaryOperatorKind(syntax.getOperatorToken().getKind(), boundOperand.getType());

        if (boundOperatorKind == null) {
            var err = STR."Unary operator \{syntax.getOperatorToken().getText()} is not defined for type \{boundOperand.getKind()}";
            diagnostics.add(err);
            return boundOperand;
        }

        return new BoundUnaryExpression(boundOperatorKind, boundOperand);
//        return new BoundUnaryExpression(boundOperatorKind.Value ?? , boundOperand);
    }

    private BoundExpression bindBinaryExpression(BinaryExpressionSyntax syntax) throws Exception {
        var boundLeft = bindExpression(syntax.getLeft());
        var boundRight = bindExpression(syntax.getRight());
        var boundOperatorKind = bindBinaryOperatorKind(syntax.getOperatorToken().getKind(), boundLeft.getType(), boundRight.getType());

        if (boundOperatorKind == null) {
            var err = STR."Unary operator \{syntax.getOperatorToken().getText()} is not defined for types <\{boundLeft.getType()}> and <\{boundRight.getType()}>";
            diagnostics.add(err);
            return boundLeft;
        }

        return new BoundBinaryExpression(boundLeft, boundOperatorKind, boundRight);
//        return new BoundBinaryExpression(boundLeft, boundOperatorKind.Value ?? , boundRight);
    }

    private BoundUnaryOperatorKind bindUnaryOperatorKind(SyntaxKind kind, Class<?> operandType) throws Exception {
        if (operandType.equals(Integer.class)) {
            return switch (kind) {
                case PlusToken -> BoundUnaryOperatorKind.Identity;
                case MinusToken -> BoundUnaryOperatorKind.Negation;
                default -> null;
            };
        }

        if (operandType.equals(Boolean.class)) {
            return switch (kind) {
                case BangToken -> BoundUnaryOperatorKind.LogicalNegation;
                default -> null;
            };
        }
        return null;
    }

    private BoundBinaryOperatorKind bindBinaryOperatorKind(SyntaxKind kind,
                                                           Class<?> leftType,
                                                           Class<?> rightType) throws Exception {

        if (leftType.equals(Integer.class) && rightType.equals(Integer.class)) {
            return switch (kind) {
                case PlusToken -> BoundBinaryOperatorKind.Addition;
                case MinusToken -> BoundBinaryOperatorKind.Subtraction;
                case StarToken -> BoundBinaryOperatorKind.Multiplication;
                case SlashToken -> BoundBinaryOperatorKind.Division;
                default -> null;
            };
        }

        if (leftType.equals(Boolean.class) && rightType.equals(Boolean.class)) {
            return switch (kind) {
                case AmpersandAmpersandToken -> BoundBinaryOperatorKind.LogicalAnd;
                case PipePipeToken -> BoundBinaryOperatorKind.LogicalOr;
                default -> null;
            };
        }
        return null;
    }
}
