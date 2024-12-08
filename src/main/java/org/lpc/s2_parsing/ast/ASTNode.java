package org.lpc.s2_parsing.ast;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class ASTNode {
    public List<ASTNode> children;

    public ASTNode() {
        this.children = new ArrayList<>();
    }

    public void addChild(ASTNode node) {
        this.children.add(node);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("  ".repeat(Math.max(0, depth)));
        sb.append(this.getClass().getSimpleName());
        for (ASTNode child : children) {
            sb.append(child.toString(depth + 1));
        }
        return sb.toString();
    }

    protected String indent(int depth) {
        return "  ".repeat(Math.max(0, depth));
    }
}
