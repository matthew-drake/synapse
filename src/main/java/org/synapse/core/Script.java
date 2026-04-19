package org.synapse.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// This class represents a Synapse Script
// That is, a series of stimulus and response mappings
public class Script 
{
    HashMap<Stimulus,List<Response>> mappings = new HashMap<>();

    public List<Response> getResponses(Stimulus s)
    {
        return mappings.get(s);
    }

    public void addResponse(Stimulus s, Response r)
    {
        if(mappings.containsKey(s))
        {
            List<Response> responses = mappings.get(s);
            responses.add(r);
            mappings.replace(s, responses);
        }
        else
        {
            LinkedList<Response> responses = new LinkedList<>();
            responses.add(r);
            mappings.put(s, responses);
        }
    }

    public Set<Stimulus> getStimuli()
    {
        return mappings.keySet();
    }

}
