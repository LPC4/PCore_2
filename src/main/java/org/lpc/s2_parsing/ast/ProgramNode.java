package org.lpc.s2_parsing.ast;

import lombok.Getter;

@Getter
public class ProgramNode extends ASTNode {
    String name;

    public ProgramNode(String name) {
        this.name = name;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("Program(").append(name).append(")").append("{");
        for (ASTNode child : children) {
            sb.append(child.toString(depth + 1));
        }
        sb.append(indent(depth)).append("}\n");
        return sb.toString();
    }
}
