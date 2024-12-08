package org.lpc.s2_parsing.ast;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;
import org.lpc.s2_parsing.ast.expression.type.TypeNode;

@Getter
public class GlobalVariableDeclarationNode extends ASTNode {
    private final String name;
    private final TypeNode type;
    private final ExpressionNode initializer;
    boolean isConst = false;

    public GlobalVariableDeclarationNode(String name, TypeNode type, ExpressionNode initializer) {
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

        if (isConst) {
            sb.append(" const");
        }

        sb.append("\n").append(indent(depth + 1));
        sb.append(name);
        sb.append(": ");
        sb.append(type.toString(depth));

        if (initializer != null) {
            sb.append(initializer.toString(depth + 1));
        }
        for (ASTNode child : children) {
            sb.append(child.toString(depth + 1));
        }
        return sb.toString();
    }
}

