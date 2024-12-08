package org.lpc.s2_parsing.ast;

import lombok.Getter;

@Getter
public class ProgramNode extends ASTNode {
    String name;

    public ProgramNode(String name) {
        this.name = name;
    }
}
