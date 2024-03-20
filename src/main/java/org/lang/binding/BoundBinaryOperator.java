package org.lang.binding;

import org.lang.syntax.SyntaxKind;

import java.util.Arrays;

public class BoundBinaryOperator {

    private SyntaxKind syntaxKind;
    private final BoundBinaryOperatorKind kind;
    private final Class<?> leftType;
    private final Class<?> rightType;
    private final Class<?> resultType;

    private BoundBinaryOperator(SyntaxKind syntaxKind, BoundBinaryOperatorKind kind, Class<?> type) {
        this(syntaxKind, kind, type, type, type);
    }

    private BoundBinaryOperator(SyntaxKind syntaxKind,
                                BoundBinaryOperatorKind kind,
                                Class<?> leftType,
                                Class<?> rightType,
                                Class<?> resultType) {
        this.syntaxKind = syntaxKind;
        this.kind = kind;
        this.leftType = leftType;
        this.rightType = rightType;
        this.resultType = resultType;
    }

    private static final BoundBinaryOperator[] operators = {
            // Arithmetic
            new BoundBinaryOperator(SyntaxKind.PlusToken, BoundBinaryOperatorKind.Addition, Integer.class),
            new BoundBinaryOperator(SyntaxKind.MinusToken, BoundBinaryOperatorKind.Subtraction, Integer.class),
            new BoundBinaryOperator(SyntaxKind.StarToken, BoundBinaryOperatorKind.Multiplication, Integer.class),
            new BoundBinaryOperator(SyntaxKind.SlashToken, BoundBinaryOperatorKind.Division, Integer.class),

            // Logical
            new BoundBinaryOperator(SyntaxKind.AmpersandAmpersandToken, BoundBinaryOperatorKind.LogicalAnd, Boolean.class),
            new BoundBinaryOperator(SyntaxKind.PipePipeToken, BoundBinaryOperatorKind.LogicalOr, Boolean.class),

    };

    public static BoundBinaryOperator bind(SyntaxKind syntaxKind, Class<?> leftType, Class<?> rightType) {
        return Arrays.stream(operators)
                .filter(op -> op.syntaxKind == syntaxKind &&
                        op.leftType == leftType &&
                        op.rightType == rightType)
                .findFirst()
                .orElse(null);
    }

    public SyntaxKind getSyntaxKind() {
        return syntaxKind;
    }

    public BoundBinaryOperatorKind getKind() {
        return kind;
    }

    public Class<?> getLeftType() {
        return leftType;
    }

    public Class<?> getRightType() {
        return rightType;
    }

    public Class<?> getResultType() {
        return resultType;
    }
}
