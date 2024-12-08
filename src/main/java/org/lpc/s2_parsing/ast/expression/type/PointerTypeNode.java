package org.lpc.s2_parsing.ast.expression.type;

import lombok.Getter;

@Getter
public class PointerTypeNode extends TypeNode {
    private final TypeNode baseType;

    public PointerTypeNode(TypeNode baseType) {
        super("Pointer");
        this.baseType = baseType;
    }

    @Override
    public String toString(int depth) {
        return baseType.toString(depth)  + "^";
    }
}
