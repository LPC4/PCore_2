package org.lpc.s3_semantic_analysis;

import org.lpc.s3_semantic_analysis.symbols.Symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    private final Stack<Map<String, Symbol>> scopes = new Stack<>();

    public SymbolTable() {
        // Push the global scope
        enterScope();
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopes.pop();
    }

    public void declare(String name, Symbol symbol) {
        scopes.peek().put(name, symbol);
    }

    public Symbol lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Symbol symbol = scopes.get(i).get(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null;
    }

    public boolean isDeclared(String name) {
        return lookup(name) != null;
    }
}
