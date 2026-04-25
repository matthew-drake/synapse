# Synapse

Synapse is a stimulus-response framework. It can automate various tasks efficiently and algorithmically.

It functions similarly to the automation website IFTTT, however it is locally hosted and provides features for advanced users.

It was originally designed to be used in cybersecurity applications as an automatic compromise tool for penetration testing.

With full understanding of the power of Synapse, and its potential for abuse, I note that Synapse is only to be used in a way that is honoring to the Lord Jesus Christ.

## Build instructions

Run `mvn package`
The output jar should appear in the "target" folder

## How to use

Usage: `java -jar <path-to-synapse> <script-path>`

## Synapse Script

Synapse scripts use the following grammar:


    \<program\> ::= \<statement\> \<program\> | \<statement\>
    \<statement\> ::= \<import\> \<line-separator\> | \<connection\> \<line-separator\>
    \<import\> ::= "import " \<name\>
    \<connection\> ::= \<name\> " --> " \<name\>


~ is used to start a single line comment. Synapse script does not support multi-line comments

Parameters are published by Stimuli and consumed by Responses in the respective module code. Synapse scripts do not control parameter passing.

This means that you do need to verify compatibility between Stimuli and Responses, but any compatible Stimuli and responses can be paired with little configuration

## Stimuli

Stimuli are classes deriving from the Stimulus base class.

They require a simple void main() method to function.

Stimuli can publish variables using the publish() function, and they call trigger() in order to fire connected responses.

See the example start response to see how this works.

Stimuli are run asyncronously by the Synapse Broker. Each gets its own thread.

Stimuli are intended to run for long periods of time and trigger multiple times. They can also be used as one-shot triggers, such as the start stimulus.

Stimuli are easy to write and will likely not give you much trouble.

## Responses

Responses are classes deriving from the Response base class. They are slightly more complicated than Stimulus objects because they have to bind and unroll data received from Stimulus objects, which requires some minimal configuration.

Response objects must declare a method called main which has the parameters that the response needs. 

The exact method signature is NOT specified in the abstract base class (because the signature is variable) so your linter will NOT inform you that you need to implement this class. However, failure to implement it will result in a runtime error.

The main method must be annotated with @Bind(strategy = BindStrategy.?)

There are important differences between the two bind strategies currently supported

Please carefully read through the differences below, as failure to do so may lead to frustration trying to resolve seemingly simple errors.


### Implicit Binding:

When using implicit binding (BindStrategy.Implicit) with responses, you MUST compile your responses with the -parameters flag in order to maintain parameter names.

The maven configuration for Synapse is already configured to do this so unless you are compiling your response objects from outside this repository you should be good.

Implicit Binding matches incoming stimulus data against parameter names and types directly. This means that your method signatures are much shorter. Implicit binding is the preferred method for most responses because of this.

### Annotation Binding

Annotation binding (BindStrategy.Annotation) requires that all method parameters in your main method are annotated with @Bind

Failure to do so will result in an exception being thrown

Annotation binding results in longer method signatures, but allows you to rename inputs and does not require the -parameters flag to be set, making it useful for situations where you cannot control your compilation process.