package classes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private int age;
    private String gender;
    public User currentUser = null;
    Order order=new Order();
    public static ArrayList<User> users = new ArrayList<>();
    private static final File userfile = new File("C:\\Users\\asus\\IdeaProjects\\Food Ordering Managment\\src\\txt\\users.txt");

    public User(String FirstName, String LastName, String email, String password, String address, int mobileNumber, String country, int age, String gender) {
        super(FirstName, LastName, email, password);
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.country = country;
        this.age = age;
        this.gender = gender;
        this.order=new Order();
        //order.loadFromFile();
    }
    public User(){}

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }


    public static String createAccount(String FirstName, String LastName, String email, String password, String address, int mobileNumber, String country, int age, String gender) {

        if (FirstName == null || FirstName.trim().isEmpty() || LastName == null || LastName.trim().isEmpty()
                || email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()
                || address == null || address.trim().isEmpty() || country == null || country.trim().isEmpty()
                || gender == null || gender.trim().isEmpty() || age <= 0 || mobileNumber <= 0) {
            return "All fields must be filled correctly";
        } else if (password.length() < 5) {
            return "Password must be at least 5 characters long";
        } else if (!email.contains("@") || !email.contains(".") || !email.contains(("com"))) {
            return "Please enter a valid email address (Ex: johndoe@gmail.com)";
        }

        User newUser = new User(FirstName, LastName, email, password, address, mobileNumber, country, age, gender);
        users.add(newUser);

        newUser.saveToFile();
        return "Account created successfully and saved to file.";
    }



    @Override
    public boolean login(String enteredEmail, String enteredPassword) {
        for (User user : users) {
            System.out.println("Checking user: " + user.getEmail() + " with password: " + user.getPassword());
            if (user.getEmail().equalsIgnoreCase(enteredEmail) && user.getPassword().equalsIgnoreCase(enteredPassword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout(){
        if (currentUser != null)
        {
            System.out.println("logout successful for user : "+ currentUser.getFirstName());
            currentUser = null;
        }
        else {System.out.println("This user is not currently logged in");}
    }

    public static void displayAllUsers() {
        for (User user : users) {
            System.out.println("First name: " + user.getFirstName() + " , Last name: " + user.getLastName() + " , Email: " + user.getEmail() + " , password: " + user.getPassword() + " , Address: " + user.getAddress() + " , mobile number: " + user.getMobileNumber() + " , Country: " + user.getCountry() + " , age: " + user.getAge() + " , gender: " + user.getGender());
        }
    }

    public void ViewOrders() {
        boolean foundOrders = false;
        for (Order or : Order.orderList) {
            if (this.getFirstName().equalsIgnoreCase(or.user.getFirstName())) {
                System.out.println(or);
                foundOrders = true;
            }
        }
        if (!foundOrders) {
            System.out.println("No orders found for this user.");
        }
    }
    public void saveToFile() {
        try (FileWriter writer = new FileWriter(userfile)) { // 'true' appends to the file instead of overwriting
            for (User u : users) {
                // Write each user's details on a new line
                writer.write(u.FirstName + ',' + u.LastName + ',' + u.email + ',' +
                        u.password + ',' + u.address + ',' + u.mobileNumber + ',' + u.country + ',' + u.age + ',' + u.gender + '\n');
            }
            System.out.println("Users saved to file");
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
                if (details.length < 9) {
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
                    int age = Integer.parseInt(details[7].trim());
                    String gender = details[8].trim();

                    User us = new User(firstname, lastname, email, pass, addr, mobilenum, country, age, gender);
                    users.add(us);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping user with invalid mobile number: " + detailsLine);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }







}