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

~ is used to start a single line comment. Ctenizidae script does not support multi-line comments

Parameters are published by Stimuli and consumed by Responses in the respective module code. Synapse scripts do not control parameter passing