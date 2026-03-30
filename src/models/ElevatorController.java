package models;

import java.util.*;

// Manages all elevators and implements scheduling strategy
public class ElevatorController {
    private List<Elevator> elevators;
    private int totalFloors;
    private Queue<Request> requestQueue;
    
    public ElevatorController(int numElevators, int totalFloors, int elevatorCapacity) {
        this.totalFloors = totalFloors;
        this.elevators = new ArrayList<>();
        this.requestQueue = new LinkedList<>();
        
        // Create elevators
        for (int i = 1; i <= numElevators; i++) {
            elevators.add(new Elevator(i, elevatorCapacity, totalFloors));
        }
    }
    
    // Add a request to the queue
    public void requestElevator(Request request) {
        requestQueue.offer(request);
        System.out.println("📋 Request added: " + request);
        assignElevator(request);
    }
    
    // Assign request to the best available elevator (Strategy Pattern)
    private void assignElevator(Request request) {
        Elevator bestElevator = selectBestElevator(request);
        
        if (bestElevator != null) {
            bestElevator.addRequest(request.getDestinationFloor());
            System.out.println("✓ Request assigned to Elevator " + bestElevator.getElevatorId());
        }
    }
    
    // Select best elevator using SCAN algorithm (simple approach)
    private Elevator selectBestElevator(Request request) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Elevator elevator : elevators) {
            // Skip if elevator is full
            if (elevator.getCurrentPassengers() >= elevator.getMaxCapacity()) {
                continue;
            }
            
            // Calculate distance from current floor to request floor
            int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());
            
            if (distance < minDistance) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }
        
        return bestElevator;
    }
    
    // Process elevator movements (simulates running the system)
    public void processRequests() {
        System.out.println("\n========== PROCESSING REQUESTS ==========\n");
        
        for (Elevator elevator : elevators) {
            processElevator(elevator);
        }
    }
    
    // Process single elevator
    private void processElevator(Elevator elevator) {
        if (!elevator.hasRequests()) {
            elevator.idle();
            return;
        }
        
        Integer nextFloor = elevator.getNextFloor();
        if (nextFloor != null) {
            // Move to floor
            elevator.moveToFloor(nextFloor);
            
            // Simulate door operations
            elevator.openDoors();
            try {
                Thread.sleep(500);  // Simulate door open time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elevator.closeDoors();
        }
    }
    
    // Display status of all elevators
    public void displayStatus() {
        System.out.println("\n========== ELEVATOR SYSTEM STATUS ==========");
        for (Elevator elevator : elevators) {
            System.out.println(elevator);
        }
        System.out.println("==========================================\n");
    }
    
    public List<Elevator> getElevators() {
        return elevators;
    }
    
    public int getTotalFloors() {
        return totalFloors;
    }
}
