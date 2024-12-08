package org.lpc.s2_parsing.ast.expression.value;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class LiteralNode extends ExpressionNode {
    private final String value;

    public LiteralNode(String value) {
        this.value = value;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "LiteralNode(" + value + ")";
    }
}

