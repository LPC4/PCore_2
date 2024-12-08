package org.lpc.s2_parsing.ast;

import lombok.Getter;
import org.lpc.s2_parsing.ast.statement.BlockNode;

import java.util.List;

@Getter
public class FunctionDeclarationNode extends ASTNode {
    private final String name;
    private final String returnType;
    private final List<ParameterNode> parameters;
    private final BlockNode body;

    public FunctionDeclarationNode(String name, String returnType, List<ParameterNode> parameters, BlockNode body) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("Function(").append(name).append(")\n");
        sb.append(indent(depth + 1)).append("returnType: ").append(returnType).append("\n");
        sb.append(indent(depth + 1)).append("parameters: [");
        for (ParameterNode parameter : parameters) {
            sb.append(parameter.toString(depth + 2));
        }
        sb.append("\n");
        sb.append(indent(depth + 1)).append("]");
        sb.append(body.toString(depth + 1));
        return sb.toString();
    }
}
