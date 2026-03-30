# Elevator System - LLD Design & Implementation

## Overview

This project is an implementation of a **Low-Level Design (LLD) 101 assignment** featuring a comprehensive elevator system design and implementation. The system models real-world elevator operations with multiple floors, request handling, and lift management.

## Project Objective

Design and implement an elevator system that:
- Manages multiple elevators across multiple floors
- Handles user requests for pickups and drop-offs
- Optimizes elevator scheduling and movement
- Manages elevator states (moving, idle, maintenance)
- Provides an extensible and maintainable architecture

## Architecture & Design

### Class Diagram

The elevator system follows object-oriented design principles with the following core components:

```
Building
├── FloorManager
│   └── Floor (multiple)
├── ElevatorController
│   └── Elevator (multiple)
└── RequestManager
```

### Key Classes

- **Building**: Main container managing the elevator system
- **Elevator**: Represents an individual lift with position, capacity, and state
- **Floor**: Represents a floor with up/down request buttons
- **ElevatorController**: Controls elevator scheduling and movement logic
- **Request**: Represents user requests (pickup/drop-off)
- **Door**: Manages elevator door operations (open/close)
- **Button**: Handles floor and cabin buttons
- **RequestManager**: Manages pending and completed requests

## Features

✅ **Multi-Floor Support** - Manage buildings with multiple floors  
✅ **Multiple Elevators** - Coordinate multiple lifts efficiently  
✅ **Request Management** - Handle pickup and drop-off requests  
✅ **Elevator States** - Moving, idle, door open/close states  
✅ **Capacity Management** - Track elevator capacity and weight limits  
✅ **Optimized Scheduling** - Intelligent elevator assignment strategies  
✅ **Extensible Design** - Easy to add new features and scheduling algorithms

## Project Structure

```
elevator-system/
├── README.md                 # This file
├── design/                   # Design documents and diagrams
│   └── class_diagram.md      # Class diagram and relationships
├── src/                      # Source code implementation
│   ├── core/                 # Core elevator system classes
│   ├── models/               # Data models and entities
│   ├── strategies/           # Elevator scheduling strategies
│   └── main.java             # Main entry point
└── docs/                     # Additional documentation
```

## Implementation Details

### Language: Java
The system is implemented in Java, utilizing:
- Object-Oriented Programming (OOP) principles
- Design Patterns (Strategy, Observer, Singleton)
- Exception handling for robustness
- Clean code practices

### Core Functionality

1. **Elevator Movement**: Move elevators to requested floors
2. **Request Processing**: Queue and prioritize requests
3. **State Management**: Track elevator operational states
4. **User Interaction**: Handle button presses from users

## How to Run

### Prerequisites
- Java 8 or higher
- IDE (IntelliJ IDEA, Eclipse, VSCode) or command line

### Compilation & Execution

```bash
# Navigate to project directory
cd elevator-system

# Compile (if using javac)
javac -d bin src/**/*.java

# Run
java -cp bin Main
```

Or use your IDE's run configuration.

## Design Patterns Used

- **Strategy Pattern**: Different elevator scheduling algorithms
- **Observer Pattern**: Notification system for state changes
- **Singleton Pattern**: Single instance of building controller
- **Factory Pattern**: Creating elevators and requests

## Elevator Scheduling Strategies

The system can support multiple scheduling algorithms:
- **SCAN Algorithm**: Elevator moves in one direction, picks up all requests
- **LOOK Algorithm**: Similar to SCAN but doesn't go beyond requests
- **Shortest Seek Time First (SSTF)**: Serves closest request first

## Future Enhancements

- [ ] Load balancing across elevators
- [ ] Emergency stop functionality
- [ ] Predictive maintenance alerts
- [ ] Weight-based capacity management
- [ ] Energy efficiency optimization
- [ ] REST API for elevator control

## Testing

Unit tests cover:
- Elevator movement logic
- Request handling
- State transitions
- Capacity constraints
- Edge cases and error scenarios

## Assumptions

1. Elevators move at constant speed between floors
2. Doors open/close in negligible time
3. Maximum weight capacity is enforced
4. Single-threaded operation (can be extended for multi-threading)
5. Requests are valid (floor exists within building)

## Key Learnings

This LLD assignment demonstrates:
- Object-oriented design principles
- System design thinking
- Scalability and maintainability
- Real-world problem modeling
- Trade-offs in algorithm selection

## Assignment Details

- **Submission Type**: Individual assignment
- **Framework**: Low-Level Design (LLD) 101
- **Deliverables**: 
  - Class diagram
  - Complete implementation
  - This README documentation

---

**Note**: This is an independent submission. External help or code copying is not permitted per assignment guidelines.

**Date**: March 2026

---

For detailed technical documentation, see the [Class Diagram](design/class_diagram.md) and inline code comments.
