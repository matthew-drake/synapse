package org.synapse.core;

import java.util.HashMap;
import java.util.Optional;

public class StimulusData
{
    HashMap<String, Object> data = new HashMap<>();
    
    public <T> Optional<T> get(String name, Class<T> clazz)
    {
        Object o = data.get(name);
        if(clazz.isInstance(o))
        {
            return Optional.of(clazz.cast(o));
        }
        else
        {
            return Optional.empty();
        }
    }

    public Optional<Object> getRaw(String name)
    {
        return Optional.ofNullable(data.get(name));
    }

    public boolean contains(String name)
    {
        return data.containsKey(name);
    }

    public void put(String name, Object value)
    {
        data.put(name, value);
    }

    public int size()
    {
        return data.size();
    }
}
