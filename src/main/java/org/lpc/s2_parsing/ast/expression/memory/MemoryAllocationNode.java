package org.lpc.s2_parsing.ast.expression.memory;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class MemoryAllocationNode extends ExpressionNode {
    private final ExpressionNode size;

    public MemoryAllocationNode(ExpressionNode size) {
        this.size = size;
    }
}
