package org.synapse.modules.stimuli;

import org.synapse.core.Stimulus;

// Example stimulus that triggers once per second for 20s
public class CounterStimulus extends Stimulus
{

    @Override
    protected void main()
    {
        int i = 0;
        while(i < 20)
        {
            i++;
            publish("msg", Integer.toString(i));
            trigger();

            try 
            {
                Thread.sleep(1000);
            } 
            catch (InterruptedException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
