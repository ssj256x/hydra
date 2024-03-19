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

    public int evaluate() throws Exception {
        return evaluateExpression(root);
    }

    private int evaluateExpression(BoundExpression node) throws Exception {
        return switch (node) {
            case BoundLiteralExpression n:
                yield (int) n.getValue();

//            case ParenthesizedExpressionSyntax p:
//                yield evaluateExpression(p.getExpression());

            case BoundUnaryExpression u:
                var operand = evaluateExpression(u.getOperand());
                yield switch (u.getOperatorKind()) {
                    case Identity -> operand;
                    case Negation -> -operand;
                };
            case BoundBinaryExpression b:
                var left = evaluateExpression(b.getLeft());
                var right = evaluateExpression(b.getRight());

                yield switch (b.getOperatorKind()) {
                    case Addition -> left + right;
                    case Subtraction -> left - right;
                    case Division -> left / right;
                    case Multiplication -> left * right;
                };

            default:
                throw new Exception(STR."Unexpected node \{node.getKind()}");
        };
    }
}
