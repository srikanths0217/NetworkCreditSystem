package networkcreditsystem;

public class Complaint {
    private String username;
    private String network;
    private String category;
    private String area;
    private boolean resolved;

    public Complaint(String username, String network, String category, String area) {
        this.username = username;
        this.network = network;
        this.category = category;
        this.area = area;
        this.resolved = false; // By default, a new complaint is unresolved
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getNetwork() {
        return network;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}