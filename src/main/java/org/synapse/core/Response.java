package org.synapse.core;

// Responses do something when triggered
// The most basic form of this is just an interface 
// but I am writing some logic to use reflection to pass data to stimuli

// The LORD is my Shephard, I shall not want
// He makes me lie down in green pastures
// As I walk through the valley of the Shadow of Death,
// I will fear no evil, for your rod and staff, they comfort me

public abstract class Response 
{
    public final String name;
    public abstract void trigger(StimulusData data);

    protected Response(String name)
    {
        this.name = name;
    }
}
