package org.synapse.parsing;

import java.util.LinkedList;
import java.util.List;

public class ScriptTokenizer 
{
    // This class tokenizes a script and strips out comments

    // Comments start with ~
    // Stimulus lines look like stimulus_name --> response_name
    // We have gotten rid of the () with data names around the 
    // response since that is handled code wise

    // Import lines start with import <java-class-name>
    // They are important since they let us actually retrieve our stimulus/responses

    public enum TokenType
    {
        IMPORT, CONNECTOR, DATA, LINE_SEPARATOR, EOF, AS
    }

    public List<Token<TokenType>> tokenize(String input)
    {
        List<Token<TokenType>> result = new LinkedList<>();
        String textBuffer = "";
        boolean inComment = false;

        for(char c : input.toCharArray())
        {
            
            if(!inComment)
            {
                if(c == '~')
                {
                    inComment = true;
                }
                else if(textBuffer.equals("import"))
                {
                    result.add(new Token<>(TokenType.IMPORT, textBuffer));
                    textBuffer = "";
                }
                else if(textBuffer.equals("as"))
                {
                    result.add(new Token<>(TokenType.AS, textBuffer));
                    textBuffer = "";
                }
                else if(textBuffer.equals("-->"))
                {
                    result.add(new Token<>(TokenType.CONNECTOR, textBuffer));
                    textBuffer = "";
                }
                else if(c == '\n')
                {
                    if(textBuffer.length() > 0)
                    {
                        result.add(new Token<>(TokenType.DATA, textBuffer));
                        textBuffer = "";
                    }

                    result.add(new Token<>(TokenType.LINE_SEPARATOR,"\n"));
                }
                else if(c == ' ')
                {
                    if(textBuffer.length() > 0)
                    {
                        result.add(new Token<>(TokenType.DATA, textBuffer));
                        textBuffer = "";
                    }
                }
                else
                {
                    textBuffer += c;
                }
            }
            else if(c == '\n')
            {
                inComment = false;
            }

        }

        if(textBuffer.length() > 0)
        {
            result.add(new Token<>(TokenType.DATA, textBuffer));
        }

        result.add(new Token<>(TokenType.EOF, null));

        return result;
    }


}
