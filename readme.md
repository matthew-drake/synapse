# Synapse

Synapse is a stimulus-response framework. It can automate various tasks efficiently and algorithmically.

It was originally designed to be used in cybersecurity applications as an automatic compromise tool for penetration testing.

## Build instructions

Run `mvn package`
The output jar should appear in the "target" folder

## How to use

Usage: `java -jar <path-to-synapse> <script-path>`

## Synapse Script

Synapse scripts use the following grammar:

`
    <program> ::= <statement> <program> | <statement>
    <statement> ::= <import> <line-separator> | <connection> <line-separator>
    <import> ::= "import " <name>
    <connection> ::= <name> " --> " <name>
`

~ is used to start a single line comment. Synapse script does not support multi-line comments

Parameters are published by Stimuli and consumed by Responses in the respective module code. Synapse scripts do not control parameter passing

## Stimuli

Stimuli are classes deriving from the Stimulus base class.

They require a simple void main() method to function.

Stimuli can publish variables using the publishData function, and they call trigger() in order to fire connected responses.

Stimuli are automatically run by the Synapse Broker

## Responses

Response classes are classes deriving from 

### Implicit Binding:

When using implicit binding (BindStrategy.Implicit) with responses, you MUST compile your responses with the -parameters flag in order to maintain parameter names.

The maven configuration for Synapse is already configured to do this so unless you are compiling your response objects from outside this repository you should be good.

Implicit Binding matches incoming stimulus data against parameter names and types directly. This means that your method signatures are much shorter. Implicit binding is the preferred method for most responses because of this.

### Annotation Binding

Annotation binding (BindStrategy.Annotation) requires that all method parameters in your main method are annotated with @Bind

Failure to do so will result in an exception being thrown

Annotation binding results in longer method signatures, but allows you to rename inputs and does not require the -parameters flag to be set, making it useful for situations where you cannot control your compilation process.