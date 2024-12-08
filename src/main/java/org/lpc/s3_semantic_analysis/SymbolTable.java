package org.lpc.s3_semantic_analysis;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> symbols = new HashMap<>();

    public void declare(String name, Symbol symbol) {
        symbols.put(name, symbol);
    }

    public Symbol lookup(String name) {
        return symbols.get(name);
    }

    public boolean isDeclared(String name) {
        return symbols.containsKey(name);
    }

    public record Symbol(String name, String type) {}
}

