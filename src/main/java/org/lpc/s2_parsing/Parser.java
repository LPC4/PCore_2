package org.lpc.s2_parsing;

import lombok.Getter;
import org.lpc.s1_tokenization.Token;
import org.lpc.s2_parsing.ast.ProgramNode;
import org.lpc.s2_parsing.stages.*;
import org.lpc.s2_parsing.ast.ASTNode;
import org.lpc.s2_parsing.stages.DeclarationParser;

import java.util.List;

@Getter
public class Parser {
    private final List<Token> tokens;
    // Stages
    Preprocessor preprocessor = new Preprocessor();
    DeclarationParser declarationParser = new DeclarationParser();

    public Parser(List<Token> tokens) {
        // Preprocess tokens
        this.tokens = preprocessor.preprocess(tokens);
    }

    public ProgramNode parse(){
        return declarationParser.parseDeclarations(tokens);
    }
}
