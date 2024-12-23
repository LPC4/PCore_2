package org.lpc.s2_parsing.ast.expression.math;

import lombok.Getter;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;

@Getter
public class UnaryExpressionNode extends ExpressionNode {
    private final Operator operator;
    private final ExpressionNode operand;

    public UnaryExpressionNode(String operator, ExpressionNode operand) {
        this.operator = toOperator(operator);
        this.operand = operand;
    }

    public UnaryExpressionNode(Operator operator, ExpressionNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    private Operator toOperator(String operator) {
        return switch (operator) {
            case "-" -> Operator.NEGATE;
            case "+" -> Operator.POSITIVE;
            case "++" -> Operator.INCREMENT;
            case "--" -> Operator.DECREMENT;
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    @Getter
    public enum Operator {
        NEGATE("-"),
        POSITIVE("+"),
        INCREMENT("++"),
        DECREMENT("--");

        private final String value;

        Operator(String value) {
            this.value = value;
        }
    }

    @Override
    public String toString(int depth) {
        return "\n" + indent(depth) + "UnaryExpression(" + operator.getValue() + ")" + operand.toString(depth + 1);
    }
}
