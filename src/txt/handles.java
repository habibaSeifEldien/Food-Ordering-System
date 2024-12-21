package txt;

import Exceptions.Dishfound;
import Exceptions.NonNumericInputException;
import classes.Restaurant;
import classes.dish;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class handles {
    private File file = new File("C:\\Users\\asus\\IdeaProjects\\Food Ordering Managment\\src\\txt\\hash.txt");
    public   ArrayList<Restaurant> restaurants = new ArrayList<>();

    // Static method to load restaurants from the file
    public void loadFromFile() {
        restaurants.clear(); // Clear existing data
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String name = fileScanner.nextLine().trim();
                if (name.isEmpty()) continue;

                if (fileScanner.hasNextLine()) {
                    String detailsLine = fileScanner.nextLine().trim();
                    String[] details = detailsLine.split(",");

                    if (details.length < 4) {
                        System.out.println("Skipping malformed restaurant entry: " + detailsLine);
                        continue;
                    }
                    try {
                        String category = details[0].trim();
                        String government = details[1].trim();
                        String area = details[2].trim();
                        float fee = Float.parseFloat(details[3].trim());

                        Restaurant restaurant = new Restaurant(name, category, government, area, fee);

                        // Read dishes for the menu
                        while (fileScanner.hasNextLine()) {
                            String menuLine = fileScanner.nextLine().trim();
                            if (menuLine.isEmpty()) break;

                            String[] dishDetails = menuLine.split(",");
                            if (dishDetails.length >= 5) {
                                String dishName = dishDetails[1].trim();
                                String description = dishDetails[2].trim();
                                int price = Integer.parseInt(dishDetails[3].trim());
                                String type = dishDetails[0].trim();
                                String spicy = dishDetails[4].trim();

                                try {
                                    restaurant.getmenu().addDishtofoundRes(dishName, description, price, type, spicy);
                                } catch (Dishfound e) {
                                    System.out.println(e.getMessage());
                                } catch (NonNumericInputException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                System.out.println("Skipping malformed dish entry: " + menuLine);
                            }
                        }
                        restaurants.add(restaurant); // Add restaurant to the list
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping restaurant with invalid fee: " + detailsLine);
                    }
                }
            }
            System.out.println("Data loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(file)) {
            for (Restaurant restaurant : restaurants) {
                writer.write(restaurant.getName() + "\n");
                writer.write(restaurant.getCategory()+ "," + restaurant.getGovernment() + "," + restaurant.getArea() + "," + restaurant.getDeliveryFee() + "\n");
                // Write dishes
                for (dish d : restaurant.getmenu().getDishes()) {
                    writer.write(  d.getType() +"," +d.getName() + "," + d.getDescription() + "," +
                            d.getPrice() + "," +   d.getSpicy() + "\n");
                }
                writer.write("\n"); // Separator for restaurants
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}


