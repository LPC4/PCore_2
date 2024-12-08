package org.lpc.s2_parsing.ast.expression.value;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class AddressOfNode extends ExpressionNode {
    private final ExpressionNode operand;

    public AddressOfNode(ExpressionNode operand) {
        this.operand = operand;
    }
}

