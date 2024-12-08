package org.lpc.s2_parsing.ast;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.type.TypeNode;

@Getter
public class ParameterNode extends ASTNode {
    private final String name;
    private final TypeNode type;

    public ParameterNode(String name, TypeNode type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append(name);
        sb.append(": ").append(type.toString(depth));
        return sb.toString();
    }
}

