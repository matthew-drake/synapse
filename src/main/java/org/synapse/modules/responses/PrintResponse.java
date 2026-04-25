package org.synapse.modules.responses;

import org.synapse.core.Response;

public class PrintResponse extends Response
{
    public PrintResponse() 
    {
        super("print");
    }

    public void main(String msg) 
    {
        System.out.println(msg);
    }

}
