package org.lpc.s2_parsing.ast.statement;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class IfStatementNode extends StatementNode {
    private final ExpressionNode condition;
    private final StatementNode thenBranch;
    private final StatementNode elseBranch;

    public IfStatementNode(ExpressionNode condition, StatementNode thenBranch, StatementNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("If(").append(condition.toString(depth + 1));
        sb.append("\n");
        sb.append(indent(depth + 1)).append("Then: ");
        sb.append(indent(depth + 1)).append(thenBranch.toString(depth + 1));
        if (elseBranch != null) {
            sb.append(indent(depth + 1)).append("Else: ");
            sb.append(indent(depth + 1)).append(elseBranch.toString(depth + 1));
        }
        return sb.toString();
    }
}

