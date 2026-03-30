package models;

import java.util.ArrayList;
import java.util.List;

// Represents a single floor in the building
public class Floor {
    private int floorNumber;
    private Button callUpButton;
    private Button callDownButton;
    private List<Integer> waitingPassengers = new ArrayList<>();
    
    public Floor(int floorNumber, int totalFloors) {
        this.floorNumber = floorNumber;
        
        // Top floor doesn't have up button, ground floor doesn't have down button
        if (floorNumber < totalFloors - 1) {
            this.callUpButton = new Button(floorNumber, enums.ButtonType.CALL_UP_BUTTON);
        }
        
        if (floorNumber > 0) {
            this.callDownButton = new Button(floorNumber, enums.ButtonType.CALL_DOWN_BUTTON);
        }
    }
    
    public void pressCallButton(enums.Direction direction) {
        if (direction == enums.Direction.UP && callUpButton != null) {
            callUpButton.press();
        } else if (direction == enums.Direction.DOWN && callDownButton != null) {
            callDownButton.press();
        }
    }
    
    public boolean hasUpCallRequest() {
        return callUpButton != null && callUpButton.isPressed();
    }
    
    public boolean hasDownCallRequest() {
        return callDownButton != null && callDownButton.isPressed();
    }
    
    public void resetCallButtons() {
        if (callUpButton != null) {
            callUpButton.release();
        }
        if (callDownButton != null) {
            callDownButton.release();
        }
    }
    
    public int getFloorNumber() {
        return floorNumber;
    }
}
