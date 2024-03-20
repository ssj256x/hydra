package org.lang.binding;

import org.lang.syntax.SyntaxKind;

import java.util.Arrays;

public class BoundUnaryOperator {

    private SyntaxKind syntaxKind;
    private BoundUnaryOperatorKind kind;
    private Class<?> operandType;
    private Class<?> resultType;

    private BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind kind, Class<?> operandType) {
        this(syntaxKind, kind, operandType, operandType);
    }

    private BoundUnaryOperator(SyntaxKind syntaxKind, BoundUnaryOperatorKind kind, Class<?> operandType, Class<?> resultType) {
        this.syntaxKind = syntaxKind;
        this.kind = kind;
        this.operandType = operandType;
        this.resultType = resultType;
    }

    private static BoundUnaryOperator[] operators = {
            new BoundUnaryOperator(SyntaxKind.BangToken, BoundUnaryOperatorKind.LogicalNegation, Boolean.class),
            new BoundUnaryOperator(SyntaxKind.PlusToken, BoundUnaryOperatorKind.Identity, Integer.class),
            new BoundUnaryOperator(SyntaxKind.MinusToken, BoundUnaryOperatorKind.Negation, Integer.class),
    };

    public static BoundUnaryOperator bind(SyntaxKind syntaxKind, Class<?> operandType) {
        return Arrays.stream(operators)
                .filter(op -> op.syntaxKind == syntaxKind && op.operandType == operandType)
                .findFirst()
                .orElse(null);
    }

    public SyntaxKind getSyntaxKind() {
        return syntaxKind;
    }

    public BoundUnaryOperatorKind getKind() {
        return kind;
    }

    public Class<?> getOperandType() {
        return operandType;
    }

    public Class<?> getResultType() {
        return resultType;
    }
}
