package org.synapse.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

// Responses do something when triggered
// The most basic form of this is just an interface 
// but I am writing some logic to use reflection to pass data to stimuli

// The LORD is my Shephard, I shall not want
// He makes me lie down in green pastures
// As I walk through the valley of the Shadow of Death,
// I will fear no evil, for your rod and staff, they comfort me

public abstract class Response 
{
    public final String mainMethod = "main"; // Used for reflection

    public final String name;

    // Find the main method, then check the incoming data and pass data
    // Using methods and reflection ensures that responses receive valid objects as parameters that are accessible at compile time
    // This is the cleanest way to go about all of this.
    // Additionally, since we have these checks, we can get rid of all the symbol overhead
    // We can just export a hashmap of data and match it against this.

    public List<Method> getMethods(String name)
    {
        List<Method> result = new LinkedList<>();
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) 
        {
            if(method.getName().equals(name) && method.canAccess(this))
            {
                result.add(method);
            }
        }

        return result;
    }

    public void trigger(StimulusData data)
    {
        List<Method> methods = getMethods(mainMethod);
        if(methods.isEmpty())
        {
            throw new RuntimeException("Could not find method " + mainMethod);
        }

        for (Method method : methods) 
        {
            // Skip the method if the parameter count is greater than our data
            if(method.getParameterCount() > data.size())
            {
                continue;
            }

            // Prepare the parameter list while we verify it
            Object[] symbolValues = new Object[method.getParameterCount()];
            int symbolIndex = 0;

            for(Parameter p : method.getParameters())
            {
                // Parameter names need to be kept
                // This requires the -parameters flag
                Optional<?> variable = data.get(p.getName(), p.getType());
                if(variable.isPresent())
                {
                    symbolValues[symbolIndex] = variable.orElseThrow();
                    symbolIndex++;
                }
                else
                {
                    continue; // No variable matches this parameter, so move on to next method.
                }
            }

            try 
            {
                method.invoke(this, symbolValues);
                return;
            } 
            catch (Exception e) 
            {
                throw new RuntimeException("Stimulus failed to provide all required variables for this response");
            } 
        }

        throw new RuntimeException("Stimulus failed to provide all required variables for this response");
    }

    protected Response(String name)
    {
        this.name = name;
    }
}
