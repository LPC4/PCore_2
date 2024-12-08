package org.lpc.s2_parsing.ast.expression;

public class VariableExpressionNode extends ExpressionNode {
    private final String name;
    private final ExpressionNode value;

    public VariableExpressionNode(String name, ExpressionNode value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "assign variable(" + name + ") to" + (value != null ? value.toString(depth + 1) : "");
    }
}
