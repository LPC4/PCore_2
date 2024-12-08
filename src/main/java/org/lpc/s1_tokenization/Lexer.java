package org.lpc.s1_tokenization;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int position = 0;
    private int line = 1;
    private int column = 1;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        while (position < source.length()) {
            char currentChar = source.charAt(position);

            if (Character.isWhitespace(currentChar)) {
                handleWhitespace(currentChar);
            } else if (Character.isDigit(currentChar)) {
                handleNumber();
            } else if (Character.isLetter(currentChar)) {
                handleIdentifierOrKeyword();
            } else if (currentChar == '"') {
                handleStringLiteral();
            } else if (currentChar == '\'') {
                handleCharLiteral();
            } else {
                handleSymbols();
            }
        }
        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    private void handleCommentOrSlash() {
        advance(); // consume the '/'
        if (match('/')) {
            // Single-line comment
            while (peek() != '\n' && !isAtEnd()) advance();
        } else {
            tokens.add(new Token(TokenType.SLASH, "/", line, column));
        }
    }

    private void handleStringLiteral() {
        advance(); // consume the opening quote
        int start = position;
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }
        if (isAtEnd()) throw new RuntimeException("Unterminated string literal at line " + line + ", column " + column);
        String value = source.substring(start, position);
        advance(); // consume the closing quote
        tokens.add(new Token(TokenType.STRING, value, line, column));
    }

    private void handleCharLiteral() {
        advance(); // consume the opening quote
        if (isAtEnd() || source.charAt(position) == '\n') throw new RuntimeException("Unterminated character literal at line " + line + ", column " + column);
        char value = source.charAt(position);
        advance();
        if (source.charAt(position) != '\'') throw new RuntimeException("Invalid character literal at line " + line + ", column " + column);
        advance(); // consume the closing quote
        tokens.add(new Token(TokenType.CHAR, String.valueOf(value), line, column));
    }

    private void handleSymbols() {
        char currentChar = source.charAt(position);
        switch (currentChar) {
            case '{':
                tokens.add(new Token(TokenType.LEFT_BRACE, "{", line, column));
                break;
            case '}':
                tokens.add(new Token(TokenType.RIGHT_BRACE, "}", line, column));
                break;
            case '(':
                tokens.add(new Token(TokenType.LEFT_PAREN, "(", line, column));
                break;
            case ')':
                tokens.add(new Token(TokenType.RIGHT_PAREN, ")", line, column));
                break;
            case '[':
                tokens.add(new Token(TokenType.LEFT_BRACKET, "[", line, column));
                break;
            case ']':
                tokens.add(new Token(TokenType.RIGHT_BRACKET, "]", line, column));
                break;
            case ',':
                tokens.add(new Token(TokenType.COMMA, ",", line, column));
                break;
            case ':':
                tokens.add(new Token(TokenType.COLON, ":", line, column));
                break;
            case ';':
                tokens.add(new Token(TokenType.SEMICOLON, ";", line, column));
                break;
            case '.':
                tokens.add(new Token(TokenType.DOT, ".", line, column));
                break;
            case '^':
                tokens.add(new Token(TokenType.CARET, "^", line, column));
                break;
            case '+':
                addTokenWithConditional(TokenType.PLUS, TokenType.PLUS_PLUS, '+');
                break;
            case '-':
                addTokenWith2Conditional(TokenType.MINUS, TokenType.MINUS_MINUS, TokenType.ARROW, '-', '>');
                break;
            case '*':
                tokens.add(new Token(TokenType.STAR, "*", line, column));
                break;
            case '/':
                handleCommentOrSlash();
                break;
            case '&':
                addTokenWithConditional(TokenType.AMPERSAND, TokenType.AND, '&');
                break;
            case '|':
                addTokenWithConditional(null, TokenType.OR, '|');
                break;
            case '=':
                addTokenWithConditional(TokenType.EQUAL, TokenType.EQUAL_EQUAL, '=');
                break;
            case '!':
                addTokenWithConditional(TokenType.NOT, TokenType.NOT_EQUAL, '=');
                break;
            case '<':
                addTokenWithConditional(TokenType.LESS, TokenType.LESS_EQUAL, '=');
                break;
            case '>':
                addTokenWithConditional(TokenType.GREATER, TokenType.GREATER_EQUAL, '=');
                break;
            default:
                throw new RuntimeException("Unexpected character: " + currentChar + " at line " + line + ", column " + column);
        }
        advance();
    }

    private void handleWhitespace(char currentChar) {
        if (currentChar == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
        advance();
    }

    private void handleNumber() {
        int start = position;
        boolean isFloat = false;

        while (position < source.length() && (Character.isDigit(source.charAt(position)) || source.charAt(position) == '.')) {
            if (source.charAt(position) == '.') {
                if (isFloat)
                    throw new RuntimeException("Invalid float number at line " + line + ", column " + column);
                isFloat = true;
            }
            advance();
        }

        String value = source.substring(start, position);
        TokenType type = isFloat ? TokenType.FLOAT : TokenType.INTEGER;
        tokens.add(new Token(type, value, line, column));
    }

    private void handleIdentifierOrKeyword() {
        int start = position;
        while (position < source.length() && Character.isLetterOrDigit(source.charAt(position))) {
            advance();
        }
        String value = source.substring(start, position);
        TokenType type = switch (value) {
            case "program" -> TokenType.PROGRAM;
            case "const" -> TokenType.CONST;
            case "set" -> TokenType.SET;
            case "func" -> TokenType.FUNC;
            case "return" -> TokenType.RETURN;
            case "if" -> TokenType.IF;
            case "else" -> TokenType.ELSE;
            case "while" -> TokenType.WHILE;
            case "do" -> TokenType.DO;
            case "for" -> TokenType.FOR;
            case "alloc" -> TokenType.ALLOC;
            case "free" -> TokenType.FREE;
            case "true", "false" -> TokenType.BOOLEAN;
            case "struct" -> TokenType.STRUCT;
            case "null" -> TokenType.NULL;
            case "new" -> TokenType.NEW;
            case "print" -> TokenType.PRINT;

            default -> TokenType.IDENTIFIER;
        };
        tokens.add(new Token(type, value, line, column));
    }

    private void addTokenWithConditional(TokenType singleCharType, TokenType doubleCharType, char expected) {
        if (peekNext() == expected) {
            tokens.add(new Token(doubleCharType, String.valueOf(source.charAt(position)) + expected, line, column));
            advance();
        } else {
            if(singleCharType == null) {
                throw new RuntimeException("Unexpected character: " + source.charAt(position) + " at line " + line + ", column " + column);
            }
            tokens.add(new Token(singleCharType, String.valueOf(source.charAt(position)), line, column));
        }
    }

    private void addTokenWith2Conditional(TokenType singleCharType, TokenType doubleCharType1, TokenType doubleCharType2, char expected1, char expected2) {
        if (peekNext() == expected1) {
            tokens.add(new Token(doubleCharType1, String.valueOf(source.charAt(position)) + expected1, line, column));
            advance();
        } else if (peekNext() == expected2) {
            tokens.add(new Token(doubleCharType2, String.valueOf(source.charAt(position)) + expected2, line, column));
            advance();
        } else {
            if(singleCharType == null) {
                throw new RuntimeException("Unexpected character: " + source.charAt(position) + " at line " + line + ", column " + column);
            }
            tokens.add(new Token(singleCharType, String.valueOf(source.charAt(position)), line, column));
        }
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(position) != expected) return false;
        advance();
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(position);
    }

    private char peekNext() {
        if (position + 1 >= source.length()) return '\0';
        return source.charAt(position + 1);
    }

    private void advance() {
        position++;
        column++;
    }

    private boolean isAtEnd() {
        return position >= source.length();
    }
}
