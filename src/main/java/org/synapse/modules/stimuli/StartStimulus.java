package org.synapse.modules.stimuli;

import org.synapse.core.Stimulus;

public class StartStimulus extends Stimulus
{

    public StartStimulus() 
    {
        super("start");
    }

    @Override
    public Void call() 
    {
        store("msg", "Welcome to Synapse");
        trigger();
        return null;    
    }
    
}
