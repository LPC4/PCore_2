package org.lpc.s2_parsing.stages;

import lombok.Getter;
import org.lpc.s1_tokenization.Token;
import org.lpc.s1_tokenization.TokenType;
import org.lpc.s2_parsing.ast.expression.ExpressionNode;
import org.lpc.s2_parsing.ast.expression.type.ArrayTypeNode;
import org.lpc.s2_parsing.ast.expression.type.PointerTypeNode;
import org.lpc.s2_parsing.ast.expression.type.TypeNode;
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
        // Default case: parse expression statement
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

        TypeNode type = parseType(); // Use parseType to handle complex types (e.g., arrays, pointers)
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

        return variableDeclaration;
    }

    private TypeNode parseType() {
        if (check(TokenType.LEFT_BRACKET)) {
            // Parse array type
            advance();

            // Recursive case: handle nested arrays
            if (check(TokenType.LEFT_BRACKET)) { // [[int, 10], 10]
                TypeNode elementType = parseType();  // Recursive call to handle nested array
                consume(TokenType.COMMA, "Expected ',' in array type");

                ExpressionNode size = parseArraySize(); // Parse array size

                consume(TokenType.RIGHT_BRACKET, "Expected ']' after array size");
                return new ArrayTypeNode(elementType, size);
            }

            // Base case: handle single array
            TypeNode elementType = new TypeNode(consume(TokenType.IDENTIFIER, "Expected array element type").getValue());
            consume(TokenType.COMMA, "Expected ',' in array type");

            ExpressionNode size = parseArraySize(); // Parse array size

            consume(TokenType.RIGHT_BRACKET, "Expected ']' after array size");
            return new ArrayTypeNode(elementType, size);
        }

        // Parse pointer type ^basetype
        if (check(TokenType.CARET)) {
            advance();
            TypeNode baseType = parseType();
            return new PointerTypeNode(baseType);
        }

        // Parse primitive or complex type (non-array)
        return new TypeNode(consume(TokenType.IDENTIFIER, "Expected type name").getValue());
    }

    private ExpressionNode parseArraySize() {
        if (check(TokenType.LEFT_BRACKET)) {
            return parseType(); // Handle nested array size
        } else if (check(TokenType.IDENTIFIER) || check(TokenType.INTEGER)) {
            // Use ExpressionParser to parse more complex expressions (e.g., function calls, identifiers, literals)
            ExpressionParser expressionParser = new ExpressionParser(tokens, current);
            ExpressionNode expression = expressionParser.parseExpression(current);
            this.current = expressionParser.getCurrent(); // Update current position
            return expression;
        } else {
            throw new SyntaxError("Expected array size");
        }
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
        throw new StatementParserException(message, tokens, current);
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

    static class StatementParserException extends RuntimeException {
        public StatementParserException(String message, List<Token> tokens, int current) {
            StringBuilder errorMessage = new StringBuilder("Statement parsing error: ");
            errorMessage.append(message);
            errorMessage.append("Context: ");
            for (int i = Math.max(0, current - 5); i < Math.min(current + 5, tokens.size()); i++) {
                errorMessage.append(tokens.get(i).getValue());
                errorMessage.append(" ");
            }
            throw new RuntimeException(errorMessage.toString());
        }
    }
}
