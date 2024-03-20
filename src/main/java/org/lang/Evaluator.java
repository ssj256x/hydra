package org.lang;

import org.lang.binding.BoundBinaryExpression;
import org.lang.binding.BoundExpression;
import org.lang.binding.BoundLiteralExpression;
import org.lang.binding.BoundUnaryExpression;
import org.lang.syntax.*;

import static java.lang.StringTemplate.STR;

public class Evaluator {
    private final BoundExpression root;

    public Evaluator(BoundExpression root) {
        this.root = root;
    }

    public Object evaluate() throws Exception {
        return evaluateExpression(root);
    }

    private Object evaluateExpression(BoundExpression node) throws Exception {
        return switch (node) {
            case BoundLiteralExpression n:
                yield n.getValue();

//            case ParenthesizedExpressionSyntax p:
//                yield evaluateExpression(p.getExpression());

            case BoundUnaryExpression u:
                var operand = evaluateExpression(u.getOperand());
                yield switch (u.getOperatorKind()) {
                    case Identity -> (Integer) operand;
                    case Negation -> -(Integer) operand;
                    case LogicalNegation -> !(Boolean) operand;
                    default -> throw new Exception(STR."Unexpected unary operator \{u.getOperand()}");
                };
            case BoundBinaryExpression b:
                var left = evaluateExpression(b.getLeft());
                var right = evaluateExpression(b.getRight());

                yield switch (b.getOperatorKind()) {
                    case Addition -> (Integer) left + (Integer) right;
                    case Subtraction -> (Integer) left - (Integer) right;
                    case Division -> (Integer) left / (Integer) right;
                    case Multiplication -> (Integer) left * (Integer) right;
                    case LogicalAnd -> (Boolean) left && (Boolean) right;
                    case LogicalOr -> (Boolean) left || (Boolean) right;
                    default -> throw new Exception(STR."Unexpected binary operator \{b.getOperatorKind()}");
                };

            default:
                throw new Exception(STR."Unexpected node \{node.getKind()}");
        };
    }
}
