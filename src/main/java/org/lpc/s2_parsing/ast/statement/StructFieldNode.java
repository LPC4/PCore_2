package org.lpc.s2_parsing.ast.statement;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.type.TypeNode;

@Getter
public class StructFieldNode extends StatementNode {
    private final String fieldName;
    private final TypeNode fieldType;

    public StructFieldNode(String fieldName, TypeNode fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("StructField(").append(fieldName).append(") type: ").append(fieldType.toString(depth + 1));
        return sb.toString();
    }
}

