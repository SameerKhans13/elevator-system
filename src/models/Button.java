package models;

import enums.ButtonType;

// Represents a button (either inside elevator or on floor)
public class Button {
    private int floorNumber;
    private ButtonType buttonType;
    private boolean isPressed;
    
    public Button(int floorNumber, ButtonType buttonType) {
        this.floorNumber = floorNumber;
        this.buttonType = buttonType;
        this.isPressed = false;
    }
    
    public void press() {
        this.isPressed = true;
        System.out.println("Button pressed for floor " + floorNumber);
    }
    
    public void release() {
        this.isPressed = false;
    }
    
    public int getFloorNumber() {
        return floorNumber;
    }
    
    public ButtonType getButtonType() {
        return buttonType;
    }
    
    public boolean isPressed() {
        return isPressed;
    }
}
