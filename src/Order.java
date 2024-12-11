import java.util.ArrayList;
import java.util.Scanner;

public class Order {

    public float totalFee;
    String Payment;
    User UserObj;
    Restaurant res;
    public Cart c;
    String orderState = "Confirmed";
    long stateStartTime;
    static ArrayList<Order> orders = new ArrayList<>();
    static int globalOrderId = 35656;
    int orderId;
    Scanner scanner = new Scanner(System.in);
    Boolean confm = false;

    Order(User UserObj, Restaurant res, Cart c, float totalFee) {
        this.res = res;
        this.UserObj = UserObj;
        this.c = c;
        this.totalFee = totalFee;
        this.orderId = globalOrderId++;
    }

    public static Order searchForId(int orderId) {
        for (Order order : orders) {
            if (order.orderId == orderId) {
                System.out.println("Order found with ID: " + orderId);
                return order;
            }
        }
        System.out.println("Order with ID " + orderId + " not found.");
        return null;
    }

    float calcTotal(Cart c) {
        this.totalFee = (c.Total + res.getDeliveryFee() + res.getServiceFee());
        return totalFee;
    }

    void fillPay() {
        System.out.println("Cash or Credit/Digital wallets");
        String Pay = scanner.nextLine().trim().toLowerCase();
        this.Payment = Pay;
    }

    boolean confirmation() {
        fillPay();
        System.out.println("Service Fee : " + res.getServiceFee());
        System.out.println("Delivery Fee : " + res.getDeliveryFee());
        System.out.println("Total fee : " + calcTotal(c));
        System.out.println("Payment method : " + Payment);
        System.out.println("Address : " + UserObj.getAddress());
        System.out.println("Estimated Time for your order : " + res.getEstimatedTime());
        System.out.println("Your order ID : " + orderId);
        System.out.println("Enter 'c' to confirm or 'cancel' to cancel");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("c")) {
            System.out.println("Order confirmed");
            confm = true;
            orders.add(this);
            stateStartTime = System.currentTimeMillis();
            display();
            updateOrderState();
            trackOrderStatus();
        } else if (input.equals("cancel")) {
            System.out.println("Order cancelled");
            return false;
        }

        return confm;
    }

    void display() {
        System.out.println("Restaurant : " + res.getName());
        System.out.println("TotalFee : " + totalFee);
        System.out.println("Order ID : " + orderId);
    }

    public void updateOrderState() {
        long currentTime = System.currentTimeMillis();
        long totalElapsedTime = currentTime - stateStartTime;
        if (totalElapsedTime >= (res.PreparingTime + res.DeliveryTime)) {
            orderState = "Completed";
        }
        else if (totalElapsedTime >= res.PreparingTime) {
            orderState = "Being Delivered";
        }
        else {
            orderState = "Being Prepared";
        }
    }

    public void displayOrderState() {
        updateOrderState();
        System.out.println("Current Order State: " + orderState);
    }
    void trackOrderStatus() {
        System.out.println("To track the order, type 's' to check the status or 'exit' to finish.");
        while (true) {
            try {
                String status = scanner.nextLine().trim().toLowerCase();
                if (status.equals("s")) {
                    displayOrderState();
                } else if (status.equals("exit")) {
                    System.out.println("Exiting tracking.");
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
 }
}
}
