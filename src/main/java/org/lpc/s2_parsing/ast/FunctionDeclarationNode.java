package org.lpc.s2_parsing.ast;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.type.TypeNode;
import org.lpc.s2_parsing.ast.statement.BlockStatementNode;

import java.util.List;

@Getter
public class FunctionDeclarationNode extends ASTNode {
    private final String name;
    private final TypeNode returnType;
    private final List<ParameterNode> parameters;
    private final BlockStatementNode body;

    public FunctionDeclarationNode(String name, TypeNode returnType, List<ParameterNode> parameters, BlockStatementNode body) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("Function(").append(name).append(")").append("{ \n");
        sb.append(indent(depth + 1)).append("returnType: ").append(returnType.getTypeName()).append("\n");
        sb.append(indent(depth + 1)).append("parameters: [");
        for (ParameterNode parameter : parameters) {
            sb.append(parameter.toString(depth + 2));
        }
        sb.append("\n");
        sb.append(indent(depth + 1)).append("]");
        sb.append(body.toString(depth + 1));
        sb.append(indent(depth)).append("}\n");

        return sb.toString();
    }
}
