import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class User extends Registration {
    private String address;
    private String country;
    private int mobileNumber;
    public  User currentUser = null;
    private static ArrayList<User> users = new ArrayList<>();
    private static final File userfile= new File("users.txt");
    public User(String FirstName, String LastName,  String email, String password, String address , int mobileNumber , String country) {
        super(FirstName, LastName, email, password);
        this.address = address;
        this.mobileNumber=mobileNumber;
        this.country=country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getMobileNumber() {return mobileNumber;}

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }


    public static void createAccount(String FirstName, String LastName, String email, String password, String address,int mobileNumber , String country) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                System.out.println("Email already exists. Please try again.");
                return;
            }
        }
        User newUser = new User(FirstName, LastName ,email, password, address,mobileNumber ,country);
        users.add(newUser);

        newUser.saveToFile();
        System.out.println("Account created successfully and saved to file.");
    }


    @Override
    public boolean login(String enteredEmail, String enteredPassword) {
        for (User user : users) {
            System.out.println("Checking user: " + user.getEmail() + " with password: " + user.getPassword());
            if (user.getEmail().equals(enteredEmail) && user.getPassword().equals(enteredPassword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout(){
        if (currentUser != null){
            System.out.println("logout successful for user : "+ currentUser.getFirstName());
            currentUser = null;
        }
        else {
            System.out.println("This user is not currently logged in");
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(userfile, true)) { // 'true' appends to the file instead of overwriting
            for (User u : users) {
                // Write each user's details on a new line
                writer.write(u.FirstName + ',' + u.LastName + ',' + u.email + ',' +
                        u.password + ',' + u.address + ',' + u.mobileNumber + ',' + u.country + '\n');
            }
            System.out.println("Users saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        users.clear(); // Clear existing data

        try (Scanner fileScanner = new Scanner(userfile)) { // Create File object for the Scanner
            while (fileScanner.hasNextLine()) {
                String detailsLine = fileScanner.nextLine().trim();
                if (detailsLine.isEmpty()) continue;

                String[] details = detailsLine.split(",");

                if (details.length < 7) {
                    System.out.println("Skipping invalid data line: " + detailsLine);
                    continue;
                }

                try {
                    String firstname = details[0].trim();
                    String lastname = details[1].trim();
                    String email = details[2].trim();
                    String pass = details[3].trim();
                    String addr = details[4].trim();
                    int mobilenum = Integer.parseInt(details[5].trim());  // Correct index for mobile number
                    String country = details[6].trim();


                    User us = new User(firstname, lastname, email, pass, addr, mobilenum, country);
                    users.add(us);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping user with invalid mobile number: " + detailsLine);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }


    public static void displayAllUsers() {
        for (User user : users) {
            System.out.println("First name: " + user.getFirstName() + " , Last name: " + user.getLastName() + " , Email: " + user.getEmail() + " , password: " + user.getPassword() + " , Address: " + user.getAddress() + " , mobile number: " + user.getMobileNumber() + " , Country: " + user.getCountry());
  }
}

}
