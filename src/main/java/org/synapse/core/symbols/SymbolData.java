package org.synapse.core.symbols;

import java.util.HashMap;

// Revamped version of StimulusData that uses Symbol objects
// It is a thin wrapper around a HashMap
public class SymbolData 
{
    HashMap<String, Symbol> symbols = new HashMap<>();

    void put(Symbol s)
    {
        symbols.put(s.name(), s);
    }

    Symbol get(String name)
    {
        return symbols.get(name);
    }
}
