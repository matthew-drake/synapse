package org.synapse.modules.stimuli;

import org.synapse.core.Stimulus;

public class StartStimulus extends Stimulus
{
    @Override
    protected void main()
    {
        publish("msg", "Welcome to Synapse");
        trigger();
    }
}
