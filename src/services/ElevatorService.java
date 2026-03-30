package services;

import models.Elevator;
import models.Request;
import repository.ElevatorRepository;
import exceptions.UnauthorizedException;
import exceptions.WeightLimitExceededException;
import users.AuthenticationService;

// Service layer - business logic for elevator operations
public class ElevatorService {
    private ElevatorRepository elevatorRepository;
    private AuthenticationService authService;
    
    public ElevatorService(ElevatorRepository elevatorRepository) {
        this.elevatorRepository = elevatorRepository;
        this.authService = AuthenticationService.getInstance();
    }
    
    // Customer can call elevator
    public void callElevator(int sourceFloor, int destinationFloor) throws UnauthorizedException {
        if (!authService.isLoggedIn()) {
            throw new UnauthorizedException("❌ User must be logged in to call elevator");
        }
        
        System.out.println("📞 " + authService.getCurrentUser().getName() + " called elevator from floor " + sourceFloor);
        
        Request request = new Request(sourceFloor, destinationFloor);
        assignElevator(request);
    }
    
    // Add passenger to elevator
    public void boardPassenger(int elevatorId) throws UnauthorizedException, WeightLimitExceededException {
        if (!authService.isLoggedIn()) {
            throw new UnauthorizedException("❌ User must be logged in");
        }
        
        Elevator elevator = elevatorRepository.getElevatorById(elevatorId);
        if (elevator == null) {
            throw new UnauthorizedException("❌ Elevator not found");
        }
        
        elevator.addPassenger();
    }
    
    // Remove passenger from elevator
    public void exitPassenger(int elevatorId) throws UnauthorizedException {
        if (!authService.isLoggedIn()) {
            throw new UnauthorizedException("❌ User must be logged in");
        }
        
        Elevator elevator = elevatorRepository.getElevatorById(elevatorId);
        if (elevator == null) {
            throw new UnauthorizedException("❌ Elevator not found");
        }
        
        elevator.removePassenger();
    }
    
    // Admin can modify weight capacity (example of admin-only operation)
    public void modifyWeightLimit(int elevatorId, int newWeightLimit) throws UnauthorizedException {
        authService.verifyAdminAccess();  // Only admin can do this
        
        Elevator elevator = elevatorRepository.getElevatorById(elevatorId);
        if (elevator == null) {
            throw new UnauthorizedException("❌ Elevator not found");
        }
        
        System.out.println("⚙️  Admin modified weight limit for Elevator " + elevatorId + " to " + newWeightLimit + "kg");
    }
    
    // Private method to assign elevator to request
    private void assignElevator(Request request) {
        Elevator bestElevator = selectBestElevator(request);
        
        if (bestElevator != null) {
            bestElevator.addRequest(request.getDestinationFloor());
            System.out.println("✓ Request assigned to Elevator " + bestElevator.getElevatorId());
        }
    }
    
    // Strategy: Select best elevator (closest one with available space)
    private Elevator selectBestElevator(Request request) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (Elevator elevator : elevatorRepository.getAllElevators()) {
            // Skip elevator if it's at weight limit
            if (elevator.isAtWeightLimit()) {
                System.out.println("⚠️  Elevator " + elevator.getElevatorId() + " is at weight limit");
                continue;
            }
            
            // Skip if no passenger capacity
            if (!elevator.canAddPassenger()) {
                continue;
            }
            
            // Calculate distance
            int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());
            
            if (distance < minDistance) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }
        
        return bestElevator;
    }
    
    public ElevatorRepository getRepository() {
        return elevatorRepository;
    }
}
