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
}

