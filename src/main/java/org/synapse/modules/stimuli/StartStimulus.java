package org.synapse.modules.stimuli;

import org.synapse.core.Stimulus;
import org.synapse.core.symbols.SymbolDeclarations;

public class StartStimulus extends Stimulus
{

    public StartStimulus() 
    {
        super("start");
    }

    // @Override
    // public Void call() 
    // {
    //     store("msg", "Welcome to Synapse");
    //     trigger();
    //     return null;    
    // }

    @Override
    protected void declareSymbols(SymbolDeclarations declarations) 
    {
        declarations.declareSymbol("msg", String.class);
    }

    @Override
    protected void main() throws Exception
    {
        // store("msg", "Welcome to Synapse");
        publishSymbol("msg", "Welcome to Synapse");
        trigger();
    }

    @Override
    protected void init() throws Exception
    {

    }

    @Override
    protected void cleanup() throws Exception
    {

    }
    
}
