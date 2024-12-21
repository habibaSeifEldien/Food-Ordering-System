package GUI;
import classes.*;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class OrderGui {
    private  final HashMap<String, Integer> itemQuantities = new HashMap<>();
    private  float deliveryFee = 18.99F;
    private float serviceFee = 3.99f;
    private static Order currentOrder;
    public void main(String[] args) {
        User user = new User();
        user.setFirstName("John Doe");
        user.setAddress("123 Street, City");
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Good Food Inc.");
        restaurant.setDeliveryFee(deliveryFee);
        restaurant.setServiceFee(serviceFee);
        restaurant.setPreparingTime(2000);
        restaurant.setDeliveryTime(2000);
        restaurant.setGovernment("Cairo");
        Cart cart = new Cart();
        cart.setTotal(122f);
        currentOrder = new Order(user, restaurant, cart);
        SwingUtilities.invokeLater(OrderGui::OrderSection);
    }

    public static class deliveryStaffPanel {
        private static JPanel panel;
        private static JLabel staffName;
        private static JLabel staffLocation;
        private static JLabel staffReview;
        private static JLabel staffImage;

        public deliveryStaffPanel() {
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setVisible(false); // Initially hidden

            staffName = new JLabel();
            staffLocation = new JLabel();
            staffReview = new JLabel();
            staffImage = new JLabel(); // Label to hold the image
            Font labelFont = new Font("Arial", Font.BOLD, 20); // Adjust size as needed
            staffName.setFont(labelFont);
            staffLocation.setFont(labelFont);
            staffReview.setFont(labelFont);

            panel.add(staffName);
            panel.add(staffLocation);
            panel.add(staffReview);
            panel.add(staffImage);
        }

        public static void setVisible(boolean b) {
        }

        public JPanel getPanel() {
            return panel;
        }

        public static void updateStaffInfo(DeliveryStaff selectedStaff) {
            if (selectedStaff != null) {
                staffName.setText("Name: " + selectedStaff.getFirstname() + " " + selectedStaff.getLastname());
                staffLocation.setText("Location: " + selectedStaff.getGovernment());
                staffReview.setText("Review: " + selectedStaff.getReview() + " / 5.0");

                // Display the image (adjust the path accordingly)
                try {
                    ImageIcon imageIcon = new ImageIcon("C:\\Users\\N\\OneDrive\\Images\\delivery.png"); // Replace with actual path
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Resize the image
                    imageIcon = new ImageIcon(scaledImage);
                    staffImage.setIcon(imageIcon);
                } catch (Exception ex) {
                    staffImage.setText("Image not available");
                }

                panel.setVisible(true); // Show the panel when staff info is available
            }
        }
    }


    public static void OrderSection() {

        JFrame frame = new JFrame("Order");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 900);
        frame.setLayout(null);

        JLabel header = new JLabel("Order", SwingConstants.CENTER);
        header.setBounds(100, 10, 400, 30);
        header.setFont(new Font("MV BOLI", Font.BOLD, 40));
        header.setForeground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to maximized

        GridBagConstraints gbc = new GridBagConstraints();
        // Order Details Section
        JTextArea orderDetails = new JTextArea();
        orderDetails.setText(currentOrder.getOrderDetails());
        orderDetails.setEditable(false);
        orderDetails.setBackground(new Color(250, 250, 250));
        orderDetails.setFont(new Font("Arial", Font.PLAIN, 18));
        orderDetails.setWrapStyleWord(true);
        orderDetails.setLineWrap(true);
        orderDetails.setCaretPosition(0);
        orderDetails.setBounds(100, 50, 400, 200);

        // Payment Method Selection Section
        JLabel paymentLabel = new JLabel("Select Payment Method:");
        paymentLabel.setBounds(100, 260, 200, 20);
        paymentLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        String[] paymentMethods = {"Cash", "Credit", "Digital Wallet"};
        JComboBox<String> paymentComboBox = new JComboBox<>(paymentMethods);
        paymentComboBox.setBounds(100, 290, 260, 30);

        JTextField creditIdField = new JTextField("Enter Credit ID");
        JTextField creditCvvField = new JTextField("Enter CVV");
        JTextField walletIdField = new JTextField("Enter Wallet ID");

        creditIdField.setBounds(100, 320, 260, 30);
        creditCvvField.setBounds(100, 360, 260, 30);
        walletIdField.setBounds(100, 400, 260, 30);

        // Action Buttons Section
        JButton confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.setBounds(100, 440, 170, 40);
        confirmOrderButton.setBackground(new Color(167, 86, 21));
        confirmOrderButton.setForeground(Color.WHITE);

        JButton trackOrderButton = new JButton("Track Order");
        trackOrderButton.setBounds(100, 490, 360, 50);
        trackOrderButton.setBackground(new Color(255, 165, 0));
        trackOrderButton.setForeground(Color.WHITE);
        trackOrderButton.setVisible(false);
        trackOrderButton.setEnabled(false);

        JButton cancelOrderButton = new JButton("Cancel Order");
        cancelOrderButton.setBounds(100, 560, 360, 40);
        cancelOrderButton.setBackground(new Color(232, 36, 65));
        cancelOrderButton.setForeground(Color.WHITE);
        cancelOrderButton.setVisible(false);

        JButton createNewOrderButton = new JButton("Create New Order");
        createNewOrderButton.setBounds(100, 610, 360, 40);
        createNewOrderButton.setBackground(new Color(0, 0, 255));
        createNewOrderButton.setForeground(Color.WHITE);
        // Initialize deliveryStaffPanel

        deliveryStaffPanel staffPanel = new deliveryStaffPanel();
        JPanel deliveryPanel = staffPanel.getPanel();
        deliveryPanel.setBounds(100, 600, 500, 350); // Adjust bounds as needed
        frame.add(deliveryPanel);


        // Add components to frame
        frame.add(header);
        frame.add(orderDetails);
        frame.add(paymentLabel);
        frame.add(paymentComboBox);
        frame.add(confirmOrderButton);
        frame.add(trackOrderButton);
        frame.add(cancelOrderButton);
        frame.add(createNewOrderButton);
        frame.add(walletIdField);
        frame.add(creditIdField);
        frame.add(creditCvvField);
        walletIdField.setVisible(false);
        creditIdField.setVisible(false);
        creditCvvField.setVisible(false);
        frame.setVisible(true);

        // Confirm Order Action
        confirmOrderButton.addActionListener(e -> {
            String selectedPaymentMethod = (String) paymentComboBox.getSelectedItem();
            boolean paymentSelected = currentOrder.selectPaymentMethod(selectedPaymentMethod,
                    creditIdField.getText().trim(), creditCvvField.getText().trim(), walletIdField.getText().trim());

            if (paymentSelected) {
                boolean confirmed = currentOrder.confirmOrder();
                if (confirmed) {
                    paymentLabel.setVisible(false);
                    paymentComboBox.setVisible(false);
                    creditIdField.setVisible(false);
                    creditCvvField.setVisible(false);
                    walletIdField.setVisible(false);
                    confirmOrderButton.setVisible(false);
                    trackOrderButton.setVisible(true);
                    trackOrderButton.setEnabled(true);
                    cancelOrderButton.setVisible(true);

                    // Fetch and display the delivery staff details
                    DeliveryStaff staff = new DeliveryStaff();
                    try {
                        staff.loadFromFile();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    DeliveryStaff selectedStaff = staff.searchByGovernment(currentOrder.restaurant);
                    // Update and show the staff panel
                    deliveryStaffPanel.updateStaffInfo(selectedStaff);

                    // Set visibility to true after confirmation
                    JOptionPane.showMessageDialog(frame, "Order Confirmed!");
                    currentOrder.saveToFile();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in the required payment details.");
            }
        });

        // Show the staff panel in the layout

        // Track Order Action
        trackOrderButton.addActionListener(e -> {
            currentOrder.trackOrderStatus();
            orderDetails.setText(currentOrder.getOrderDetails());
            JOptionPane.showMessageDialog(frame, "Current Order Status: " + currentOrder.getOrderStatus());
        });

        // Cancel Order Action
        cancelOrderButton.addActionListener(e -> {
            currentOrder.cancelOrder();
            JOptionPane.showMessageDialog(frame, "Order Canceled!");
            frame.dispose();
        });

        // Create New Order Action
        createNewOrderButton.addActionListener(e -> {
            // Clear all components from the frame
            frame.getContentPane().removeAll();

            // Set up a new empty page
            JLabel newHeader = new JLabel("New Order Page", SwingConstants.CENTER);
            newHeader.setBounds(100, 10, 400, 30);
            newHeader.setFont(new Font("MV BOLI", Font.BOLD, 30));
            newHeader.setForeground(Color.BLACK);

            JTextArea newOrderDetails = new JTextArea();
            newOrderDetails.setText("Start a new order...");
            newOrderDetails.setEditable(true);
            newOrderDetails.setBackground(new Color(250, 250, 250));
            newOrderDetails.setFont(new Font("Arial", Font.PLAIN, 18));
            newOrderDetails.setWrapStyleWord(true);
            newOrderDetails.setLineWrap(true);
            newOrderDetails.setBounds(100, 50, 400, 200);

            JButton goBackButton = new JButton("Go Back");
            goBackButton.setBounds(100, 300, 170, 40);
            goBackButton.setBackground(new Color(167, 86, 21));
            goBackButton.setForeground(Color.WHITE);
            goBackButton.addActionListener(ev -> {
                frame.getContentPane().removeAll();
                OrderSection();
            });

            // Add components for the new page
            frame.add(newHeader);
            frame.add(newOrderDetails);
            frame.add(goBackButton);

            // Refresh the frame
            frame.revalidate();
            frame.repaint();
        });

        // Payment ComboBox Action
        paymentComboBox.addActionListener(e -> {
            String selectedPaymentMethod = (String) paymentComboBox.getSelectedItem();
            if ("Credit".equals(selectedPaymentMethod)) {
                creditIdField.setVisible(true);
                creditCvvField.setVisible(true);
            } else if ("Digital Wallet".equals(selectedPaymentMethod)) {
                creditIdField.setVisible(false);
                creditCvvField.setVisible(false);
                walletIdField.setVisible(true);
            } else {
                creditIdField.setVisible(false);
                creditCvvField.setVisible(false);
                walletIdField.setVisible(false);
            }
        });
    }


}