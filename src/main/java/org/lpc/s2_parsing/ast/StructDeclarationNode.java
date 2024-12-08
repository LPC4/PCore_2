package org.lpc.s2_parsing.ast;

import lombok.Getter;
import lombok.ToString;
import org.lpc.s2_parsing.ast.statement.BlockStatementNode;
import org.lpc.s2_parsing.ast.statement.StructFieldNode;

import java.util.List;

@Getter
public class StructDeclarationNode extends ASTNode {
    private final String name;
    private final List<StructFieldNode> fields;

    public StructDeclarationNode(String name, List<StructFieldNode> fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(indent(depth)).append("StructDeclarationNode(").append(name).append(")");
        for (StructFieldNode field : fields) {
            sb.append(field.toString(depth + 1));
        }
        return sb.toString();
    }
}
