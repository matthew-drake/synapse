package org.synapse.core;

import java.util.concurrent.Callable;

import org.synapse.core.symbols.SymbolDataV2;
import org.synapse.core.symbols.SymbolDeclarations;
import org.synapse.core.symbols.SymbolDefinitions.SymbolDefinedWithoutDeclarationException;
import org.synapse.core.symbols.SymbolDefinitions.SymbolDefinitionTypeMismatchException;

// Stimuli listen for something. They are run by the Broker
public abstract class Stimulus implements Callable<Void>
{
    public static class SymbolExportException extends Exception
    {
        SymbolExportException()
        {
            super("Exported symbols do not match defined symbols");
        }
    }

    Broker broker;

    public final String name;

    private SymbolDataV2 symbolData;

    // Calling trigger causes connected responses to happen
    protected void trigger()
    {
        if(broker != null)
        {
            broker.receive(this, symbolData);
        }
    }

    
    abstract protected void declareSymbols(SymbolDeclarations declarations); // Define / Declare symbols
    abstract protected void init() throws Exception; // Initialize the stimulus
    abstract protected void main() throws Exception; // Actually perform the stimulus
    abstract protected void cleanup() throws Exception; // Shut down the stimulus

    // Call will be handled by base class and then stimuli will
    // Define symbols and main class

    public Void call() throws Exception
    {
        declareSymbols(symbolData.declarations);
        try 
        {
            main();    
        } 
        catch (Exception e) 
        {
            throw e;
        }

        return null;
    }

    protected void publishSymbol(String name, Object value) throws SymbolDefinitionTypeMismatchException, SymbolDefinedWithoutDeclarationException
    {
        symbolData.defineSymbol(name, value);
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
