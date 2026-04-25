package org.synapse.core;

import java.util.concurrent.Callable;

// Stimuli listen for something. They are run by the Broker
public abstract class Stimulus implements Callable<Void>
{

    Broker broker;

    public final String name;

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

    // Call will be handled by base class and then call main. This lets us use regular void instead of the Void class

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

    protected Stimulus(String name)
    {
        this.name = name;
    }

}
