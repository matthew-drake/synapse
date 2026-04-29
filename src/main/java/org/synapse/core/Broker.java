package org.synapse.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The broker is the middleman that is responsible for actually running Synapse
// The Broker has a script that is used to run all Stimuli which then trigger responses

public class Broker 
{
    final static Logger logger = LogManager.getLogger();
    final ExecutorService stimulusExecutor;
    final ExecutorService responseExecutor;

    Script script;

    public void receive(Stimulus stimulus, StimulusData data)
    {
        if(script != null)
        {
            for(Response response : script.getResponses(stimulus))
            {
                response.asyncTrigger(responseExecutor, data);
            }
        }
    }

    public void start(Script script) throws InterruptedException
    {
        this.script = script;
        stimulusExecutor.invokeAll(script.getStimuli());
        
        // For debugging: (single threaded version of invokeAll)
        // for (Stimulus stimulus : script.getStimuli()) 
        // {
        //     stimulus.call();
        // }
    }

    public Broker()
    {
        // Stimulus executor
        ThreadFactory stimulusThreadFactory = Thread.ofVirtual().name("stimulus-", 0).factory();
        stimulusExecutor = Executors.newThreadPerTaskExecutor(stimulusThreadFactory);

        // Response executor
        ThreadFactory responseThreadFactory = Thread.ofVirtual().name("response", 0).factory();
        responseExecutor = Executors.newCachedThreadPool(responseThreadFactory);
    }


}
