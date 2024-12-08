package org.lpc;

import org.lpc.s1_tokenization.Lexer;
import org.lpc.s1_tokenization.Token;
import org.lpc.s2_parsing.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("""
                program main;
                
                func add(a: int, b: int) -> int {
                
                    set c: int = a + b;
                    ++x;
                    add(a, b);
                    return c;
                
                    set b: [int, 10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

                    a = null;
                
                    if (a == b) {
                        return a;
                    } else {
                        return b;
                    }
                
                    a = 0;
               
                    while (a < b) {
                        add(a, b);
                    }
                }
                """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
    }

    public static void printTokens(List<Token> tokens) {
        // print value : type with same indentation
        int maxTokenLength = tokens.stream().mapToInt(token -> token.getValue().length()).max().orElse(0);
        for (Token token : tokens) {
            String value = token.getValue();
            String type = token.getType().toString();
            System.out.printf("%-" + maxTokenLength + "s : %s%n", value, type);
        }
    }
}