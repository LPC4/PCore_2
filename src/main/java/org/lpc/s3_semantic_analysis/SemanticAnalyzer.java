package org.lpc.s3_semantic_analysis;

import org.lpc.s2_parsing.ast.*;

public class SemanticAnalyzer {
    private final SymbolTable symbolTable = new SymbolTable();

    public void analyze(ProgramNode program) {
        for (ASTNode stmt : program.getChildren()) {
            analyzeNode(stmt);
        }

        System.out.println("Semantic Analysis Complete");
    }

    private void analyzeNode(ASTNode stmt) {
        if (stmt instanceof GlobalVariableDeclarationNode) {
            analyzeGlobalVariableDeclaration((GlobalVariableDeclarationNode) stmt);
        } else if (stmt instanceof FunctionDeclarationNode) {
            analyzeFunctionDeclaration((FunctionDeclarationNode) stmt);
        } else if (stmt instanceof StructDeclarationNode) {
            analyzeStructDeclaration((StructDeclarationNode) stmt);
        } else {
            throw new RuntimeException("Unknown node type: " + stmt.getClass());
        }
    }

    private void analyzeGlobalVariableDeclaration(GlobalVariableDeclarationNode node) {
        if (symbolTable.isDeclared(node.getName())) {
            throw new RuntimeException("Variable already declared: " + node.getName());
        } else {
            symbolTable.declare(node.getName(), new SymbolTable.Symbol(node.getName(), node.getType().getTypeName()));
        }
    }

    private void analyzeFunctionDeclaration(FunctionDeclarationNode node) {
        if (symbolTable.isDeclared(node.getName())) {
            throw new RuntimeException("Function already declared: " + node.getName());
        } else {
            symbolTable.declare(node.getName(), new SymbolTable.Symbol(node.getName(), node.getReturnType().getTypeName()));
        }
    }

    private void analyzeStructDeclaration(StructDeclarationNode node) {
        if (symbolTable.isDeclared(node.getName())) {
            throw new RuntimeException("Struct already declared: " + node.getName());
        } else {
            symbolTable.declare(node.getName(), new SymbolTable.Symbol(node.getName(), "struct"));
        }
    }
}
