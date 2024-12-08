package org.lpc.s2_parsing.ast.expression;

import lombok.Getter;

@Getter
public class BinaryExpressionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final Operator operator;
    private final ExpressionNode right;

    public BinaryExpressionNode(ExpressionNode left, String operator, ExpressionNode right) {
        this.left = left;
        this.operator = toOperator(operator);
        this.right = right;

        addChild(left);
        addChild(right);
    }

    public BinaryExpressionNode(ExpressionNode left, Operator operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    private Operator toOperator(String operator) {
        return switch (operator) {
            case "+" -> Operator.PLUS;
            case "-" -> Operator.MINUS;
            case "*" -> Operator.STAR;
            case "/" -> Operator.SLASH;
            case "==" -> Operator.EQUAL;
            case "!=" -> Operator.NOT_EQUAL;
            case ">" -> Operator.GREATER;
            case ">=" -> Operator.GREATER_EQUAL;
            case "<" -> Operator.LESS;
            case "<=" -> Operator.LESS_EQUAL;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    @Getter
    public enum Operator {
        PLUS("+"),
        MINUS("-"),
        STAR("*"),
        SLASH("/"),
        EQUAL("=="),
        NOT_EQUAL("!="),
        GREATER(">"),
        GREATER_EQUAL(">="),
        LESS("<"),
        LESS_EQUAL("<=");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "BinaryExpressionNode(" + operator.getSymbol() + ")" + left.toString(depth + 1) + right.toString(depth + 1);
    }
}
