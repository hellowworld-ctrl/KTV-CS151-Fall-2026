# KTV

SJSU CS 151 Section 5, Fall 2026. Project 2 group repository.

Due Saturday, October 24, 2026, at 11:59 PM.

## Overview

KTV is a Java program that simulates a real-world system. A user can create objects, connect them, change them through methods, and display their state. Each launch starts fresh. The program does not save data between runs.

The system allows at most 100 instances of each class. Creating an object that matches one that already exists is rejected. Going past the cap prints an error and leaves the program running.

## Developers

| Name | GitHub | Ownership |
| --- | --- | --- |
| Volodymyr Surin | [hellowworld-ctrl](https://github.com/hellowworld-ctrl) | Repository setup and this README |

Class ownership for each group member will be added here once the group and the class list are set.

## Design

The project theme is KTV. The class list, attributes, methods, and UML class diagram are not in the repository yet.

The design will follow the Project 2 rules: at least six classes, each with a single responsibility, plus one interface. Methods that change the system will check that the action is allowed before they update any object. A shared cap of 100 instances applies to every class, and two objects that are functionally the same cannot both exist.

## Installation Instructions

Install a JDK (17 or newer). Then clone the repository:

```bash
git clone https://github.com/hellowworld-ctrl/KTV-CS151-Fall-2026.git
cd KTV-CS151-Fall-2026
```

Java source and build files are not in the repository yet. Compile and run commands will be added with the first source commit.

## Usage

There is no program to run yet. After the interface is added, start it from the project root and use the menu to create objects, change them, and print their state.

## Contributions

- **Volodymyr Surin:** Added this README with the sections required for Project 2: Overview, Developers, Design, Installation Instructions, Usage, and Contributions.
