package org.synapse.parsing;

// Generic token class
public class Token <T extends Enum<T>>
{
    T type;
    String value;

    Token(T type, String value)
    {
        this.type = type;
        this.value = value;
    }
}
