package org.synapse.core;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Stimuli listen for something. They are run by the Broker
public abstract class Stimulus implements Callable<Void>
{

    Broker broker;

    protected final Logger logger = LogManager.getLogger(this.getClass());
    private StimulusData data = new StimulusData();

    // Calling trigger causes connected responses to happen
    protected void trigger()
    {
        if(broker != null)
        {
            // broker.receive(this, symbolData);
            broker.receive(this, data);
        }
    }
    
    abstract protected void main();

    // Call will be handled by base class and then call main on subclass
    // This lets us hide the call function from the Stimulus
    public Void call()
    {
        main();

        return null;
    }

    protected void publish(String name, Object value)
    {
        data.put(name, value);
    }

    public void pair(Broker broker)
    {
        this.broker = broker;
    }

}
