package models;

import java.util.*;

// Facade - Main entry point for the elevator system
public class Building {
    private int buildingId;
    private int totalFloors;
    private List<Floor> floors;
    private ElevatorController elevatorController;
    
    public Building(int buildingId, int totalFloors, int numElevators, int elevatorCapacity) {
        this.buildingId = buildingId;
        this.totalFloors = totalFloors;
        this.floors = new ArrayList<>();
        this.elevatorController = new ElevatorController(numElevators, totalFloors, elevatorCapacity);
        
        // Initialize all floors
        for (int i = 0; i < totalFloors; i++) {
            floors.add(new Floor(i, totalFloors));
        }
    }
    
    // User presses call button on a floor
    public void callElevator(int floorNumber, enums.Direction direction) {
        if (floorNumber < 0 || floorNumber >= totalFloors) {
            System.out.println("❌ Invalid floor number!");
            return;
        }
        
        Floor floor = floors.get(floorNumber);
        floor.pressCallButton(direction);
        
        // Create request based on button pressed
        if (direction == enums.Direction.UP) {
            System.out.println("🔘 Call UP button pressed on floor " + floorNumber);
            // For simplicity, assume destination is top floor
            Request request = new Request(floorNumber, totalFloors - 1);
            elevatorController.requestElevator(request);
        } else {
            System.out.println("🔘 Call DOWN button pressed on floor " + floorNumber);
            // For simplicity, assume destination is ground floor
            Request request = new Request(floorNumber, 0);
            elevatorController.requestElevator(request);
        }
    }
    
    // User presses floor button inside elevator
    public void pressFloorButton(int floorNumber) {
        if (floorNumber < 0 || floorNumber >= totalFloors) {
            System.out.println("❌ Invalid floor number!");
            return;
        }
        
        System.out.println("🔘 Floor button " + floorNumber + " pressed inside elevator");
        
        // Go to the closest elevator and add request
        Elevator closestElevator = findClosestElevator(floorNumber);
        if (closestElevator != null) {
            closestElevator.addRequest(floorNumber);
        }
    }
    
    // Find closest elevator to a given floor
    private Elevator findClosestElevator(int targetFloor) {
        List<Elevator> elevators = elevatorController.getElevators();
        Elevator closestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - targetFloor);
            if (distance < minDistance) {
                minDistance = distance;
                closestElevator = elevator;
            }
        }
        
        return closestElevator;
    }
    
    // Run the elevator system
    public void runSystem() {
        System.out.println("\n===========================================");
        System.out.println("   ELEVATOR SYSTEM RUNNING");
        System.out.println("   Building: " + buildingId);
        System.out.println("   Total Floors: " + totalFloors);
        System.out.println("===========================================\n");
        
        elevatorController.processRequests();
    }
    
    // Display system status
    public void displayStatus() {
        elevatorController.displayStatus();
    }
    
    public int getTotalFloors() {
        return totalFloors;
    }
    
    public ElevatorController getElevatorController() {
        return elevatorController;
    }
}
