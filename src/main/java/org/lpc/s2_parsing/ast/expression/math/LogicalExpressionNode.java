package org.lpc.s2_parsing.ast.expression.math;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class LogicalExpressionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final LogicalOperator operator;
    private final ExpressionNode right;

    public LogicalExpressionNode(ExpressionNode left, String operator, ExpressionNode right) {
        this.left = left;
        this.operator = toLogicalOperator(operator);
        this.right = right;
    }

    public LogicalExpressionNode(ExpressionNode left, LogicalOperator operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    private LogicalOperator toLogicalOperator(String operator) {
        return switch (operator) {
            case "&&" -> LogicalOperator.AND;
            case "||" -> LogicalOperator.OR;
            case "!" -> LogicalOperator.NOT;
            default -> throw new IllegalArgumentException("Unknown logical operator: " + operator);
        };
    }

    @Getter
    public enum LogicalOperator {
        AND("&&"),
        OR("||"),
        NOT("!");

        private final String value;

        LogicalOperator(String value) {
            this.value = value;
        }
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "LogicalExpressionNode(" + operator + ")" + left.toString(depth + 1) + right.toString(depth + 1);
    }
}
