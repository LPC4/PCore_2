package org.lpc.s3_semantic_analysis.symbols;

import lombok.Getter;
import java.util.Map;

@Getter
public class StructSymbol extends Symbol {
    private final Map<String, String> fields;

    public StructSymbol(String name, Map<String, String> fields) {
        super(name);
        this.fields = fields;
    }
}

