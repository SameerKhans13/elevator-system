package enums;

// Different states an elevator can be in
public enum ElevatorState {
    IDLE,           // Not moving, doors closed
    MOVING_UP,      // Moving towards higher floors
    MOVING_DOWN,    // Moving towards lower floors
    DOOR_OPEN,      // Stopped and doors are open
    DOOR_CLOSED     // Stopped and doors are closed
}
