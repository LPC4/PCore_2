package org.lpc.s2_parsing.ast.statement;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class ReturnStatementNode extends StatementNode {
    private final ExpressionNode returnValue;

    public ReturnStatementNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "Return(" + returnValue.toString(depth + 1) + "\n" + indent(depth) + ")\n";
    }
}

