package org.lpc.s2_parsing.ast;

import lombok.Getter;
import lombok.ToString;
import org.lpc.s2_parsing.ast.statement.BlockStatementNode;

@Getter
public class StructDeclarationNode extends ASTNode {
    private final String name;
    private final BlockStatementNode block;

    public StructDeclarationNode(String name, BlockStatementNode block) {
        this.name = name;
        this.block = block;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "StructDeclaration(" + name + ")" + block.toString(depth + 1);
    }
}
