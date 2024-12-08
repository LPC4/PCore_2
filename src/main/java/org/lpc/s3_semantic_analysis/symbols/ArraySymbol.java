package org.lpc.s3_semantic_analysis.symbols;

import lombok.Getter;

@Getter
public class ArraySymbol extends Symbol {
    private final String elementType;
    private final int size;

    public ArraySymbol(String name, String elementType, int size) {
        super(name);
        this.elementType = elementType;
        this.size = size;
    }
}
