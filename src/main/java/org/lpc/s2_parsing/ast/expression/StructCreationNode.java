package org.lpc.s2_parsing.ast.expression;

import lombok.Getter;
import java.util.List;

@Getter
public class StructCreationNode extends ExpressionNode {
    private final String structType;
    private final List<ExpressionNode> arguments;

    public StructCreationNode(String structType, List<ExpressionNode> arguments) {
        this.structType = structType;
        this.arguments = arguments;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "StructCreationNode(" + structType + ")";
    }
}

