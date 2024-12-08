package org.lpc.s2_parsing.ast.statement;

import java.util.List;

public class BlockNode extends StatementNode {
    List<StatementNode> statements;

    public BlockNode(List<StatementNode> statements) {
        this.statements = statements;

        for (StatementNode statement : statements) {
            addChild(statement);
        }
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("Code Block {");
        for (StatementNode statement : statements) {
            sb.append(statement.toString(depth + 1));
        }
        sb.append(indent(depth)).append("}\n");
        return sb.toString();
    }
}
