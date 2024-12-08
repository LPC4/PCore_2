package org.lpc.s2_parsing.ast.expression.memory;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class PointerDereferenceNode extends ExpressionNode {
    private final ExpressionNode pointer;

    public PointerDereferenceNode(ExpressionNode pointer) {
        this.pointer = pointer;
    }
}
