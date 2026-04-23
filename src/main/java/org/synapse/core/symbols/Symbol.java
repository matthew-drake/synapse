package org.synapse.core.symbols;

import java.util.Optional;

// A Symbol is a parameter that has a value and a name.
// It can be exported from Stimuli and Required by responses
public class Symbol
{
    private String name;
    private Object value;

    public String name()
    {
        return name;
    }

    // Ultimately we want to get rid of the Optional around any data passed to the response that the stimulus exports.
    // However we still want the optional around any non-guaranteed data.
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

    public Symbol(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }
}
