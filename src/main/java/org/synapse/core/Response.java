package org.synapse.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

// Responses do something when triggered. This amounts to calling the main function on the response
// Most of the code in this class has to do with unrolling stimulus data into a list of type-safe variables
// I find that using method parameters is a great way to handle this (after all we are just linking methods at runtime)


// The LORD is my Shephard, I shall not want
// He makes me lie down in green pastures
// As I walk through the valley of the Shadow of Death,
// I will fear no evil, for your rod and staff, they comfort me

public abstract class Response 
{

    // This allows binding parameter data
    // Every parameter must have this if we use the annotation binding strategy
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface BindParam
    {
        String value();
    }

    // Different ways to bind
    public enum BindStrategy {Implicit, Annotation};

    // Must be present on main method because we must declare explicitly how to bind
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Bind
    {
        BindStrategy strategy();
    }

    public final String mainMethod = "main"; // Used for reflection

    // public final String name;

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
            if(method.getName().equals(name) && method.canAccess(this) && method.isAnnotationPresent(Bind.class))
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
            throw new RuntimeException("Could not find method " + mainMethod + " with bind annotation");
        }

        for (Method method : methods) 
        {

            Bind bindAnnotation = method.getAnnotation(Bind.class);
            switch (bindAnnotation.strategy()) 
            {
                case Implicit:
                    ParameterDispatch(method, data);
                    return;

                case Annotation:
                    AnnotationDispatch(method, data);
                    return;
            
                default:
                    throw new RuntimeException("Invalid bind strategy " + bindAnnotation.strategy().name());
            }
        }

        throw new RuntimeException("Stimulus failed to provide all required variables for this response");
    }

    private void AnnotationDispatch(Method method, StimulusData data)
    {
        // Skip the method if the parameter count is greater than our data
        if(method.getParameterCount() > data.size())
        {
            return;
        }

        // Prepare the parameter list while we verify it
        Object[] symbolValues = new Object[method.getParameterCount()];
        int symbolIndex = 0;

        for(Parameter p : method.getParameters())
        {
            // Parameter names need to be kept
            // This requires the -parameters flag
            BindParam bindAnnotation = p.getDeclaredAnnotation(BindParam.class);

            if(bindAnnotation == null)
            {
                throw new RuntimeException("All parameters require @Bind when using annotation binding");
            }

            Optional<?> variable = data.get(bindAnnotation.value(), p.getType());
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

    private void ParameterDispatch(Method method, StimulusData data)
    {
        // Skip the method if the parameter count is greater than our data
        if(method.getParameterCount() > data.size())
        {
            return;
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
                return; // No variable matches this parameter, so move on to next method.
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
}
