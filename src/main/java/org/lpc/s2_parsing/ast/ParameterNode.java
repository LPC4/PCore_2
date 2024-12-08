package org.lpc.s2_parsing.ast;

import lombok.Getter;

@Getter
public class ParameterNode extends ASTNode {
    private final String name;
    private final String type;

    public ParameterNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append(name).append(")");
        sb.append(indent(1)).append("type: ").append(type);
        return sb.toString();
    }
}

