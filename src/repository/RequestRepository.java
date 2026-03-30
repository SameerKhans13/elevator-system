package repository;

import models.Request;
import java.util.*;

// Repository pattern - manages request data
public class RequestRepository {
    private Queue<Request> pendingRequests;
    private List<Request> completedRequests;
    
    public RequestRepository() {
        this.pendingRequests = new LinkedList<>();
        this.completedRequests = new ArrayList<>();
    }
    
    // Add new request
    public void addRequest(Request request) {
        pendingRequests.offer(request);
    }
    
    // Get next pending request
    public Request getNextRequest() {
        return pendingRequests.poll();
    }
    
    // Check if requests exist
    public boolean hasPendingRequests() {
        return !pendingRequests.isEmpty();
    }
    
    // Mark request as completed
    public void completeRequest(Request request) {
        completedRequests.add(request);
    }
    
    // Get all completed requests
    public List<Request> getCompletedRequests() {
        return new ArrayList<>(completedRequests);
    }
    
    // Get pending request count
    public int getPendingRequestCount() {
        return pendingRequests.size();
    }
    
    // Get total completed
    public int getCompletedRequestCount() {
        return completedRequests.size();
    }
}
