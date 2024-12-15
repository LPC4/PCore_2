package org.lpc;

import org.lpc.s1_tokenization.Lexer;
import org.lpc.s1_tokenization.Token;
import org.lpc.s2_parsing.Parser;
import org.lpc.s2_parsing.ast.ProgramNode;
import org.lpc.s3_semantic_analysis.SemanticAnalyzer;
import org.lpc.s4_code_generation.CodeGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer(readFile("src/main/resources/preview.pc"));

        List<Token> tokens = lexer.tokenize();
        printTokens(tokens);

        Parser parser = new Parser(tokens);
        ProgramNode program = parser.parse();

        System.out.println(program.toString());

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyze(program);

        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generate(program);
    }

    public static void printTokens(List<Token> tokens) {
        int maxTokenLength = tokens.stream().mapToInt(token -> token.getValue().length()).max().orElse(0);
        for (Token token : tokens) {
            String value = token.getValue();
            String type = token.getType().toString();
            System.out.printf("%-" + maxTokenLength + "s : %s%n", value, type);
        }
    }

    public static String readFile(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}