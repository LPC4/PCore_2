package org.lpc.s3_semantic_analysis.symbols;

import lombok.Getter;
import java.util.List;

@Getter
public class FunctionSymbol extends Symbol {
    private final List<String> parameterTypes;
    private final String returnType;

    public FunctionSymbol(String name, List<String> parameterTypes, String returnType) {
        super(name);
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }
}

