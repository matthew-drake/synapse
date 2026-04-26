package org.synapse.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// The broker is the middleman that is responsible for actually running Synapse
// The Broker has a script that is used to run all Stimuli which then trigger responses

public class Broker 
{
    final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    Script script;

    public void receive(Stimulus stimulus, StimulusData data)
    {
        if(script != null)
        {
            for(Response response : script.getResponses(stimulus))
            {
                response.trigger(data);
            }
        }
    }

    public void start(Script script) throws InterruptedException
    {
        this.script = script;
        // executor.invokeAll(script.getStimuli());

        // For debugging: (single threaded version of invokeAll)
        for (Stimulus stimulus : script.getStimuli()) {
            stimulus.call();
        }
    }


}
