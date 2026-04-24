package org.synapse.core.symbols;

import java.util.HashMap;

public class SymbolDeclarations 
{
    public static class SymbolDeclaration
    {
        public Class<?> clazz;
        public String name;

        public SymbolDeclaration(String name, Class<?> clazz)
        {
            this.name = name;
            this.clazz = clazz;
        }
    }

    HashMap<String, SymbolDeclaration> declarations = new HashMap<>();    

    public void declareSymbol(SymbolDeclaration def)
    {
        declarations.put(def.name, def);
    }

    public void declareSymbol(String name, Class<?> clazz)
    {
        SymbolDeclaration s = new SymbolDeclaration(name, clazz);
        declareSymbol(s);
    }

    public SymbolDeclaration get(String name)
    {
        return declarations.get(name);
    }
}
