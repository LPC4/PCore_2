

import org.junit.jupiter.api.Test;
import org.lpc.s1_tokenization.Lexer;
import org.lpc.s1_tokenization.Token;
import org.lpc.s1_tokenization.TokenType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTests {

    @Test
    public void testBasicTokens() {
        Lexer lexer = new Lexer("func main() -> { return x + 5; } // comment\n== != && || 'c' \"string\" 42.0");
        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        // Add assertions to check token types and values
        assertEquals(TokenType.FUNC, tokens.get(0).getType());
        assertEquals("func", tokens.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("main", tokens.get(1).getValue());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(2).getType());
        assertEquals("(", tokens.get(2).getValue());

        // ends with EOF
        assertEquals(TokenType.EOF, tokens.get(tokens.size() - 1).getType());
    }

    @Test
    public void testInvalidCharacters() {
        Lexer lexer = new Lexer("| &");
        Exception exception = assertThrows(RuntimeException.class, lexer::tokenize);
        assertTrue(exception.getMessage().contains("Invalid bitwise operator"));
    }

    @Test
    public void testStringLiteral() {
        Lexer lexer = new Lexer("\"Hello, World!\"");
        List<Token> tokens = lexer.tokenize();
        assertEquals(2, tokens.size()); // STRING and EOF
        assertEquals(TokenType.STRING, tokens.get(0).getType());
        assertEquals("Hello, World!", tokens.get(0).getValue());
    }

    @Test
    public void testCharLiteral() {
        Lexer lexer = new Lexer("'c'");
        List<Token> tokens = lexer.tokenize();
        assertEquals(2, tokens.size()); // CHAR and EOF
        assertEquals(TokenType.CHAR, tokens.get(0).getType());
        assertEquals("c", tokens.get(0).getValue());
    }

    @Test
    public void testSingleLineComment() {
        Lexer lexer = new Lexer("// this is a comment\nfunc main() {}");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.FUNC, tokens.get(0).getType());
        assertEquals("func", tokens.get(0).getValue());
    }

    @Test
    public void testNumberTokenization() {
        Lexer lexer = new Lexer("42 42.0");
        List<Token> tokens = lexer.tokenize();
        assertEquals(3, tokens.size()); // INTEGER, FLOAT, and EOF
        assertEquals(TokenType.INTEGER, tokens.get(0).getType());
        assertEquals("42", tokens.get(0).getValue());
        assertEquals(TokenType.FLOAT, tokens.get(1).getType());
        assertEquals("42.0", tokens.get(1).getValue());
    }

    public static void printTokens(List<Token> tokens) {
        int maxTokenLength = tokens.stream().mapToInt(token -> token.getValue().length()).max().orElse(0);
        for (Token token : tokens) {
            String value = token.getValue();
            String type = token.getType().toString();
            System.out.printf("%-" + maxTokenLength + "s : %s%n", value, type);
        }
    }
}
