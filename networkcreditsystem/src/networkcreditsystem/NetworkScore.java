package networkcreditsystem;

public class NetworkScore {
    private String area;
    private String network;
    private int score;

    public NetworkScore(String area, String network, int score) {
        this.area = area;
        this.network = network;
        this.score = score;
    }
    public String getArea() {
        return area;
    }

    public String getNetwork() {
        return network;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}