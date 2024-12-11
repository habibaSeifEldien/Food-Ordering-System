import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class DeliveryStaff {
    private String firstname;
    private String lastname;
    private String Government;
    private boolean Available;
    private double review;
    private static File REVIEW_FILE = new File("C:\\Users\\asus\\IdeaProjects\\thotho\\src\\reviewss.txt");
    public ArrayList<DeliveryStaff> staff = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public DeliveryStaff(String firstname, String lastname, String Government, double review, boolean Available) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.Government = Government;
        this.Available = Available;
        this.review = review;
    }
    public DeliveryStaff() {
        setReview(review);
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


    public void saveToFile() {
        try (FileWriter writer = new FileWriter(REVIEW_FILE)) {
            for (DeliveryStaff member : staff) {
                writer.write(member.firstname + "," + member.lastname + "," +
                        member.Government + "," + member.review + "," +
                        member.Available + "\n");
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save data.");
        }
    }

    public void loadFromFile() {
        staff.clear(); // Clear existing data only if loading succeeds
        try (Scanner fileScanner = new Scanner(REVIEW_FILE)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] details = line.split(",");
                if (details.length < 5) {
                    System.out.println("Skipping malformed entry: " + line);
                    continue;
                }
                try {
                    String firstname = details[0].trim();
                    String lastname = details[1].trim();
                    String government = details[2].trim();
                    double review = Double.parseDouble(details[3].trim());
                    boolean available = Boolean.parseBoolean(details[4].trim());

                    DeliveryStaff member = new DeliveryStaff(firstname, lastname, government, review, available);
                    staff.add(member);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping entry due to invalid number format: " + line);
                }
            }
            System.out.println("Data loaded successfully!");
        } catch (FileNotFoundException e) {
             System.out.println("File not found: " + e.getMessage());
        }
    }


    public void add_deliverymember() {
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
        System.out.println("Enter State of the Delivery Member:");
        boolean state = scanner.nextBoolean();

        DeliveryStaff member = new DeliveryStaff(firstname, secondname, government, review, state);
        staff.add(member);
        saveToFile();
        //loadFromFile();
    }

    public DeliveryStaff searchByGovernment(Restaurant resturant) {
        boolean found = false;
        for (DeliveryStaff member : staff) {
            if (member.getGovernment().equalsIgnoreCase(resturant.getGovernment()) && member.Available) {
                // Print details of the matching delivery staff
                System.out.println("Found Delivery Member:");
                System.out.println("Name: " + member.getFirstname() + " " + member.getLastname());
                System.out.println("Government: " + member.getGovernment());
                System.out.println("Review: " + member.getReview() + " / 5.0");
                found = true;
                member.Available = false;
                return member;
            }
        }
        if (!found) {
            System.out.println("No matching delivery members found in " + resturant.getGovernment());
        }
        return null;

    }
    public void removedeliverymember(DeliveryStaff delivery){
        staff.remove(delivery);
        System.out.println(delivery.firstname + delivery.lastname + "has been removed.");
        saveToFile();
    }

    public void displayDetails() {
        System.out.println("Delivery Staff Details:");
        System.out.println("Name: " + this.firstname + " " + this.lastname);
        System.out.println("Location: " + this.Government);
        System.out.println("Review: " + this.review + "/5.0");
    }
}
