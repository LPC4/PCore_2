package org.lpc.s1_tokenization;

import lombok.Getter;

@Getter
public class Token {
    private final TokenType type;
    private final String value;
    private final int line;
    private final int column;

    public Token(TokenType type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("Token{type=%s, value='%s', line=%d, column=%d}", type, value, line, column);
    }
}

