package org.lpc.s2_parsing.stages;

import lombok.Getter;
import org.lpc.s1_tokenization.Token;
import org.lpc.s1_tokenization.TokenType;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;
import org.lpc.s2_parsing.ast.statement.*;

import java.util.ArrayList;
import java.util.List;

public class StatementParser {
    private final List<Token> tokens;
    @Getter
    private int current;
    private final ExpressionParser expressionParser;

    public StatementParser(List<Token> tokens, int current, ExpressionParser expressionParser) {
        this.tokens = tokens;
        this.current = current;
        this.expressionParser = expressionParser;
    }

    public BlockStatementNode parseBlock(int current) {
        this.current = current;
        return parseBlock();
    }

    private BlockStatementNode parseBlock() {
        List<StatementNode> statements = new ArrayList<>();
        consume(TokenType.LEFT_BRACE, "Expect '{' before block.");
        while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            statements.add(parseStatement());
        }
        consume(TokenType.RIGHT_BRACE, "Expect '}' after block.");
        return new BlockStatementNode(statements);
    }

    private StatementNode parseStatement() {
        if (check(TokenType.LEFT_BRACE)) {
            return parseBlock();
        }
        if (check(TokenType.CONST) || check(TokenType.SET)) {
            return parseVariableDeclaration();
        }
        if (check(TokenType.IF)) {
            return parseIfStatement();
        }
        if (check(TokenType.WHILE)) {
            return parseWhileStatement();
        }
        if (check(TokenType.RETURN)) {
            return parseReturnStatement();
        }
        System.out.println("Expression statement");
        System.out.println(peek().getValue());
        return parseExpressionStatement();
    }

    private StatementNode parseWhileStatement() {
        consume(TokenType.WHILE, "Expect 'while' keyword.");
        consume(TokenType.LEFT_PAREN, "Expect '(' after 'while'.");
        ExpressionNode condition = expressionParser.parseExpression(current);
        current = expressionParser.getCurrent();
        consume(TokenType.RIGHT_PAREN, "Expect ')' after while condition.");
        StatementNode body = parseStatement();
        return new WhileStatementNode(condition, body);
    }

    private StatementNode parseIfStatement() {
        consume(TokenType.IF, "Expect 'if' keyword.");
        consume(TokenType.LEFT_PAREN, "Expect '(' after 'if'.");
        ExpressionNode condition = expressionParser.parseExpression(current);
        current = expressionParser.getCurrent();
        consume(TokenType.RIGHT_PAREN, "Expect ')' after if condition.");
        StatementNode thenBranch = parseStatement();
        StatementNode elseBranch = null;
        if (match(TokenType.ELSE)) {
            elseBranch = parseStatement();
        }
        return new IfStatementNode(condition, thenBranch, elseBranch);
    }

    private ReturnStatementNode parseReturnStatement() {
        consume(TokenType.RETURN, "Expect 'return' keyword.");
        ExpressionNode returnValue = expressionParser.parseExpression(current);
        current = expressionParser.getCurrent();
        consume(TokenType.SEMICOLON, "Expect ';' after return value.");
        return new ReturnStatementNode(returnValue);
    }

    private ExpressionStatementNode parseExpressionStatement() {
        ExpressionNode expression = expressionParser.parseExpression(current);
        current = expressionParser.getCurrent();
        consume(TokenType.SEMICOLON, "Expect ';' after expression.");
        return new ExpressionStatementNode(expression);
    }

    private VariableDeclarationStatementNode parseVariableDeclaration() {
        VariableDeclarationStatementNode variableDeclaration;

        boolean isConst = check(TokenType.CONST);
        advance();

        consume(TokenType.IDENTIFIER, "Expect variable name.");
        String name = previous().getValue();
        consume(TokenType.COLON, "Expect ':' after variable name.");

        if (check(TokenType.IDENTIFIER)) { // Normal variable declaration
            consume(TokenType.IDENTIFIER, "Expect type name.");
            String type = previous().getValue();
            ExpressionNode initializer = null;
            if (match(TokenType.EQUAL)) {
                initializer = expressionParser.parseExpression(current);
                current = expressionParser.getCurrent();
            }
            consume(TokenType.SEMICOLON, "Expect ';' after variable declaration.");

            variableDeclaration = new VariableDeclarationStatementNode(name, type, initializer);
            if (isConst) {
                variableDeclaration.setConst();
            }
        }
        else if (check(TokenType.LEFT_BRACKET)) { // Array declaration
            consume(TokenType.LEFT_BRACKET, "Expect '[' after variable name.");
            consume(TokenType.IDENTIFIER, "Expect type name.");
            String elementType = previous().getValue();
            consume(TokenType.COMMA, "Expect ',' after array element type.");
            consume(TokenType.INTEGER, "Expect array size.");
            String size = previous().getValue();
            consume(TokenType.RIGHT_BRACKET, "Expect ']' after array size.");
            ExpressionNode initializer = null;
            if (match(TokenType.EQUAL)) {
                initializer = expressionParser.parseExpression(current);
                current = expressionParser.getCurrent();
            }
            consume(TokenType.SEMICOLON, "Expect ';' after variable declaration.");

            variableDeclaration = new VariableDeclarationStatementNode(name, "[" + elementType + ", " + size + "]", initializer);
            if (isConst) {
                variableDeclaration.setConst();
            }
        }
        else if (check(TokenType.CARET)) { // Pointer declaration
            consume(TokenType.CARET, "Expect '^' after variable name.");
            consume(TokenType.IDENTIFIER, "Expect type name.");
            String baseType = previous().getValue();
            ExpressionNode initializer = null;
            if (match(TokenType.EQUAL)) {
                initializer = expressionParser.parseExpression(current);
                current = expressionParser.getCurrent();
            }
            consume(TokenType.SEMICOLON, "Expect ';' after variable declaration.");

            variableDeclaration = new VariableDeclarationStatementNode(name, "^" + baseType, initializer);
            if (isConst) {
                variableDeclaration.setConst();
            }
        }

        else {
            throw new RuntimeException("Unexpected token: " + peek().getValue());
        }

        return variableDeclaration;
    }

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
