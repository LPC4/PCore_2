package org.lpc.s2_parsing.ast.statement;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class VariableDeclarationStatementNode extends StatementNode {
    private final String name;
    private final String type;
    private final ExpressionNode initializer;
    private boolean isConst;

    public VariableDeclarationStatementNode(String name, String type, ExpressionNode initializer) {
        this.name = name;
        this.type = type;
        this.initializer = initializer;
        this.isConst = false;
    }

    public void setConst() {
        this.isConst = true;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("VariableDeclaration(").append(name).append(")").append(isConst ? " const" : "").append(" type: ").append(type);
        if (initializer != null) {
            sb.append(indent(depth + 1)).append(initializer.toString(depth + 1));
        } else {
            sb.append("\n");
        }
        return sb.toString();
    }
}

