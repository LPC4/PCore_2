package org.lpc.s2_parsing.ast.expression;

import lombok.Getter;

import java.util.List;

@Getter
public class FunctionCallExpressionNode extends ExpressionNode {
    private final String functionName;
    private final List<ExpressionNode> arguments;

    public FunctionCallExpressionNode(String functionName, List<ExpressionNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("FunctionCallExpressionNode(").append(functionName).append(")\n");
        sb.append(indent(depth + 1)).append("arguments: [");
        for (ExpressionNode argument : arguments) {
            sb.append(argument.toString(depth + 2)).append("\n");
        }
        sb.append(indent(depth + 1)).append("]\n");
        sb.append(indent(depth)).append(")");
        return sb.toString();
    }
}

