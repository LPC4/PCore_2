package org.lpc.s3_semantic_analysis.symbols;

import lombok.Getter;

@Getter
public class VariableSymbol extends Symbol {
    private final String type;
    private final boolean isConst;

    public VariableSymbol(String name, String type, boolean isConst) {
        super(name);
        this.type = type;
        this.isConst = isConst;
    }
}

