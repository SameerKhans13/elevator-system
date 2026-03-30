package repository;

import models.Elevator;
import java.util.*;

// Repository pattern - manages elevator data persistence
public class ElevatorRepository {
    private List<Elevator> elevators;
    
    public ElevatorRepository() {
        this.elevators = new ArrayList<>();
    }
    
    // Add elevator to repository
    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
        System.out.println("✓ Elevator " + elevator.getElevatorId() + " added to repository");
    }
    
    // Get elevator by ID
    public Elevator getElevatorById(int elevatorId) {
        return elevators.stream()
                .filter(e -> e.getElevatorId() == elevatorId)
                .findFirst()
                .orElse(null);
    }
    
    // Get all elevators
    public List<Elevator> getAllElevators() {
        return new ArrayList<>(elevators);
    }
    
    // Get count
    public int getTotalElevators() {
        return elevators.size();
    }
    
    // Find available elevator (with space for passenger)
    public Elevator findAvailableElevator() {
        return elevators.stream()
                .filter(Elevator::canAddPassenger)
                .findFirst()
                .orElse(null);
    }
    
    // Get elevator closest to a floor
    public Elevator getClosestElevator(int targetFloor) {
        return elevators.stream()
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - targetFloor)))
                .orElse(null);
    }
}
