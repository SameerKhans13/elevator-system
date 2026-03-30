package models;

import enums.ElevatorState;
import enums.Direction;
import exceptions.WeightLimitExceededException;
import java.util.*;

// Represents a single elevator
public class Elevator {
    private int elevatorId;
    private int currentFloor;
    private int maxCapacity;
    private int currentPassengers;
    private int currentWeight;           // Total weight in kg
    private int maxWeight;               // Maximum weight limit in kg
    private int averagePassengerWeight;  // Average weight per passenger
    private ElevatorState state;
    private Direction direction;
    private PriorityQueue<Integer> requestedFloors;  // Floors to visit
    private List<Button> floorButtons;
    
    public Elevator(int elevatorId, int maxCapacity, int totalFloors) {
        this.elevatorId = elevatorId;
        this.currentFloor = 0;  // Starts at ground floor
        this.maxCapacity = maxCapacity;
        this.currentPassengers = 0;
        this.maxWeight = 700;  // Weight limit 700kg (typical for elevators)
        this.currentWeight = 0;
        this.averagePassengerWeight = 75;  // Average passenger weight 75kg
        this.state = ElevatorState.IDLE;
        this.direction = Direction.IDLE;
        this.requestedFloors = new PriorityQueue<>();
        this.floorButtons = new ArrayList<>();
        
        // Create floor buttons for all floors
        for (int i = 0; i < totalFloors; i++) {
            floorButtons.add(new Button(i, enums.ButtonType.FLOOR_BUTTON));
        }
    }
    
    // Simulate elevator moving to a floor
    public void moveToFloor(int targetFloor) {
        System.out.println("🔔 Elevator " + elevatorId + " is moving from floor " + currentFloor + " to floor " + targetFloor);
        
        // Determine direction
        if (targetFloor > currentFloor) {
            this.direction = Direction.UP;
            this.state = ElevatorState.MOVING_UP;
        } else if (targetFloor < currentFloor) {
            this.direction = Direction.DOWN;
            this.state = ElevatorState.MOVING_DOWN;
        }
        
        // Simulate movement
        this.currentFloor = targetFloor;
        System.out.println("✓ Elevator " + elevatorId + " reached floor " + currentFloor);
    }
    
    public void openDoors() {
        this.state = ElevatorState.DOOR_OPEN;
        System.out.println("🚪 Elevator " + elevatorId + " doors opening at floor " + currentFloor);
    }
    
    public void closeDoors() {
        this.state = ElevatorState.DOOR_CLOSED;
        System.out.println("🚪 Elevator " + elevatorId + " doors closing");
    }
    
    public void addPassenger() throws WeightLimitExceededException {
        if (currentPassengers >= maxCapacity) {
            throw new WeightLimitExceededException("⚠️  Elevator " + elevatorId + " is full (capacity limit: " + maxCapacity + ")!");
        }
        
        int newWeight = currentWeight + averagePassengerWeight;
        if (newWeight > maxWeight) {
            throw new WeightLimitExceededException("⚠️  Elevator " + elevatorId + " weight limit exceeded! (Current: " + currentWeight + "kg, Max: " + maxWeight + "kg)");
        }
        
        currentPassengers++;
        currentWeight = newWeight;
        System.out.println("👤 Passenger entered elevator " + elevatorId + ". Passengers: " + currentPassengers + "/" + maxCapacity + " | Weight: " + currentWeight + "/" + maxWeight + "kg");
    }
    
    public void removePassenger() {
        if (currentPassengers > 0) {
            currentPassengers--;
            currentWeight -= averagePassengerWeight;
            System.out.println("👤 Passenger exited elevator " + elevatorId + ". Passengers: " + currentPassengers + "/" + maxCapacity + " | Weight: " + currentWeight + "/" + maxWeight + "kg");
        }
    }
    
    public void addRequest(int floorNumber) {
        if (floorNumber >= 0 && floorNumber < floorButtons.size()) {
            requestedFloors.offer(Math.abs(floorNumber - currentFloor) * 10 + floorNumber);  // Add priority
            System.out.println("📍 Floor " + floorNumber + " request added to elevator " + elevatorId);
        }
    }
    
    public Integer getNextFloor() {
        Integer priority = requestedFloors.poll();
        if (priority != null) {
            return priority % 10;  // Extract floor number
        }
        return null;
    }
    
    public boolean hasRequests() {
        return !requestedFloors.isEmpty();
    }
    
    public void idle() {
        this.state = ElevatorState.IDLE;
        this.direction = Direction.IDLE;
    }
    
    // Getters
    public int getElevatorId() {
        return elevatorId;
    }
    
    public int getCurrentFloor() {
        return currentFloor;
    }
    
    public ElevatorState getState() {
        return state;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public int getCurrentPassengers() {
        return currentPassengers;
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public int getCurrentWeight() {
        return currentWeight;
    }
    
    public int getMaxWeight() {
        return maxWeight;
    }
    
    public boolean isAtWeightLimit() {
        return currentWeight >= maxWeight;
    }
    
    public boolean canAddPassenger() {
        int potentialWeight = currentWeight + averagePassengerWeight;
        return currentPassengers < maxCapacity && potentialWeight <= maxWeight;
    }
    
    @Override
    public String toString() {
        return "Elevator " + elevatorId + "{" +
                "Floor: " + currentFloor +
                ", State: " + state +
                ", Passengers: " + currentPassengers + "/" + maxCapacity +
                ", Weight: " + currentWeight + "/" + maxWeight + "kg" +
                '}';
    }
}
