package org.synapse.core.symbols;

import java.util.HashMap;

// The symbol definitions object consists of a set of Symbols
// These symbols can be required by responses and exported by stimuli
// A stimulus that defines specific symbol is required to export it.

// An example is the print response can require a string symbol called msg.
// These effectively work as parameter requirements.

// This is going to work internally to move data between stimulus and response objects in a guaranteed way
// We can use reflection to unroll this automatically into a response method based on parameter types in the future but this is the backbone

public class SymbolDefinitions 
{
    // This pairs a name and its associated class token
    public static class SymbolDefinition
    {
        Class<?> clazz;
        String name;
    }

    HashMap<String, SymbolDefinition> definitions = new HashMap<>();

    void defineSymbol(SymbolDefinition def)
    {
        definitions.put(def.name, def);
    }

    SymbolDefinition getDefinition(String name)
    {
        return definitions.get(name);
    }


}
