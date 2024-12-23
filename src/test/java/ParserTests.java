import org.junit.jupiter.api.Test;
import org.lpc.s1_tokenization.Lexer;
import org.lpc.s1_tokenization.Token;
import org.lpc.s2_parsing.Parser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void testFunctionCalls1() {
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

    @Test
    public void testFunctionDeclarations1() {
        Lexer lexer = new Lexer("""
                program main;
                
                func add(param1: int, b: int) -> int {
                    set c: int = a + b;
                    ++x;
                    add(a, b);
                    return c;
                    
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
        //printTokens(tokens);

        Parser parser = new Parser(tokens);
    }

    @Test
    public void testArrays(){
        Lexer lexer = new Lexer("""
                program main;
                
                set a: [int, 10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                set b: [[int, 10], 10] = {a, a, a, a, a, a, a, a, a, a};
                set c: [[int, 10], 10] = {a, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, a, a, a, a, a, a, a, a};
                set d: [[[int, 10], 10], 10] = {b, b, b, b, b, b, b, b, b, b};
                
                set x: int = 10;
                set e: [int, x] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                set f: [[int, x], 10] = {e, e, e, e, e, e, e, e, e, e};
                set g: [[int, x], 10] = {e, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, e, e, e, e, e, e, e, e};
                
                func add(param1: [int, 10], b: [int, 10]) -> [int, 10] {
                    set c: [int, 10] = a + b;
                    set d: [[int, 10], 10] = {c, c, c, c, c, c, c, c, c, c};
                    return c;
                }
                
                """
        );

        List<Token> tokens = lexer.tokenize();
        //printTokens(tokens);

        Parser parser = new Parser(tokens);
    }

    @Test
    public void testStructs(){
        Lexer lexer = new Lexer("""
                program main;
                
                struct Person {
                    set name: string;
                    set age: int;
                }
                
                set p: Person = new Person("John", 25);
                set p2: Person = new Person(p.name, p.age);
                set p3: Person = new Person(p2.name, p2.age);
                
                func getPersonName(person: Person) -> string {
                    return person.name;
                }
                
                func test() -> void {
                    set p: Person = new Person("John", 25);
                    set name: string = getPersonName(p);
               
                    set p2: Person = new Person(p.name, p.age);
                }
                """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
    }

    @Test
    public void testPointers(){
        Lexer lexer = new Lexer("""
                program main;
                
                set x: int = 5;
                set p: ^int = &x;
                set y: int = ^p;
                set z: ^int = malloc(10 * sizeof(int));
                
                func main() -> void {
                    set a: int = 5;
                    set b: int = 10;
                    set p: ^int = &a;
                    set q: ^int = &b;
                    set r: ^int = add(p, q);
                    free(r);
                }
                """
        );

        List<Token> tokens = lexer.tokenize();
        //printTokens(tokens);

        Parser parser = new Parser(tokens);
    }





    @Test
    public void testVariableDeclarations() {
        Lexer lexer = new Lexer("""
            program main;
            
            set x: int = 5;
            const y: i8 = 10;
            const z: string = "hello";
            const w: bit = 1;
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
    }

    @Test
    public void testArithmeticOperations() {
        Lexer lexer = new Lexer("""
            program main;
            
            set a: int = 5 + 10;
            set b: int = 5 * 10;
            set c: int = 5 / 10;
            set d: int = 5 - 10;
            set e: int = (5 + 10) * 2;
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
    }

    @Test
    public void testLogicalOperations() {
        Lexer lexer = new Lexer("""
            program main;
            
            set test1: bit = 5 > 10;
            set test2: bit = 5 < 10;
            set test3: bit = 5 >= 10;
            set test4: bit = 5 <= 10;
            set test5: bit = 5 == 10;
            set test6: bit = 5 != 10;
            set test11: bit = 5 > 10 && 10 < 5;
            set test12: bit = 5 > 10 || 10 < 5;
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
    }

    @Test
    public void testFunctionCalls() {
        Lexer lexer = new Lexer("""
            program main;
            
            set x: int = x;
            set y: int = x(x(a) * 5);
            set z: int = x(x(a) * 5) + 10;
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
    }

    @Test
    public void testFunctionDeclarations() {
        Lexer lexer = new Lexer("""
            program main;
            
            func add(param1: int, b: int) -> int {
                set c: int = a + b;
                add(a, b);
                return c;
            }
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
    }

    @Test
    public void testArrays2() {
        Lexer lexer = new Lexer("""
            program main;
            
            set a: [int, 10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            set b: [[int, 10], 10] = {a, a, a, a, a, a, a, a, a, a};
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
    }

    @Test
    public void testStructs2() {
        Lexer lexer = new Lexer("""
            program main;
            
            struct Person {
                set name: string;
                set age: int;
            }
            
            set p: Person = new Person("John", 25);
            set p2: Person = new Person(p.name, p.age);
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
    }

    @Test
    public void testPointers2() {
        Lexer lexer = new Lexer("""
            program main;
            
            set x: int = 5;
            set p: ^int = &x;
            set y: int = ^p;
            set z: ^int = malloc(10 * sizeof(int));
            
            func add(param1: ^int, b: ^int) -> ^int {
                set c: ^int = a + b;
                return c;
            }
            
            func main() -> void {
                set a: int = 5;
                set b: int = 10;
                set p: ^int = &a;
                set q: ^int = &b;
                set r: ^int = add(p, q);
                
                free(r);
            }
            """
        );

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        assertNotNull(parser); // Check that the parser is instantiated
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
