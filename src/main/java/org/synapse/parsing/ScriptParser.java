package org.synapse.parsing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.synapse.core.Broker;
import org.synapse.core.Response;
import org.synapse.core.Script;
import org.synapse.core.Stimulus;
import org.synapse.parsing.ScriptTokenizer.TokenType;

public class ScriptParser extends RecursiveDescentParser<TokenType>
{
    // These map names to responses/stimuli
    // They are used to save the imported stimuli/responses 
    // so we can construct connections in the script object
    HashMap<String, Stimulus> stimulusMappings = new HashMap<>();
    HashMap<String, Response> responseMappings = new HashMap<>();
   
    // This is a simple recursive descent parser used to parse scripts
    // It should take in a token stream and return a Script object

    // Grammar:

    // <statement> ::= <import> | <connection>
    // <import> ::= "import" <name>
    // <connection> ::= <name> "-->" <name>

    Script result;
    Broker broker;

    void registerImportedClass(String name, Class<?> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        if(Response.class.isAssignableFrom(clazz))
        {
            Constructor<?> constructor;
            constructor = clazz.getDeclaredConstructor();
            Response response = Response.class.cast(constructor.newInstance());
            // responseMappings.put(response.name, response);
            responseMappings.put(name, response);
                
        }
        else if(Stimulus.class.isAssignableFrom(clazz))
        {
            Constructor<?> constructor;
            constructor = clazz.getDeclaredConstructor();
            Stimulus stimulus = Stimulus.class.cast(constructor.newInstance());
            stimulus.pair(broker);
            // stimulusMappings.put(stimulus.name, stimulus);
            stimulusMappings.put(name, stimulus);
        }
    }

    void script(List<Token<TokenType>> tokens) throws SyntaxError, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        init(tokens); // Initialize parser
        
        while(!match(TokenType.EOF))
        {
            if(match(TokenType.LINE_SEPARATOR))
            {
                advance(); // Skip line separators
            }
            else
            {
                statement(); // Process statements
            }
        }

    }

    void statement() throws SyntaxError, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        if(match(TokenType.IMPORT))
        {
            importStatement();
        }
        else if(match(TokenType.DATA))
        {
            connectionStatement();
        }
        else
        {
            error(currentToken());
        }

        // All lines end with line separators
        expect(TokenType.LINE_SEPARATOR);
    }

    void importStatement() throws SyntaxError, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Class<?> clazz = null;
        String name = null;

        expect(TokenType.IMPORT);

        if(match(TokenType.DATA))
        {
            clazz = Class.forName(currentToken().value);
        }

        expect(TokenType.DATA);
        expect(TokenType.AS);

        if(match(TokenType.DATA))
        {
            name = currentToken().value;
        }

        expect(TokenType.DATA);

        registerImportedClass(name, clazz);

    }

    void connectionStatement() throws SyntaxError
    {
        Stimulus stimulus = null;
        Response response = null;

        if(match(TokenType.DATA))
        {
            stimulus = stimulusMappings.get(currentToken().value);
            expect(TokenType.DATA);
        }
        expect(TokenType.CONNECTOR);
        if(match(TokenType.DATA))
        {
            response = responseMappings.get(currentToken().value);
            expect(TokenType.DATA);
        }

        if(stimulus != null && response != null)
        {
            result.addResponse(stimulus, response);
        }

    }

    
    public Script parse(List<Token<TokenType>> tokens, Broker broker) throws SyntaxError, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        this.result = new Script();
        this.broker = broker;

        script(tokens);

        return result;

    }
}
