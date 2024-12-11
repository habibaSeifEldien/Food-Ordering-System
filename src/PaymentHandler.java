public interface PaymentHandler
{
    void selectPaymentMethod();
    boolean confirmOrder ();
    void updateOrderStatus();
    void display();
    void trackOrderStatus();
}
