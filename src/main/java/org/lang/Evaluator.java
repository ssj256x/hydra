package org.lang;

import org.lang.syntax.*;

import static java.lang.StringTemplate.STR;

public class Evaluator {
    private final ExpressionSyntax root;

    public Evaluator(ExpressionSyntax root) {
        this.root = root;
    }

    public int evaluate() throws Exception {
        return evaluateExpression(root);
    }

    private int evaluateExpression(ExpressionSyntax node) throws Exception {
        return switch (node) {
            case LiteralExpressionSyntax n:
                yield (int) n.getLiteralToken().getValue();

            case ParenthesizedExpressionSyntax p:
                yield evaluateExpression(p.getExpression());

            case UnaryExpressionSyntax u:
                var operand = evaluateExpression(u.getOperand());
                yield switch (u.getOperatorToken().getKind()) {
                    case PlusToken -> operand;
                    case MinusToken -> -operand;
                    default -> throw new Exception(STR."Unexpected Unary Operator \{u.getOperatorToken().getKind()}");
                };
            case BinaryExpressionSyntax b:
                var left = evaluateExpression(b.getLeft());
                var right = evaluateExpression(b.getRight());

                yield switch (b.getOperatorToken().getKind()) {
                    case PlusToken -> left + right;
                    case MinusToken -> left - right;
                    case SlashToken -> left / right;
                    case StarToken -> left * right;
                    default -> throw new Exception(STR."Unexpected Binary Operator \{b.getOperatorToken().getKind()}");
                };

            default:
                throw new Exception(STR."Unexpected node \{node.getKind()}");
        };
    }
}
