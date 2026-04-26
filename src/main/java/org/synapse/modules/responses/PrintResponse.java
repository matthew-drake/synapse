package org.synapse.modules.responses;

import org.synapse.core.Response;

public class PrintResponse extends Response
{
    @Bind(strategy = BindStrategy.Annotation)
    public void main(@BindParam("msg") String msg) 
    {
        System.out.println(msg);
    }
}
