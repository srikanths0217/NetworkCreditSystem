package networkcreditsystem;

public class Person {
    private String username;
    private String mobile;
    private String password;

    public Person(String username, String mobile, String password) {
        this.username = username;
        this.mobile = mobile;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
