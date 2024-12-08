package org.lpc.s2_parsing.ast;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class GlobalVariableDeclarationNode extends ASTNode {
    private final String name;
    private final String type;
    private final ExpressionNode initializer;
    boolean isConst = false;

    public GlobalVariableDeclarationNode(String name, String type, ExpressionNode initializer) {
        this.name = name;
        this.type = type;
        this.initializer = initializer;
    }

    public void setConst() {
        isConst = true;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("  ".repeat(Math.max(0, depth)));
        sb.append("Global");
        sb.append(" ");
        sb.append(name);
        sb.append(" ");
        sb.append(type);
        if (initializer != null) {
            sb.append(initializer.toString(depth + 1));
        }
        for (ASTNode child : children) {
            sb.append(child.toString(depth + 1));
        }
        return sb.toString();
    }
}
