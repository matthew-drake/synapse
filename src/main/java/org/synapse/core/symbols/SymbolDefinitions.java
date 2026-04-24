package org.synapse.core.symbols;

import java.util.HashMap;
import java.util.Optional;

import org.synapse.core.symbols.SymbolDeclarations.SymbolDeclaration;

public class SymbolDefinitions 
{
    public static class SymbolDefinedWithoutDeclarationException extends Exception
    {
        SymbolDefinedWithoutDeclarationException(SymbolDefinition s)
        {
            super("Cannot define symbol " + s.name() + ", without declaring it first");
        }
    }

    public static class SymbolDefinitionTypeMismatchException extends Exception
    {
        SymbolDefinitionTypeMismatchException(SymbolDefinition s)
        {
            super("Type of symbol " + s.name() + ", does not match its declared type");
        }
    }

    public static class SymbolDefinition
    {
        private String name;
        private Object value;

        public String name()
        {
            return name;
        }

        public <T> Optional<T> value(Class<T> clazz) 
        {
            if(clazz.isInstance(value))
            {
                return Optional.of(clazz.cast(value));
            }
            else
            {
                return Optional.empty();
            }
        }

        public SymbolDefinition(String name, Object value)
        {
            this.name = name;
            this.value = value;
        }
    }

    HashMap<String, SymbolDefinition> definitions = new HashMap<>();

    public void defineSymbol(String name, Object value, SymbolDeclarations declarations) throws SymbolDefinitionTypeMismatchException, SymbolDefinedWithoutDeclarationException
    {
        SymbolDefinition s = new SymbolDefinition(name, value);
        defineSymbol(s, declarations);
    }

    public void defineSymbol(SymbolDefinition s, SymbolDeclarations declarations) throws SymbolDefinitionTypeMismatchException, SymbolDefinedWithoutDeclarationException
    {
        SymbolDeclaration associatedDeclaration = declarations.get(s.name);
        if(associatedDeclaration != null)
        {
            if(associatedDeclaration.clazz.isInstance(s.value))
            {
                definitions.put(s.name(), s);
            }
            else
            {
                throw new SymbolDefinitionTypeMismatchException(s);
            }
        }
        else
        {
            throw new SymbolDefinedWithoutDeclarationException(s);
        }
    }

    public SymbolDefinition get(String name)
    {
        return definitions.get(name);
    }
}
