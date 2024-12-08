import org.junit.jupiter.api.Test;
import org.lpc.s1_tokenization.Lexer;
import org.lpc.s1_tokenization.Token;
import org.lpc.s2_parsing.Parser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTests {
    @Test
    public void test() {
        Lexer lexer = new Lexer("""
                program main;
                
                set x: int = 5;
                const y: i8 = 10;
                const y: string = "hello";
                const z: bit = 1;
                set a: int = 5 + 10;
                set b: int = 5 * 10;
                set c: int = 5 / 10;
                set d: int = 5 - 10;
                set e: int = (5 + 10) * 2;
                set f: int = 5 + 10 * 2;
                set g: int = 5 + 10 / 2;
                set h: int = 5 * 10 + 2;
                set i: int = 5 * (10 + 2);
                set j: int = 5 * 10 + 2 * 3;
                set test1: bit = 5 > 10;
                set test2: bit = 5 < 10;
                set test3: bit = 5 >= 10;
                set test4: bit = 5 <= 10;
                set test5: bit = 5 == 10;
                set test6: bit = 5 != 10;
                set test7: int = -x;
                set test8: int = -5;

                set test11: bit = 5 > 10 && 10 < 5;
                set test12: bit = 5 > 10 || 10 < 5;
                set test13: bit = 5 > 10 && 10 < 5 || 5 == 5;
                
                set test21: bit = 5 > 10 && 10 < 5 && 5 == 5;
                set test22: bit = 5 > 10 || 10 < 5 || 5 == 5;
                
                set test31: bit = 5 > 10 && (10 < 5 || 5 == 5);
                set test32: bit = 5 > 10 || (10 < 5 && 5 == 5);
                
                set test41: bit = 5 > 10 && (10 < 5 || 5 == 5) && 5 == 5;
                set test42: bit = 5 > 10 || (10 < 5 && 5 == 5) || 5 == 5;
                
                set test51: bit = 5 > 10 && (10 < 5 || 5 == 5) && 5 == 5 && 5 == 5;
                set test52: bit = 5 > 10 || (10 < 5 && 5 == 5) || 5 == 5 || 5 == 5;
                """
        );

        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);

        // no errors, check the AST
    }

    @Test
    public void testFunctionCalls() {
        Lexer lexer = new Lexer("""
                program main;
                
                set x: int = x;
                set y: int = x(x(a) * 5);
                set z: int = x(x(a) * 5) + 10;
                set a: int = x(x(a) * 5) + 10 * 2;
                set b: int = x(x(a) * 5) + 10 * 2 + 5;
                set c: int = x(x(a) * 5) + 10 * 2 + 5 / 2;
                """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);

        // no errors, check the AST
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