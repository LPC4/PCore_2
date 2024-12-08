package org.lpc.s2_parsing.ast.expression.type;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class ArrayTypeNode extends TypeNode {
    private final TypeNode elementType;
    private final ExpressionNode size;

    public ArrayTypeNode(TypeNode elementType, ExpressionNode size) {
        super("Array");
        this.elementType = elementType;
        this.size = size;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(elementType.toString(depth + 1)).append("[").append(size.toString(depth + 2));
        sb.append("\n").append(indent(depth + 1)).append("]");
        return sb.toString();
    }
}
