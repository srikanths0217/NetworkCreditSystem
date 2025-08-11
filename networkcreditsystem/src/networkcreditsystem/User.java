package networkcreditsystem;
public class User extends Person {
    public User(String username, String mobile, String password) {
        super(username, mobile, password); // This line calls the Person class constructor
    }
}