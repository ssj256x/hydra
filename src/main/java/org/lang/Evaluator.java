package org.lang;

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
        if (node instanceof LiteralExpressionSyntax n) {
            return (int) n.getLiteralToken().getValue();
        }

        if (node instanceof BinaryExpressionSyntax b) {
            var left = evaluateExpression(b.getLeft());
            var right = evaluateExpression(b.getRight());

            switch (b.getOperatorToken().getKind()) {
                case PlusToken -> {
                    return left + right;
                }
                case MinusToken -> {
                    return left - right;
                }
                case SlashToken -> {
                    return left / right;
                }
                case StarToken -> {
                    return left * right;
                }
                default -> throw new Exception(STR."Unexpected Binary Operator \{b.getOperatorToken().getKind()}");
            }
        }

        if(node instanceof ParenthesizedExpressionSyntax p) {
            return evaluateExpression(p.getExpression());
        }

        throw new Exception(STR."Unexpected node \{node.getKind()}");
    }
}
