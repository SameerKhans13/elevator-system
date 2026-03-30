package services;

import models.Elevator;
import enums.ElevatorState;
import repository.ElevatorRepository;
import exceptions.UnauthorizedException;
import users.AuthenticationService;

// Service layer - manages elevator controller operations
public class BuildingManagementService {
    private ElevatorRepository elevatorRepository;
    private AuthenticationService authService;
    
    public BuildingManagementService(ElevatorRepository elevatorRepository) {
        this.elevatorRepository = elevatorRepository;
        this.authService = AuthenticationService.getInstance();
    }
    
    // Admin can view all elevator status
    public void displayElevatorStatus() throws UnauthorizedException {
        authService.verifyAdminAccess();
        
        System.out.println("\n========== ELEVATOR SYSTEM STATUS ==========");
        for (Elevator elevator : elevatorRepository.getAllElevators()) {
            System.out.println(elevator);
        }
        System.out.println("==========================================\n");
    }
    
    // Admin can add new elevator
    public void addNewElevator(Elevator elevator) throws UnauthorizedException {
        authService.verifyAdminAccess();
        elevatorRepository.addElevator(elevator);
    }
    
    // Admin can perform maintenance
    public void performMaintenance(int elevatorId) throws UnauthorizedException {
        authService.verifyAdminAccess();
        
        Elevator elevator = elevatorRepository.getElevatorById(elevatorId);
        if (elevator == null) {
            throw new UnauthorizedException("❌ Elevator not found");
        }
        
        System.out.println("🔧 Performing maintenance on Elevator " + elevatorId);
    }
    
    // Customer can view basic status
    public void displayPublicStatus() {
        System.out.println("\n========== ELEVATOR AVAILABILITY STATUS ==========");
        for (Elevator elevator : elevatorRepository.getAllElevators()) {
            if (elevator.canAddPassenger()) {
                System.out.println("✓ Elevator " + elevator.getElevatorId() + " - Available (Floor: " + elevator.getCurrentFloor() + ")");
            } else {
                System.out.println("✗ Elevator " + elevator.getElevatorId() + " - Full or at weight limit");
            }
        }
        System.out.println("==================================================\n");
    }
}
