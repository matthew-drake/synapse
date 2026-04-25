package org.synapse.modules.stimuli;

import org.synapse.core.Stimulus;

public class StartStimulus extends Stimulus
{

    public StartStimulus() 
    {
        super("start");
    }

    @Override
    protected void main()
    {
        // store("msg", "Welcome to Synapse");
        publish("msg", "Welcome to Synapse");
        trigger();
    }
    
}
