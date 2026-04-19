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
            broker.receive(this, data);
        }
    }

    abstract public Void call();

    // Store stores data which is then passed to connected responses
    protected void store(String name, Object value)
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
