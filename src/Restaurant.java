import java.io.*;
import java.util.*;

public class Restaurant {
    private String name;
    private Menu menu;
    private float deliveryFee;
    private String category;
    private String area;
    private String government;
    private float serviceFee;
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

    public float getDeliveryFee() {
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

    public float getServiceFee() {
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
    public void setDeliveryTime()
    {
        this.DeliveryTime=DeliveryTime;
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
                restaurant.getmenu().addDish(dishName, description, price, type, spicy);
            } catch (Dishfound e) {
                System.out.println(e.getMessage());
            }
        }
        filee.restaurants.add(restaurant);
        filee.saveToFile();
        filee.loadFromFile();
    }
    public void displayRestaurantsByGovernment() {
        System.out.println("Which Government do you need?: ");
        String gov = scanner.next().trim();

        boolean found = false;
        for (Restaurant restaurant : filee.restaurants) {
            if (restaurant.getGovernment().equalsIgnoreCase(gov)) {
                found = true;
                System.out.println(restaurant.getName() + " - ");
                restaurant.getmenu().DisplayOrderbyType();
            }
        }
        if (!found) {
            System.out.println("No restaurants found in " + gov + ".");
        }
    }

    public void displayRestaurantsByArea() {
        System.out.println("Which Area do you need?: ");
        String ar = scanner.nextLine().trim();

        boolean found = false;
        for (Restaurant restaurant : filee.restaurants) {
            if (restaurant.getArea().equalsIgnoreCase(ar)) {
                found = true;
                System.out.println(restaurant.getName() + " - ");
                restaurant.getmenu().DisplayOrderbyType();
            }
        }
        if (!found) {
            System.out.println("No restaurants found in " + ar + ".");
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

    public void displayMenu(String restaurantName) {
        for (Restaurant restaurant : filee.restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                System.out.println("Menu for " + restaurantName + ":");
                restaurant.getmenu().DisplayOrderbyType();
                return;
            }
        }
        System.out.println("Restaurant not found.");
    }

    public void displayAllDishes() {
        for (Restaurant restaurant : filee.restaurants) {
            System.out.println("Restaurant: " + restaurant.getName());
            restaurant.getmenu().DisplayOrderbyType();
            System.out.println('\n');
        }
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
