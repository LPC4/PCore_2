package org.lpc.s2_parsing;

import org.lpc.s1_tokenization.Token;
import org.lpc.s2_parsing.ast.ProgramNode;
import org.lpc.s2_parsing.stages.*;
import org.lpc.s2_parsing.ast.ASTNode;
import org.lpc.s2_parsing.stages.DeclarationParser;

import java.util.List;

public class Parser {
    // Stages
    Preprocessor preprocessor = new Preprocessor();
    DeclarationParser declarationParser = new DeclarationParser();

    public Parser(List<Token> tokens) {
        // Preprocess tokens
        tokens = preprocessor.preprocess(tokens);

        // Parse declarations from tokens
        ProgramNode declarations = declarationParser.parseDeclarations(tokens);

        // Print AST
        System.out.println(declarations.toString(0));
    }

}
