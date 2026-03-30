package models;

// Represents a request to go to a specific floor
public class Request {
    private int sourceFloor;
    private int destinationFloor;
    private long requestTime;
    
    public Request(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.requestTime = System.currentTimeMillis();
    }
    
    public int getSourceFloor() {
        return sourceFloor;
    }
    
    public int getDestinationFloor() {
        return destinationFloor;
    }
    
    public long getRequestTime() {
        return requestTime;
    }
    
    @Override
    public String toString() {
        return "Request{" +
                "from " + sourceFloor +
                " to " + destinationFloor +
                '}';
    }
}
