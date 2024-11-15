
# BioMASS Space Model

This project is licensed under the terms of the MIT license.

## Introduction

BioMASS introduces a highly efficient spatial model tailored for multi-agent systems that require dynamic neighborhood exploration and real-time locomotion across large spatial environments. The innovative aspect of BioMASS lies in its use of a quadruply linked list structure, which drastically reduces the computational complexity traditionally associated with agent-based modeling platforms.

## The MAS Framework

The spatial model is integrated into a simple yet versatile Java framework for multi-agent system simulation. Key classes within this framework include *Agent.java*, *Scheduler.java*, *Space.java* and *PhysicalObject.java*, which coordinate the core functions of agent behavior, simulation timing, and spatial environment management. The *PhysicalObject.java* class specifically equips agents with the four pointers necessary for insertion into the quadruply linked list. The orchestration of a complete simulation can be observed in the example code provided, where the *multiagent.simulator.gui.Main.java* class serves as the entry point. Running this class launches a graphical user interface (GUI) that allows users to configure key parameters for the provided example, such as the simulation space size, number of agents, and perception range.

In addition to the standard GUI implementation, we have developed specialized classes named Headless.java, designed specifically for environments that do not support graphical interfaces, such as CodeOcean. These headless classes facilitate the execution of the simulation without the need for a GUI, enabling users to verify the model's functionality in a streamlined manner. Instead of using a graphical interface, the simulation saves images of each step into the output folder, providing a visual representation of the simulation's progress.


