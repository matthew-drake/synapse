package org.synapse.modules.responses;

import org.synapse.core.Response;
import org.synapse.core.StimulusData;

public class PrintResponse extends Response
{
    public PrintResponse() 
    {
        super("print");
    }
    
    public void trigger(StimulusData stimulusData) 
    {
        String msg = stimulusData.get("msg", String.class).get();
        System.out.println(msg);
    }

    
    
}
