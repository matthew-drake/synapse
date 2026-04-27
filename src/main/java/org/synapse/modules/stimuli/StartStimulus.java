package org.synapse.modules.stimuli;

import org.synapse.core.Stimulus;

public class StartStimulus extends Stimulus
{
    @Override
    protected void main()
    {
        logger.info("Logging from a derived stimulus");
        publish("msg", "Welcome to Synapse");
        trigger();
    }
}
