import java.util.ArrayList;
import java.util.Scanner;

public class Order implements PaymentHandler {

    private float totalFee;
    private String paymentMethod;
    private User user;
    private Restaurant restaurant;
    private Cart cart;
    private String orderStatus = "Confirmed";
    private long orderStartTime;

    private static ArrayList<Order> orderList = new ArrayList<>();
    private static int nextOrderId = 35656;
    private int orderId;
    private Scanner scanner = new Scanner(System.in);
    private boolean isConfirmed = false;

    public Order(User user, Restaurant restaurant, Cart cart, float totalFee) {
        this.user = user;
        this.restaurant = restaurant;
        this.cart = cart;
        this.totalFee = totalFee;
        this.orderId = nextOrderId++;
    }

    public static Order searchById(int orderId) {
        for (Order order : orderList) {
            if (order.orderId == orderId) {
                System.out.println("Order found with ID: " + orderId);
                return order;
            }
        }
        System.out.println("Order with ID " + orderId + " not found.");
        return null;
    }

    public float calculateTotalFee() {
        this.totalFee = cart.Total + restaurant.getDeliveryFee() + restaurant.getServiceFee();
        return totalFee;
    }

    @Override
    public void selectPaymentMethod() {
        System.out.println("Select payment method: Cash, Credit, or Digital Wallets");
        this.paymentMethod = scanner.nextLine().trim().toLowerCase();

        switch (paymentMethod) {
            case "credit":
                System.out.println("Please enter credit ID ");
                long creditId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Please enter credit CVV");
                long creditCvv = scanner.nextInt();
                scanner.nextLine();
                break;

            case "cash":
                System.out.println("You will pay in Cash");
                break;

            default:
                System.out.println("Please enter wallet ID ");
                int walletId = scanner.nextInt();
                scanner.nextLine(); // clear buffer
                break;
        }
    }

    @Override
    public boolean confirmOrder() {
        selectPaymentMethod();
        System.out.println("Service Fee: " + restaurant.getServiceFee());
        System.out.println("Delivery Fee: " + restaurant.getDeliveryFee());
        System.out.println("Total Fee: " + calculateTotalFee());
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Address: " + user.getAddress());
        System.out.println("Estimated Time for your order: " + (restaurant.getDeliveryTime() + restaurant.getPreparingTime()) / 1000 + " min");
        System.out.println("Your Order ID: " + orderId);
        System.out.println("Enter 'c' to confirm or 'cancel' to cancel");

        String userInput = scanner.nextLine().trim().toLowerCase();

        if (userInput.equals("c")) {
            System.out.println("Order confirmed");
            isConfirmed = true;
            orderList.add(this);
            orderStartTime = System.currentTimeMillis();
            trackOrderStatus();
        } else if (userInput.equals("cancel")) {
            System.out.println("Order cancelled");
            return false;
        }

        return isConfirmed;
    }

    @Override
    public void display() {
        System.out.println("Restaurant: " + restaurant.getName());
        System.out.println("Total Fee: " + totalFee);
        System.out.println("Order ID: " + orderId);
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
        System.out.println("To track the order, type 's' to check the status or 'exit' to finish.");
        while (true) {
            try {
                String userInput = scanner.nextLine().trim().toLowerCase();
                if (userInput.equals("s")) {
                    displayOrderStatus();
                } else if (userInput.equals("exit")) {
                    System.out.println("Exiting tracking.");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void displayOrderStatus() {
        updateOrderStatus();
        System.out.println("Current Order Status: " + orderStatus);
    }
}
