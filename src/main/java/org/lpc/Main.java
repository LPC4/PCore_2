package org.lpc;

import org.lpc.s1_tokenization.Lexer;
import org.lpc.s1_tokenization.Token;
import org.lpc.s2_parsing.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("""
                program main;
                
                set arry: [int, 5] = {1, 2, 3, 4, 5};
                set arry2: [[int, 5], 5] = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
                
                func main() -> void {
                    set result: int = add(5, 3);
                    set pNumber: ^int = &number;
                    set memory: ^int = malloc(10 * sizeof(int));
                    set number: int = ^pNumber;
                    free(memory);
                   
                }
                
                struct Person {
                    set name: string;
                    set age: int;
                }
                
                set p: Person = new Person("John", 25);
                set p2: Person = new Person(p.name, p.age);
                set p3: Person = new Person(p2.name, p2.age);
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