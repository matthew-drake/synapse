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

    public void put(String name, Object value)
    {
        data.put(name, value);
    }
}
