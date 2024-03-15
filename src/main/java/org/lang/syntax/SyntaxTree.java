package org.lang.syntax;

import java.util.List;

public class SyntaxTree {
    private final List<String> diagnostics;
    private final ExpressionSyntax root;
    private final SyntaxToken endOfFileToken;

    public SyntaxTree(List<String> diagnostics, ExpressionSyntax root, SyntaxToken endOfFileToken) {
        this.diagnostics = diagnostics;
        this.root = root;
        this.endOfFileToken = endOfFileToken;
    }

    public List<String> getDiagnostics() {
        return diagnostics;
    }

    public ExpressionSyntax getRoot() {
        return root;
    }

    public SyntaxToken getEndOfFileToken() {
        return endOfFileToken;
    }

    public static SyntaxTree parse(String text) {
        var parser = new Parser(text);
        return parser.parse();
    }
}
