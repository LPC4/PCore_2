package org.lpc.s2_parsing.ast.expression.memory;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class PointerAssignmentNode extends ExpressionNode {
    private final ExpressionNode pointer;
    private final ExpressionNode value;

    public PointerAssignmentNode(ExpressionNode pointer, ExpressionNode value) {
        this.pointer = pointer;
        this.value = value;
    }
}
