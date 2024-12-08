package org.lpc.s2_parsing.ast.statement;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class ReturnStatementNode extends StatementNode {
    private final ExpressionNode returnValue;

    public ReturnStatementNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }
}

