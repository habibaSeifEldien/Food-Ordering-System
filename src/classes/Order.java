package classes;
import Exceptions.InvalidInputException;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Order implements Handler {
    public User user;
    private Cart cart;
    private int orderId;
    private double totalFee;
    public Restaurant restaurant;
    public String paymentMethod;
    private String orderStatus = "Confirmed";
    private long orderStartTime;
    private DeliveryStaff deliveryStaff;
    private boolean isConfirmed = false;  // Track if the order is confirmed
    static ArrayList<Order> orderList = new ArrayList<>();
    private static int nextOrderId = 10;
    File file = new File("C:\\Users\\asus\\IdeaProjects\\Food Ordering Managment\\src\\txt\\Newfile.txt");

    public Order(User user, Restaurant restaurant, Cart cart) {
        this.user = user;
        this.restaurant = restaurant;
        this.cart = cart;
        this.orderId = getNextOrderId();
    }
    public Order(){

    }
    public Order searchById(int ordId){
        loadFromFile();
        for(Order o:orderList){
            if(o.getId()==ordId){
                return o;
            }
        }
        return null;
    }

    public  Restaurant getRestaurant(){
        return restaurant;
    }
    private static synchronized int getNextOrderId() {
        return nextOrderId++;
    }

    public int getId() {
        return orderId;
    }


    @Override
    public boolean confirmOrder() {
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            return false;
        }

        isConfirmed = true;
        orderStartTime = System.currentTimeMillis();
        trackOrderStatus();
        orderList.add(this);



        return true;
    }


    public double calculateTotalFee() {
        // Fetching delivery and service fees from the restaurant
        this.totalFee = cart.total + restaurant.getDeliveryFee() + restaurant.getServiceFee();
        return totalFee;
    }

    public boolean validateInput(String input, String type) throws InvalidInputException {
        switch (type) {
            case "creditId":
            case "walletId":
                // Check if the input contains only digits and is of length 11
                if (!input.matches("\\d{11}")) {
                    throw new InvalidInputException("Please enter a valid " + type + " with exactly 11 digits.");
                }
                break;

            case "cvv":
                // Check if the CVV contains exactly 3 digits
                if (!input.matches("\\d{3}")) {
                    throw new InvalidInputException("Please enter a valid CVV with exactly 3 digits.");
                }
                break;

            default:
                throw new InvalidInputException("Unknown input type.");
        }
        return true;
    }
    public boolean selectPaymentMethod(String selectedMethod, String creditId, String creditCvv, String walletId) {
        this.paymentMethod = selectedMethod;

        try {
            switch (paymentMethod) {
                case "Credit":
                    // Validate credit ID and CVV
                    if (creditId.isEmpty() || creditCvv.isEmpty()) {
                        return false;
                    }
                    validateInput(creditId, "creditId");
                    validateInput(creditCvv, "cvv");
                    break;

                case "Cash":
                    // No additional details needed for Cash
                    break;

                case "Digital Wallet":
                    // Validate wallet ID
                    if (walletId.isEmpty()) {
                        return false;
                    }
                    validateInput(walletId, "walletId");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid payment method.");
                    return false;
            }
        } catch (InvalidInputException e) {
            // Show the error message to the user
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false; // Return false to indicate the method failed
        }
        return true;
    }

    public void updateOrderStatus() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - orderStartTime;

        if (elapsedTime >= (restaurant.getPreparingTime() + restaurant.getDeliveryTime())) {
            orderStatus = "Completed";
        } else if (elapsedTime >= restaurant.getPreparingTime()) {
            orderStatus = "Being Delivered";
        } else {
            orderStatus = "Being Prepared";
        }
    }


    @Override
    public void trackOrderStatus() {
        updateOrderStatus();
        orderStatus = getOrderStatus();

    }

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order ID: ").append(orderId).append("\n");
        details.append("User Name: ").append(user.getFirstName()).append("\n");
        details.append("User Address: ").append(user.getAddress()).append("\n");
        details.append("Restaurant: ").append(restaurant.getName()).append("\n");
        details.append("Cart Total: ").append(String.format("%.2f", cart.total)).append(" USD\n");

        // Remove the order status and delivery/service fee from the order details if not required
        details.append("Total Fee: ").append(String.format("%.2f", calculateTotalFee())).append(" USD\n");

        return details.toString();
    }


    public String getOrderStatus() {
        updateOrderStatus();
        return orderStatus;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
    public void cancelOrder() {
        Order canceledOrder = orderList.remove(orderList.size() - 1);
    }


    public String toString() {
        return "OrderID: " + orderId + " "+
                "Restaurant Name: " + restaurant.getName() + " " +
                "Foods: " + restaurant.displayMenu(restaurant.getName()) + " " +
                "TotalFee: " + totalFee;
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(orderId + "," + user.getFirstName() + "," + user.getAddress() + "," +
                    restaurant.getName() + "," + totalFee + "\n");
            System.out.println("Order saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving file.");
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        orderList.clear(); // Clear any existing orders
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] details = line.split(",");
                if (details.length < 5) {
                    continue;
                }

                try {
                    int orderId = Integer.parseInt(details[0].trim());
                    String userName = details[1].trim();
                    String userAddress = details[2].trim();
                    String restaurantName = details[3].trim();
                    double totalFee = Double.parseDouble(details[4].trim());

                    // Find the associated user from the existing list
                    User orderUser = null;
                    for (User user : User.users) {

                        if (user.getFirstName().equalsIgnoreCase(userName) && user.getAddress().equalsIgnoreCase(userAddress)) {
                            orderUser = user;
                            break;
                        }
                    }
                    if (orderUser != null) {
                        // Initialize Cart and Restaurant objects (assuming you have them in your application)
                        Restaurant restaurant = new Restaurant();
                        restaurant.setName(restaurantName);  // Ensure restaurant has a valid name
                        Cart cart = new Cart();  // Properly initialize Cart as needed

                        // Create the order
                        Order order = new Order(orderUser,restaurant, cart);
                        order.orderId = orderId;  // Set the order ID
                        order.totalFee = totalFee; // Set the total fee
                        orderList.add(order);  // Add the order to the list
                    } else {
                        System.out.println("User not found for order: " + userName);  // Debugging line
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping malformed order data: " + line);
                }
            }
            System.out.println("Orders loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }



}
