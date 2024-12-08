package org.lpc.s4_code_generation;

import org.lpc.s2_parsing.ast.ProgramNode;

public class CodeGenerator {
    public void generate(ProgramNode program) {
        System.out.println("Generating code for program: " + program.getName());
    }
}
