package org.lpc.s2_parsing.ast.statement;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;
import org.lpc.s2_parsing.ast.expression.type.TypeNode;

@Getter
public class VariableDeclarationStatementNode extends StatementNode {
    private final String name;
    private final TypeNode type;
    private final ExpressionNode initializer;
    private boolean isConst = false;

    public VariableDeclarationStatementNode(String name, TypeNode type, ExpressionNode initializer) {
        this.name = name;
        this.type = type;
        this.initializer = initializer;
    }

    public void setConst() {
        this.isConst = true;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("VariableDeclaration(").append(name).append(")").append(isConst ? " const" : "").append(" type: ").append(type.toString(depth + 1));
        if (initializer != null) {
            sb.append(indent(depth + 1)).append(initializer.toString(depth + 1));
        } else {
            sb.append("\n");
        }
        return sb.toString();
    }
}

