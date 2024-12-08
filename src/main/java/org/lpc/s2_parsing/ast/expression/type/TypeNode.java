package org.lpc.s2_parsing.ast.expression.type;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class TypeNode extends ExpressionNode {
    private final String typeName;

    public TypeNode(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString(int depth) {
        return typeName;
    }
}

