# N-particles-simulation
Multithreaded simulation of n particles in space. Gravitational and electric forces are implemented, but application may be easily scaled to consider other forces.

![GUI](https://raw.githubusercontent.com/victorvianna/N-particles-simulation/master/screenshots/gui-screenshot.png) 

## Authors

Cauim de Souza Lima  
Victor Hugo Vianna

## Requirements

Java 1.8

## Instructions

Open a shell and run  
```
$ sudo bash RUN.sh
```  
You can either enter the particle data through the shell or pre-record it in a file input.txt. If such a file exists, input will be read there.

## Main algorithmic ideas

Use of an Executor who consumes tasks of type PositionUpdate and VelocityUpdate from a task queue