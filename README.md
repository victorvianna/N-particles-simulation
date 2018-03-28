# N-particles-simulation
Multithreaded simulation of n particles in space. Gravitational and electric forces are implemented, but application may be easily scaled to consider other forces.

## Authors

Cauim de Souza Lima  
Victor Hugo Vianna

## Requirements

Java 1.8

## Instructions

Add the desired image file as image.jpg to the project root, open a shell and run
```
$ sudo bash RUN.sh
```

## Main algorithmic ideas

Use of an Executor who consumes tasks of type PositionUpdate and VelocityUpdate from a task queue