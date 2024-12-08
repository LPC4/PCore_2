package org.lpc.s2_parsing.ast.statement;

import org.lpc.s2_parsing.ast.expression.ExpressionNode;

public class ExpressionStatementNode extends StatementNode {
    private final ExpressionNode expression;

    public ExpressionStatementNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "ExpressionStatement(" + expression.toString(depth + 1) + "\n" + indent(depth) + ")";
    }
}
