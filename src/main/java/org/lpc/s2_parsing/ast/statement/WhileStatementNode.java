package org.lpc.s2_parsing.ast.statement;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class WhileStatementNode extends StatementNode {
    private final ExpressionNode condition;
    private final StatementNode body;

    public WhileStatementNode(ExpressionNode condition, StatementNode body) {
        this.condition = condition;
        this.body = body;
    }
}
