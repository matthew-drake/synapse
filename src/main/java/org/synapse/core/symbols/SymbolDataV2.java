package org.synapse.core.symbols;

import org.synapse.core.symbols.SymbolDeclarations.SymbolDeclaration;
import org.synapse.core.symbols.SymbolDefinitions.SymbolDefinedWithoutDeclarationException;
import org.synapse.core.symbols.SymbolDefinitions.SymbolDefinition;
import org.synapse.core.symbols.SymbolDefinitions.SymbolDefinitionTypeMismatchException;

// This version of symbol data supports definition, declaration, and validation
// You should not be able to define a symbol which is not declared

// For God so loved the world that He send His one and only Son so that whosoever believeth in Him 
// shall not perish but have eternal life

// Come to Jesus and live :)

public class SymbolDataV2 
{
    public SymbolDeclarations declarations = new SymbolDeclarations();
    public SymbolDefinitions definitions = new SymbolDefinitions();

    public void defineSymbol(String name, Object value) throws SymbolDefinitionTypeMismatchException, SymbolDefinedWithoutDeclarationException
    {
        definitions.defineSymbol(name, value, declarations);
    }

    public void declareSymbol(String name, Class<?> clazz)
    {
        declarations.declareSymbol(name, clazz);
    }

    public SymbolDefinition getDefinition(String name)
    {
        return definitions.get(name);
    }

    public SymbolDeclaration getDeclaration(String name)
    {
        return declarations.get(name);   
    }
}
