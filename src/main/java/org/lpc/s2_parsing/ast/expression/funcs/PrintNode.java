package org.lpc.s2_parsing.ast.expression.funcs;

import org.lpc.s2_parsing.ast.expression.ExpressionNode;

public class PrintNode extends ExpressionNode {
    private final ExpressionNode expression;

    public PrintNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "Print(" + expression.toString(depth + 1) + "\n" + indent(depth) + ")";
    }
}
