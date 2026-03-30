package users;

import exceptions.UnauthorizedException;
import java.util.*;

// Authentication layer - manages user login/logout
public class AuthenticationService {
    private static AuthenticationService instance;
    private User currentUser;
    private Map<String, User> userDatabase;
    
    private AuthenticationService() {
        this.userDatabase = new HashMap<>();
        initializeUsers();
    }
    
    // Singleton pattern
    public static synchronized AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }
    
    // Initialize some default users
    private void initializeUsers() {
        userDatabase.put("admin1", new User("admin1", "Admin User", enums.UserRole.ADMIN, "admin123"));
        userDatabase.put("cust1", new User("cust1", "John Doe", enums.UserRole.CUSTOMER, "cust123"));
        userDatabase.put("cust2", new User("cust2", "Jane Smith", enums.UserRole.CUSTOMER, "cust123"));
    }
    
    // Login a user
    public void login(String userId, String password) throws UnauthorizedException {
        User user = userDatabase.get(userId);
        
        if (user == null) {
            throw new UnauthorizedException("❌ User not found: " + userId);
        }
        
        if (!user.getPassword().equals(password)) {
            throw new UnauthorizedException("❌ Invalid password for user: " + userId);
        }
        
        this.currentUser = user;
        System.out.println("✓ Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")");
    }
    
    // Logout current user
    public void logout() {
        if (currentUser != null) {
            System.out.println("✓ " + currentUser.getName() + " logged out");
            this.currentUser = null;
        }
    }
    
    // Get current logged-in user
    public User getCurrentUser() {
        return currentUser;
    }
    
    // Check if user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    // Check if current user is admin
    public boolean isCurrentUserAdmin() throws UnauthorizedException {
        if (currentUser == null) {
            throw new UnauthorizedException("❌ No user logged in");
        }
        return currentUser.isAdmin();
    }
    
    // Verify admin access
    public void verifyAdminAccess() throws UnauthorizedException {
        if (currentUser == null) {
            throw new UnauthorizedException("❌ No user logged in. Admin access required.");
        }
        
        if (!currentUser.isAdmin()) {
            throw new UnauthorizedException("❌ Access denied. Only admins can perform this operation.");
        }
    }
    
    // Add new user (admin only)
    public void addUser(User user) throws UnauthorizedException {
        verifyAdminAccess();
        userDatabase.put(user.getUserId(), user);
        System.out.println("✓ User added: " + user.getName());
    }
    
    public Map<String, User> getUserDatabase() {
        return userDatabase;
    }
}
