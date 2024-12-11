import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the System");
            System.out.println("1. Create User Account");
            System.out.println("2. Login as User");
            System.out.println("3. Display All Users");
            System.out.println("4. Login as Admin");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter details to create a user account:");
                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Mobile Number: ");
                    int mobileNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Country: ");
                    String country = scanner.nextLine();

                    User.createAccount(firstName, lastName, email, password, address, mobileNumber, country);
                    System.out.println("User account created successfully.");
                    break;

                case 2:
                    System.out.println("Enter login details:");
                    System.out.print("Email: ");
                    String userEmail = scanner.nextLine();
                    System.out.print("Password: ");
                    String userPassword = scanner.nextLine();

                    User user = new User("", "", "", "", "", 0, "");
                    user.loadFromFile();

                    if (user.login(userEmail, userPassword)) {
                        System.out.println("User login successful.");
                    } else {
                        System.out.println("User login failed. Invalid credentials.");
                    }
                    break;

                case 3:
                    System.out.println("Displaying all users:");
                    User u = new User("", "", "", "", "", 0, "");
                    u.loadFromFile();
                    u.displayAllUsers();
                    break;

                case 4:
                    System.out.println("Enter login details:");
                    System.out.print("Email: ");
                    String adminLoginEmail = scanner.nextLine();
                    System.out.print("Password: ");
                    String adminLoginPassword = scanner.nextLine();

                    Admin adminLogin = new Admin("", "", "", "", 0);
                    adminLogin.loadFromFile();

                    if (adminLogin.login(adminLoginEmail, adminLoginPassword)) {
                        System.out.println("Admin login successful.");
                    } else {
                        System.out.println("Admin login failed. Invalid credentials.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }
}
