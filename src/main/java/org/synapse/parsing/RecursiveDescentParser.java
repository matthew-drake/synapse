package org.synapse.parsing;

import java.util.List;

public abstract class RecursiveDescentParser <T extends Enum<T>>
{
    // This class provides RDP plumbing methods
    // It does not enforce a specific grammar or parse function

    public static class SyntaxError extends Exception
    {
        SyntaxError(Token<?> t)
        {
            super("Syntax error on token: " + t.type.name() + ", " + t.value);
        }
    }

    private List<Token<T>> tokens = null;
    private int currentTokenIndex = -1;

    // RDP Plumbing functions

    protected void advance()
    {
        currentTokenIndex++;
    }

    protected void error(Token<T> t) throws SyntaxError // Throw an error
    {
        cleanup();
        throw new SyntaxError(t);
    }

    protected Token<T> currentToken() // Convenience method for currentToken
    {
        return tokens.get(currentTokenIndex);
    }


    protected boolean match(T type) // Does current token match type
    {
        return (currentToken().type == type);
    }

    protected void expect(T type) throws SyntaxError // Enforce current token matches this type, if not, error
    {
        if(currentToken().type == type)
        {
            advance();
        }
        else
        {
            error(currentToken());
        }
    }

    protected void init(List<Token<T>> tokens)
    {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    protected void cleanup()
    {
        tokens = null;
        currentTokenIndex = -1;
    }

}
