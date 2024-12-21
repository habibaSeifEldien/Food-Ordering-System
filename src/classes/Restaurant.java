package classes;
import Exceptions.Dishfound;
import Exceptions.NonNumericInputException;
import txt.handles;
import java.io.*;
import java.util.*;

public class Restaurant {
    private String name;
    private Menu menu;
    private double deliveryFee;
    private String category;
    private String area;
    private String government;
    private double serviceFee;
    private int PreparingTime;
    private int DeliveryTime;
    private Scanner scanner = new Scanner(System.in);
    public static handles filee = new handles();
    // Constructors
    public Restaurant(String name, String category, String government, String area, float fee) {
        this.name = name;
        this.category = category;
        this.government = government;
        this.area = area;
        this.deliveryFee = fee;
        this.menu = new Menu();
    }
    public Restaurant() {
        this.menu = new Menu();
    }
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu getmenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(float deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(float serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getPreparingTime() {
        return PreparingTime ;
    }

    public void setPreparingTime(int PreparingTime) {
        this.PreparingTime = PreparingTime;
    }
    public int getDeliveryTime()
    {
        return DeliveryTime;
    }
    public void setDeliveryTime(int deliveryTime)
    {
        this.DeliveryTime=deliveryTime;
    }


    // Methods
    public void addRestaurant() {
        System.out.println("Enter the name of the restaurant:");
        String name = scanner.nextLine();
        System.out.println("Enter the category of the restaurant:");
        String category = scanner.nextLine();
        System.out.println("Enter the government of the restaurant:");
        String government = scanner.nextLine();
        System.out.println("Enter the area of the restaurant:");
        String area = scanner.nextLine();
        System.out.println("Enter the fee of the restaurant:");
        float fee = scanner.nextFloat();
        System.out.println("Enter the number of dishes:");
        int numDishes = scanner.nextInt();

        Restaurant restaurant = new Restaurant(name, category, government, area, fee);

        for (int i = 0; i < numDishes; i++) {
            System.out.println("Enter the name of the dish:");
            scanner.nextLine(); // Consume the newline
            String dishName = scanner.nextLine();
            System.out.println("Enter the description:");
            String description = scanner.nextLine();
            System.out.println("Enter the type:");
            String type = scanner.nextLine();
            System.out.println("Enter the spicy level:");
            String spicy = scanner.nextLine();
            System.out.println("Enter the price:");
            int price = scanner.nextInt();
            try {
                restaurant.getmenu().addDishtofoundRes(dishName, description, price, type, spicy);
            } catch (Dishfound e) {
                System.out.println(e.getMessage());
            } catch (NonNumericInputException e) {
                throw new RuntimeException(e);
            }
        }
        filee.restaurants.add(restaurant);
        filee.saveToFile();
        filee.loadFromFile();
    }
    public String displayRestaurantsByGov(String governate) {
        String gov = "";
        boolean found = false;
        for (Restaurant restaurant : filee.restaurants) {
            if (restaurant.getGovernment().equalsIgnoreCase(governate.trim())) {
                found = true;
                gov= restaurant.getName() + "\n";
            }
        }
        if (!found) {
            return ("No restaurants found in " + governate + ".");
        }
        else{
            return gov;
        }
    }

    public String displayRestaurantsByArea(String ar) {
        String ress = "";
        boolean found = false;
        for (Restaurant restaurant : filee.restaurants) {
            if (restaurant.getArea().equalsIgnoreCase(ar.trim())) {
                found = true;
                ress= restaurant.getName() + "\n";
            }
        }
        if (!found) {
            return ("No restaurants found in " + ar + ".");
        }
        else{
            return ress;
        }
    }

    public Restaurant Searchforres(String name) {
        for (Restaurant res : filee.restaurants) {
            if (res.getName().equalsIgnoreCase(name)) {
                return res;
            }
        }
        return null;
    }

    public String displayMenu(String restaurantName) {
        String resmenu = "";
        for (Restaurant restaurant : filee.restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                resmenu += ("Menu for " + restaurantName + ":"+'\n');
                resmenu +=restaurant.getmenu().DisplayOrderbyType();
            }
        }
        return resmenu;
    }

    public String displayAllDishes() {
        String result = "Restaurant: " + getName() + "\n";
        result += menu.DisplayOrderbyType();  // Concatenate the result of DisplayOrderbyType()
        return result; // Return the full string
    }

    public static void removeRestaurant(Restaurant res) {
        filee.restaurants.remove(res);
        System.out.println(res.getName() + " has been removed.");
        filee.saveToFile();
    }

    @Override
    public String toString() {
        return "Restaurant Name: " + name +
                "\nCategory: " + category +
                "\nGovernment: " + government +
                "\nArea: " + area +
                "\nFee: " + deliveryFee +
                "\nMenu: \n" + getmenu().toString();
    }
}
