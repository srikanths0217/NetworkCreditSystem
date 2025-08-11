package networkcreditsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NetworkCreditSystem {
    private List<User> users;
    private List<Complaint> complaints;
    private List<NetworkScore> scores;
    private Scanner scanner;
    private User loggedInUser;

    public NetworkCreditSystem() {
        this.users = new ArrayList<>();
        this.complaints = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.loggedInUser = null;
    }

    public void runMenu() {
        while (true) {
            if (loggedInUser == null) {
                showMainMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "3":
                System.out.println("Exiting application. Goodbye!");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void showUserMenu() {
        System.out.println("\n--- User Menu ---");
        System.out.println("Logged in as: " + loggedInUser.getUsername());
        System.out.println("1. File Complaint");
        System.out.println("2. View Scores");
        System.out.println("3. Mark Complaint as Resolved");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                fileComplaint();
                break;
            case "2":
                viewScores();
                break;
            case "3":
                markResolved();
                break;
            case "4":
                logoutUser();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public void registerUser() {
        System.out.println("\n--- User Registration ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter mobile number: ");
        String mobile = scanner.nextLine();

        // Check if mobile number already exists
        for (User user : users) {
            if (user.getMobile().equals(mobile)) {
                System.out.println("This mobile number is already registered.");
                return;
            }
        }
        
        // OTP Simulation
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        System.out.println("An OTP has been sent to your mobile: " + otp);
        System.out.print("Enter OTP to verify: ");
        int userOtp = Integer.parseInt(scanner.nextLine());

        if (userOtp != otp) {
            System.out.println("OTP verification failed. Registration aborted.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        users.add(new User(username, mobile, password));
        System.out.println("Registration successful!");
    }

    public void loginUser() {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter mobile number: ");
        String mobile = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getMobile().equals(mobile) && user.getPassword().equals(password)) {
                this.loggedInUser = user;
                System.out.println("Login successful! Welcome, " + user.getUsername() + ".");
                return;
            }
        }
        System.out.println("Invalid mobile number or password.");
    }
    
    public void logoutUser() {
        this.loggedInUser = null;
        System.out.println("You have been logged out.");
    }

    public void fileComplaint() {
        System.out.println("\n--- File a Complaint ---");
        System.out.print("Enter network name (Jio, Airtel, Vi, BSNL): ");
        String network = scanner.nextLine();
        System.out.print("Enter complaint category (e.g., call drop, slow internet): ");
        String category = scanner.nextLine();
        System.out.print("Enter area name: ");
        String area = scanner.nextLine();

        Complaint newComplaint = new Complaint(loggedInUser.getUsername(), network, category, area);
        complaints.add(newComplaint);

        // Deduct points from network score
        NetworkScore existingScore = null;
        for (NetworkScore score : scores) {
            if (score.getArea().equalsIgnoreCase(area) && score.getNetwork().equalsIgnoreCase(network)) {
                existingScore = score;
                break;
            }
        }

        if (existingScore != null) {
            existingScore.setScore(existingScore.getScore() - 10);
        } else {
            // Create a new score with default 100 points and then deduct
            NetworkScore newScore = new NetworkScore(area, network, 90);
            scores.add(newScore);
        }
        System.out.println("Complaint filed successfully! 10 points deducted from " + network + " in " + area + ".");
    }

    public void viewScores() {
        System.out.println("\n--- View Network Scores ---");
        System.out.print("Enter area name to view scores: ");
        String area = scanner.nextLine();

        List<NetworkScore> areaScores = new ArrayList<>();
        for (NetworkScore score : scores) {
            if (score.getArea().equalsIgnoreCase(area)) {
                areaScores.add(score);
            }
        }

        if (areaScores.isEmpty()) {
            System.out.println("No scores found for the area: " + area);
            return;
        }

        // Sort scores in descending order
        Collections.sort(areaScores, Comparator.comparingInt(NetworkScore::getScore).reversed());

        System.out.println("Network scores for " + area + ":");
        for (NetworkScore score : areaScores) {
            System.out.println(score.getNetwork() + ": " + score.getScore());
        }
    }

    public void markResolved() {
        System.out.println("\n--- Mark Complaint as Resolved ---");
        List<Complaint> userUnresolvedComplaints = new ArrayList<>();
        
        // Find unresolved complaints by the logged-in user
        int index = 1;
        for (Complaint complaint : complaints) {
            if (complaint.getUsername().equals(loggedInUser.getUsername()) && !complaint.isResolved()) {
                userUnresolvedComplaints.add(complaint);
                System.out.println(index + ". Network: " + complaint.getNetwork() + ", Category: " + complaint.getCategory() + ", Area: " + complaint.getArea());
                index++;
            }
        }

        if (userUnresolvedComplaints.isEmpty()) {
            System.out.println("You have no unresolved complaints.");
            return;
        }

        System.out.print("Enter the number of the complaint to mark as resolved: ");
        int complaintIndex = Integer.parseInt(scanner.nextLine());
        
        if (complaintIndex > 0 && complaintIndex <= userUnresolvedComplaints.size()) {
            Complaint selectedComplaint = userUnresolvedComplaints.get(complaintIndex - 1);
            selectedComplaint.setResolved(true);
            
            // Add points back to the network score
            for (NetworkScore score : scores) {
                if (score.getArea().equalsIgnoreCase(selectedComplaint.getArea()) && score.getNetwork().equalsIgnoreCase(selectedComplaint.getNetwork())) {
                    score.setScore(score.getScore() + 10);
                    break;
                }
            }
            System.out.println("Complaint marked as resolved successfully! 10 points added back to " + selectedComplaint.getNetwork() + ".");
        } else {
            System.out.println("Invalid complaint number.");
        }
    }
}