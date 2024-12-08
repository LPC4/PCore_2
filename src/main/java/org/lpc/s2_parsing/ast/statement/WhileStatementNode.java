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

    @Override
    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(indent(depth)).append("While(").append(condition.toString(depth + 1));
        sb.append("\n");
        sb.append(indent(depth + 1)).append("Body: ");
        sb.append(indent(depth + 1)).append(body.toString(depth + 1));
        sb.append(indent(depth)).append(")\n");
        return sb.toString();
    }
}
