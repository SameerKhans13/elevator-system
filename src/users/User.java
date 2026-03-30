package users;

import enums.UserRole;

// Represents a user in the elevator system
public class User {
    private String userId;
    private String name;
    private UserRole role;
    private String password;
    
    public User(String userId, String name, UserRole role, String password) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.password = password;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public String getPassword() {
        return password;
    }
    
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
