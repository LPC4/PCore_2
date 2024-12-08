package org.lpc.s2_parsing.ast.expression;

import lombok.Getter;

@Getter
public class GroupingNode extends ExpressionNode {
    private final ExpressionNode expression;

    public GroupingNode(ExpressionNode expression) {
        this.expression = expression;
    }
}

