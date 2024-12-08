package org.lpc.s2_parsing.ast.expression;

import lombok.Getter;

@Getter
public class IdentifierNode extends ExpressionNode {
    private final String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "Identifier(" + name + ")";
    }
}

