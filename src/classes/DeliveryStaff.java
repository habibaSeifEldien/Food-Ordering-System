package classes;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DeliveryStaff {
    private String firstname;
    private String lastname;
    private String Government;
    private String RestaurantName;
    private double review;
    private int orderid;
    public static final File REVIEW_FILE = new File("C:\\Users\\N\\OneDrive\\Desktop\\reviewss.txt");
    public ArrayList<DeliveryStaff> staff = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public DeliveryStaff(String firstname, String lastname, String Government, String restaurantName, double review,int orderid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.Government = Government;
        this.RestaurantName = restaurantName;
        this.review=review;
        this.orderid=orderid;

    }
    public DeliveryStaff(){

    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setGovernment(String government) {
        this.Government = government;
    }

    public String getGovernment() {
        return Government;
    }
    public String getRestaurantName(){
        return RestaurantName;
    }

    public void setReview(double review) {
        if (review >= 0 && review <= 5) {
            this.review = review;
        } else {
            System.out.println("Invalid review score. Must be between 0 and 5. Setting default value (0).");
            this.review = 0;
        }
    }

    public double getReview() {
        return review;
    }

    public void updateReview(double newReview) {
        if (newReview >= 0 && newReview <= 5) {
            this.review = (this.review + newReview) / 2.0; // Update review as an average
        } else {
            System.out.println("Invalid review score. Must be between 0 and 5.");
        }
    }
    public void setOrder(int orderid){
        this.orderid=orderid;
    }
    public int getOrderid(){return orderid;}

    public void saveToFile() throws FileNotFoundException{
        try (FileWriter writer = new FileWriter(REVIEW_FILE)) {
            for (DeliveryStaff member : staff) {
                writer.write(member.firstname+","+member.lastname + "," + member.Government + "," + member.RestaurantName + "," +member.review + "," +member.orderid +"\n");
                writer.write("\n"); // Separator for restaurants
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadFromFile() throws FileNotFoundException{
        staff.clear(); // Clear existing data
        try (Scanner fileScanner = new Scanner(REVIEW_FILE)) {
            while (fileScanner.hasNextLine()) {
                String detailsLine = fileScanner.nextLine().trim();
                if (detailsLine.isEmpty()) continue; // Skip empty lines

                String[] details = detailsLine.split(","); // Split the line into details

                if (details.length < 6) { // Ensure the line has all required fields
                    System.out.println("Skipping malformed entry: " + detailsLine);
                    continue;
                }

                try {

                    String firstname = details[0].trim();
                    String lastname = details[1].trim();
                    String government = details[2].trim();
                    String restaurantName = details[3].trim();
                    double review = Double.parseDouble(details[4].trim());
                    int ord= Integer.parseInt(details[5].trim());
                    // Create and add a new DeliveryStaff member
                    DeliveryStaff member = new DeliveryStaff(firstname, lastname, government, restaurantName, review,ord);
                    staff.add(member);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in entry: " + detailsLine);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + REVIEW_FILE.getAbsolutePath());
        }
    }

    public  void add_deliverymember() throws FileNotFoundException {
        System.out.println("Enter the FirstName of the Delivery Member :");
        String firstname = scanner.nextLine();
        System.out.println("Enter the SecondName of the Delivery Member:");
        String secondname = scanner.nextLine();
        System.out.println("Enter the government of the Delivery Member:");
        String government = scanner.nextLine();
        System.out.println("Enter the Restaurant name of the Delivery Member:");
        String restaurnatname = scanner.nextLine();
        System.out.println("Enter the Review of the Delivery Member:");
        float review = scanner.nextFloat();
        int orderid1=0;
        DeliveryStaff member = new DeliveryStaff(firstname, secondname, government, restaurnatname, review,orderid1);
        staff.add(member);
        saveToFile();
        loadFromFile();
    }
    public DeliveryStaff searchByGovernment(Restaurant restaurant) {
        boolean found = false;

        for (DeliveryStaff member : staff) {
            if (member.getGovernment().equalsIgnoreCase(restaurant.getGovernment()) && member.getOrderid()==0) {

                // Print details of the matching delivery staff
                System.out.println("Found Delivery Member:");
                System.out.println("Name: " + member.getFirstname() + " " + member.getLastname());
                System.out.println("Government: " + member.getGovernment());
                System.out.println("Restaurant Name: " + member.RestaurantName);
                System.out.println("Review: " + member.getReview() + " / 5.0");
                found = true;
                return member;
            }
        }
        if (!found) {
            System.out.println("No matching delivery members found in " + restaurant.getGovernment());
        }

        return null;
    }
    public void removedeliverymember(DeliveryStaff delivery) throws FileNotFoundException {
        staff.remove(delivery);
        System.out.println(delivery.firstname + delivery.lastname + "has been removed.");
        saveToFile();
    }
    public void displayDetails() {
        System.out.println("Delivery Staff Details:");
        System.out.println("Name: " + firstname + " " + lastname);
        System.out.println("Location: " + Government);
        System.out.println("Restaurant Name: " + RestaurantName);
        System.out.println("Review: " + review + " / 5.0");
    }
}