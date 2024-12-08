package org.lpc.s2_parsing.ast.expression.value;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class StructFieldAccessNode extends ExpressionNode {
    private final ExpressionNode structInstance;
    private final String fieldName;

    public StructFieldAccessNode(ExpressionNode structInstance, String fieldName) {
        this.structInstance = structInstance;
        this.fieldName = fieldName;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "StructFieldAccessNode(" + fieldName + ")";
    }
}

