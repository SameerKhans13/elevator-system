import models.*;
import enums.*;
import users.*;
import services.*;
import repository.*;
import exceptions.*;

// Main entry point - Demonstrates the elevator system with LLD requirements
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("╔═══════════════════════════════════════════════════╗");
            System.out.println("║  ELEVATOR SYSTEM - LLD WITH DESIGN PATTERNS       ║");
            System.out.println("║  Features: Authentication, Weight Limits, Roles   ║");
            System.out.println("╚═══════════════════════════════════════════════════╝\n");
            
            // Initialize services and repositories
            ElevatorRepository elevatorRepo = new ElevatorRepository();
            ElevatorService elevatorService = new ElevatorService(elevatorRepo);
            BuildingManagementService buildingService = new BuildingManagementService(elevatorRepo);
            AuthenticationService authService = AuthenticationService.getInstance();
            
            // Create elevators
            System.out.println("--- INITIALIZING SYSTEM ---\n");
            Elevator elevator1 = new Elevator(1, 5, 6);  // 5 person capacity, 700kg limit
            Elevator elevator2 = new Elevator(2, 5, 6);
            elevatorRepo.addElevator(elevator1);
            elevatorRepo.addElevator(elevator2);
            
            System.out.println("\n--- DEMO: AUTHENTICATION & ROLES ---\n");
            
            // Admin login
            System.out.println("1️⃣  Admin attempting to login:");
            authService.login("admin1", "admin123");
            buildingService.displayElevatorStatus();
            
            // Admin checks public status
            System.out.println("\n2️⃣  Checking elevator availability:");
            buildingService.displayPublicStatus();
            
            // Admin adds new elevator (access control demo)
            System.out.println("3️⃣  Admin modifying system settings:");
            buildingService.performMaintenance(1);
            
            // Logout admin
            authService.logout();
            
            // Customer login
            System.out.println("\n--- DEMO: CUSTOMER OPERATIONS ---\n");
            System.out.println("4️⃣  Customer logging in:");
            authService.login("cust1", "cust123");
            
            try {
                // Customer cannot modify weight (should fail)
                buildingService.displayElevatorStatus();
            } catch (UnauthorizedException e) {
                System.out.println(e.getMessage());
            }
            
            // Customer can check public status
            System.out.println("\n5️⃣  Customer checking elevator availability:");
            buildingService.displayPublicStatus();
            
            // Customer calls elevator
            System.out.println("6️⃣  Customer calling elevator:");
            elevatorService.callElevator(2, 5);
            
            System.out.println("\n--- DEMO: WEIGHT LIMIT MANAGEMENT ---\n");
            
            System.out.println("7️⃣  Passengers boarding Elevator 1:");
            try {
                for (int i = 1; i <= 6; i++) {
                    elevatorService.boardPassenger(1);
                }
            } catch (WeightLimitExceededException e) {
                System.out.println(e.getMessage());
            }
            
            System.out.println("\n8️⃣  Showing elevator status with weight:");
            System.out.println(elevator1);
            System.out.println(elevator2);
            
            System.out.println("\n--- DEMO: PASSENGER OPERATIONS ---\n");
            
            System.out.println("9️⃣  Moving elevator to destination:");
            elevator1.moveToFloor(5);
            elevator1.openDoors();
            
            System.out.println("\n🔟 Passengers exiting:");
            elevatorService.exitPassenger(1);
            elevatorService.exitPassenger(1);
            elevatorService.exitPassenger(1);
            
            elevator1.closeDoors();
            
            System.out.println("\n--- FINAL SYSTEM STATUS ---\n");
            System.out.println("Elevator 1: " + elevator1);
            System.out.println("Elevator 2: " + elevator2);
            
            authService.logout();
            
            System.out.println("\n╔═══════════════════════════════════════════════════╗");
            System.out.println("║   KEY FEATURES DEMONSTRATED:                      ║");
            System.out.println("║   ✓ User Authentication (Admin/Customer)           ║");
            System.out.println("║   ✓ Role-Based Access Control                     ║");
            System.out.println("║   ✓ Weight Limit Management (700kg per elevator)   ║");
            System.out.println("║   ✓ Service Layer Architecture                    ║");
            System.out.println("║   ✓ Repository Pattern for Data Management        ║");
            System.out.println("║   ✓ Custom Exception Handling                     ║");
            System.out.println("║   ✓ Capacity Management                           ║");
            System.out.println("╚═══════════════════════════════════════════════════╝");
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
