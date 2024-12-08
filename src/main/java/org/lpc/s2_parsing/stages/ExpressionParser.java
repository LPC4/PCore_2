package org.lpc.s2_parsing.stages;

import lombok.Getter;
import org.lpc.s1_tokenization.Token;
import org.lpc.s1_tokenization.TokenType;
import org.lpc.s2_parsing.ast.expression.*;

import java.util.ArrayList;
import java.util.List;


public class ExpressionParser {
    private final List<Token> tokens;
    @Getter
    private int current;

    public ExpressionParser(List<Token> tokens, int current) {
        this.tokens = tokens;
        this.current = current;
    }

    public ExpressionNode parseExpression(int current) {
        this.current = current;
        return parseLogicalOr();
    }

    private ExpressionNode parseLogicalOr() {
        ExpressionNode expr = parseLogicalAnd();

        while (match(TokenType.OR)) {
            Token operator = previous();
            ExpressionNode right = parseLogicalAnd();
            expr = new LogicalExpressionNode(expr, operator.getValue(), right);
        }

        return expr;
    }

    private ExpressionNode parseLogicalAnd() {
        ExpressionNode expr = parseEquality();

        while (match(TokenType.AND)) {
            Token operator = previous();
            ExpressionNode right = parseEquality();
            expr = new LogicalExpressionNode(expr, operator.getValue(), right);
        }

        return expr;
    }

    private ExpressionNode parseEquality() {
        ExpressionNode expr = parseComparison();

        while (match(TokenType.EQUAL_EQUAL, TokenType.NOT_EQUAL)) {
            Token operator = previous();
            ExpressionNode right = parseComparison();
            expr = new BinaryExpressionNode(expr, operator.getValue(), right);
        }

        return expr;
    }

    private ExpressionNode parseComparison() {
        ExpressionNode expr = parseAddition();

        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            Token operator = previous();
            ExpressionNode right = parseAddition();
            expr = new BinaryExpressionNode(expr, operator.getValue(), right);
        }

        return expr;
    }

    private ExpressionNode parseAddition() {
        ExpressionNode expr = parseMultiplication();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            ExpressionNode right = parseMultiplication();
            expr = new BinaryExpressionNode(expr, operator.getValue(), right);
        }

        return expr;
    }

    private ExpressionNode parseMultiplication() {
        ExpressionNode expr = parseUnary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            ExpressionNode right = parseUnary();
            expr = new BinaryExpressionNode(expr, operator.getValue(), right);
        }

        return expr;
    }

    private ExpressionNode parseUnary() {
        if (match(TokenType.NOT, TokenType.MINUS, TokenType.PLUS_PLUS, TokenType.MINUS_MINUS)) {
            Token operator = previous();
            ExpressionNode right = parseUnary();
            return new UnaryExpressionNode(operator.getValue(), right);
        }

        return parsePrimary();
    }

    private ExpressionNode parsePrimary() {
        System.out.println("Current token: " + peek().getValue() + " Type: " + peek().getType());

        if (match(TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.CHAR)) {
            System.out.println("Literal detected");
            return new LiteralNode(previous().getValue());
        }
        if (match(TokenType.LEFT_PAREN)) {
            System.out.println("Grouping detected");
            ExpressionNode expr = parseExpression(current);
            consume(TokenType.RIGHT_PAREN, "Expect ')' after expression");
            return new GroupingNode(expr);
        }
        if (check(TokenType.IDENTIFIER)) {
            Token identifierToken = advance(); // Advance the current position to consume the identifier
            if (check(TokenType.LEFT_PAREN)) {
                System.out.println("Function call detected");
                ExpressionNode callee = new IdentifierNode(identifierToken.getValue());
                return parseFunctionCall(callee);
            }
            System.out.println("Identifier detected");
            return new IdentifierNode(identifierToken.getValue());
        }

        throw new RuntimeException("Unexpected token: " + peek().getValue());
    }

    private ExpressionNode parseFunctionCall(ExpressionNode callee) {
        List<ExpressionNode> arguments = new ArrayList<>();
        consume(TokenType.LEFT_PAREN, "Expect '(' after function name");
        if (!check(TokenType.RIGHT_PAREN)) {
            do {
                arguments.add(parseExpression(current));
            } while (match(TokenType.COMMA));
        }
        consume(TokenType.RIGHT_PAREN, "Expect ')' after arguments");
        return new FunctionCallExpressionNode(((IdentifierNode) callee).getName(), arguments);
    }

    // Other helper methods...

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean match(TokenType type, int index) {
        if (index >= tokens.size()) return false;
        return tokens.get(index).getType() == type;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException(message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}

