package org.lpc.s2_parsing.ast.expression;

import java.util.List;

public class ArrayNode extends ExpressionNode {
    private final List<ExpressionNode> elements;

    public ArrayNode(List<ExpressionNode> elements) {
        this.elements = elements;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("ArrayNode\n");
        sb.append(indent(depth + 1)).append("elements: [");
        for (ExpressionNode element : elements) {
            sb.append(element.toString(depth + 2));
        }
        sb.append("\n").append(indent(depth + 1)).append("]\n");
        return sb.toString();
    }
}
