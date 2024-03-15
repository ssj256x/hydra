package org.lang.syntax;

import java.util.Collections;
import java.util.List;

public class SyntaxToken extends SyntaxNode {
    private final SyntaxKind kind;
    private final int position;
    private final String text;
    private final Object value;

    public SyntaxToken(SyntaxKind kind, int position, String text, Object value) {
        this.kind = kind;
        this.position = position;
        this.text = text;
        this.value = value;
    }

    public SyntaxKind getKind() {
        return kind;
    }

    @Override
    public List<SyntaxNode> getChildren() {
        return Collections.emptyList();
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return STR."\{kind.name()}: '\{text}'\{value != null ? STR." \{value}" : ""}";
    }
}
