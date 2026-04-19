package org.synapse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.synapse.core.Broker;
import org.synapse.core.Script;
import org.synapse.parsing.RecursiveDescentParser.SyntaxError;
import org.synapse.parsing.ScriptParser;
import org.synapse.parsing.ScriptTokenizer;
import org.synapse.parsing.ScriptTokenizer.TokenType;
import org.synapse.parsing.Token;

public class Synapse {
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SyntaxError, IOException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
        Broker broker = new Broker();
        ScriptParser parser = new ScriptParser();
        ScriptTokenizer tokenizer = new ScriptTokenizer();

        if(args.length == 1)
        {
            String scriptPath = args[0];
            String scriptContent = Files.readString(Path.of(scriptPath));
            List<Token<TokenType>> tokens = tokenizer.tokenize(scriptContent);
            Script script = parser.parse(tokens, broker);
            broker.start(script);
        }
        else
        {
            System.out.println("Usage: synapse [script-path : string]");
        }
    }
}