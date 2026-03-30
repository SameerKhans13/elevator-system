# Architecture & Design Patterns

## System Architecture Overview

The elevator system implements a layered architecture with clear separation of concerns:

```
┌─────────────────────────────────────┐
│   Main / Presentation Layer         │
│   (Entry Point & Demo)              │
├─────────────────────────────────────┤
│   Authentication Layer              │
│   - User login/logout               │
│   - Role-based access control       │
├─────────────────────────────────────┤
│   Service Layer (Business Logic)    │
│   - ElevatorService                 │
│   - BuildingManagementService       │
├─────────────────────────────────────┤
│   Repository Layer (Data Access)    │
│   - ElevatorRepository              │
│   - RequestRepository               │
├─────────────────────────────────────┤
│   Model Layer (Entities)            │
│   - Elevator, User, Request, Floor  │
└─────────────────────────────────────┘
```

## Design Patterns Implemented

### 1. Facade Pattern
**Purpose**: Simplify complex subsystem interface  
**Implementation**: `Building` class (simplified interface)  
**Benefit**: Clients interact with single, simple interface

### 2. Repository Pattern
**Purpose**: Centralize data access and management  
**Implementation**: `ElevatorRepository`, `RequestRepository`  
**Benefit**: Data integrity, easy to test, swap implementations

### 3. Service Layer Pattern
**Purpose**: Separate business logic from controllers  
**Implementation**: `ElevatorService`, `BuildingManagementService`  
**Benefit**: Reusable business logic, clean code

### 4. Singleton Pattern
**Purpose**: Ensure single instance of shared resource  
**Implementation**: `AuthenticationService`  
**Benefit**: Centralized authentication management

### 5. Strategy Pattern
**Purpose**: Encapsulate algorithms with interchangeable implementations  
**Implementation**: `selectBestElevator()` method (scheduling)  
**Benefit**: Easy to swap strategies without code changes

## Class Relationships

```
┌──────────────────┐
│   User           │
├──────────────────┤
│ - userId         │
│ - name           │
│ - role (Admin/   │
│   Customer)      │
│ - password       │
└──────────────────┘
         │
         │ authenticates through
         ▼
┌──────────────────────────────┐
│  AuthenticationService       │
│  (Singleton)                 │
├──────────────────────────────┤
│ - currentUser                │
│ - userDatabase               │
│ + login()                    │
│ + logout()                   │
│ + verifyAdminAccess()        │
└──────────────────────────────┘
         │
         │ used by
         ▼
┌──────────────────────────────┐         ┌──────────────────────┐
│  ElevatorService             │────────▶│  ElevatorRepository  │
├──────────────────────────────┤         ├──────────────────────┤
│ + callElevator()             │         │ - elevators[]        │
│ + boardPassenger()           │         │ + add/get            │
│ + exitPassenger()            │         │ + findAvailable()    │
│ - selectBestElevator()       │         │ + getClosest()       │
└──────────────────────────────┘         └──────────────────────┘
                              │                        │
                    used by   │                        │
                              ▼                        ▼
                        ┌──────────────────┐  ┌─────────────────┐
                        │  Elevator        │  │  Request        │
                        ├──────────────────┤  ├─────────────────┤
                        │ - currentFloor   │  │ - sourceFloor   │
                        │ - currentWeight  │  │ - destFloor     │
                        │ - maxWeight      │  │ - requestTime   │
                        │ - state          │  └─────────────────┘
                        │ - direction      │
                        └──────────────────┘
```

## Key Design Decisions

### 1. Weight-Based Passenger Tracking
Instead of simple capacity limits, the system tracks both:
- Number of passengers (capacity limit)
- Total weight (700kg limit)

This prevents both overcrowding and overloading.

### 2. Role-Based Access Control
Two distinct roles with different capabilities:
- **CUSTOMER**: Can call elevators, view availability
- **ADMIN**: Full system access, can modify settings

Enforced through `AuthenticationService.verifyAdminAccess()`.

### 3. Service Layer Separation
Business logic separated from models to enable:
- Easy testing
- Code reusability
- Clear responsibilities
- Low coupling

### 4. Repository Pattern
All elevator data access through `ElevatorRepository` to ensure:
- Data consistency
- Easy persistence layer swap
- Centralized query logic

## Exception Hierarchy

```
Exception
├── UnauthorizedException
│   ├── User not logged in
│   ├── Invalid credentials
│   └── Insufficient permissions
├── WeightLimitExceededException
│   └── Exceeding 700kg limit
└── InvalidOperationException
    └── Invalid requests
```

## State Machine

```
Elevator State Transitions:

           ┌─────────────────┐
           │   IDLE          │
           └────────┬────────┘
                    │
          (get floor request)
                    │
      ┌─────────────┴──────────────┐
      ▼                            ▼
┌────────────┐          ┌────────────────┐
│ MOVING_UP  │          │  MOVING_DOWN   │
└────┬───────┘          └────┬───────────┘
     │                       │
     │ (reach floor)         │ (reach floor)
     │                       │
     └─────────┬─────────────┘
               ▼
         ┌───────────────┐
         │  DOOR_OPEN    │
         └───┬───────────┘
             │
      (doors close)
             │
             ▼
         ┌───────────────┐
         │ DOOR_CLOSED   │
         └───┬───────────┘
             │
      (more requests?)
             │
    No◄──────┤──────►Yes
      │                │
      ▼                ▼
    IDLE         MOVING_UP/DOWN
```

## Concurrency Considerations

**Current Implementation**: Single-threaded (for simplicity)

**Future Enhancement**: Multi-threaded
- Each elevator can move independently (Thread per elevator)
- Request queue processed concurrently
- Need synchronization for shared data

```java
// Future: Multi-threaded elevator movement
class Elevator extends Thread {
    public void run() {
        while (true) {
            Integer nextFloor = getNextFloor();
            if (nextFloor != null) {
                moveToFloor(nextFloor);
                openDoors();
                // ...
                closeDoors();
            }
        }
    }
}
```

## Data Flow Example

### User Calls Elevator

```
User presses call button (Floor 3, direction UP)
    │
    ├─ AuthenticationService.isLoggedIn()? ✓
    ├─ Create Request(3, 5)
    │
    ▼
ElevatorService.callElevator()
    │
    ├─ selectBestElevator()
    │    └─ Check all elevators
    │    └─ Skip if at weight limit
    │    └─ Skip if no capacity
    │    └─ Find closest
    │
    ▼
Assign request to Elevator 1
    │
    ├─ elevator1.addRequest(5)
    │
    ▼
ElevatorService.boardPassenger()
    │
    ├─ Check weight limit
    ├─ Check capacity
    ├─ Add weight
    ├─ Increment passengers
    │
    ▼
Elevator moves to next floor
    │
    ├─ moveToFloor()
    ├─ openDoors()
    ├─ Wait for exit
    ├─ closeDoors()
    │
    ▼
Continue to next request
```

## Performance Characteristics

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| Find closest elevator | O(n) | O(1) |
| Add passenger | O(1) | O(1) |
| Get elevator by ID | O(n) | O(1) |
| Process request | O(n) | O(1) |

Where n = number of elevators

---

For detailed implementation, see [LLD Requirements](LLD_REQUIREMENTS.md).
