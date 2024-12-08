package org.lpc.s3_semantic_analysis.symbols;

import lombok.Getter;

@Getter
public abstract class Symbol {
    String name;

    public Symbol(String name){
        this.name = name;
    }
}
