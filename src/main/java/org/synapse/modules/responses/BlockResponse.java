package org.synapse.modules.responses;

import org.synapse.core.Response;

public class BlockResponse extends Response
{
    // @Bind(strategy = BindStrategy.Implicit)
    public void main() 
    {
        while(true)
        {
            try 
            {
                Thread.sleep(5000);
                logger.info("Blocking thread");
            } 
            catch (InterruptedException e) {}
        }
    }
}
