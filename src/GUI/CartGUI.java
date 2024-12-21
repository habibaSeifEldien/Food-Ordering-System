package GUI;

import classes.Cart;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

class CartGUI {
    // Cart items with their quantities
    private static final HashMap<String, Integer> itemQuantities = new HashMap<>();
    private static double deliveryFee = 18.99;
    private static double serviceFee = 3.99;
    private static final Cart currentCart = new Cart();


    public static void main(String[] args) {
        SwingUtilities.invokeLater(CartGUI::CartSection);
    }

    public static void CartSection() {
        // Main frame
        JFrame frame = new JFrame("Cart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 900); // Adjusted height
        frame.setLayout(null);

        // Header
        JLabel header = new JLabel("Cart", SwingConstants.CENTER);
        header.setBounds(600, 10, 400, 30);
        header.setFont(new Font("MV BOLI", Font.BOLD, 40));
        header.setForeground(Color.BLACK);

        // Cart items list
        JTextArea cartItems = new JTextArea();
        cartItems.setText("Your cart is empty."); // Initially empty cart
        cartItems.setEditable(false);
        cartItems.setBackground(new Color(250, 250, 250));
        cartItems.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(cartItems);
        scrollPane.setBounds(600, 50, 360, 100); // Reduced height

        // Add item section
        JLabel AddItem = new JLabel("Add Item:"); // Add item Label
        AddItem.setBounds(600, 170, 100, 20);
        AddItem.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        JTextField AddField = createPlaceholderField("Item Name"); // Add item Field
        AddField.setBounds(600, 200, 200, 30);

        // Quantity field
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(810, 200, 70, 30);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // Quantity spinner
        quantitySpinner.setBounds(880, 200, 50, 30);

        JButton AddBtn = new JButton("Add"); // Add Button
        AddBtn.setBounds(940, 200, 60, 30);
        AddBtn.setBackground(new Color(0, 200, 0));
        AddBtn.setForeground(Color.WHITE);

        // Remove item section
        JLabel RemoveItem = new JLabel("Remove Item:"); //Remove item Label
        RemoveItem.setBounds(600, 240, 100, 20);
        RemoveItem.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        JTextField RemoveField = createPlaceholderField("Item Name to Remove"); //Remove item Field
        RemoveField.setBounds(600, 270, 200, 30);

        // Quantity for removing
        JLabel removeQuantityLabel = new JLabel("Quantity:");
        removeQuantityLabel.setBounds(810, 270, 70, 30);
        JSpinner removeQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // Remove quantity spinner
        removeQuantitySpinner.setBounds(880, 270, 50, 30);

        JButton removeItemButton = new JButton("Remove"); //Remove Button
        removeItemButton.setBounds(940, 270, 60, 30);
        removeItemButton.setBackground(new Color(200, 0, 0));
        removeItemButton.setForeground(Color.WHITE);

        // Special Request Section
        JLabel specialRequestLabel = new JLabel("Special Request:");
        specialRequestLabel.setBounds(600, 310, 200, 20);
        specialRequestLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        JTextField specialRequestField = createPlaceholderField("Any special requests?");
        specialRequestField.setBounds(600, 340, 260, 30);

        JButton specialRequestButton = new JButton("Submit");
        specialRequestButton.setBounds(880, 340, 100, 30);
        specialRequestButton.setFocusable(false);
        specialRequestButton.setBackground(new Color(128, 0, 128));
        specialRequestButton.setForeground(Color.WHITE);
        specialRequestButton.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        //specialRequestButton functionality
        specialRequestButton.addActionListener(e -> {
            String notes = specialRequestField.getText().trim();
            currentCart.otherNotes(notes); // Call the otherNotes method
        });

        // Save on Your Order Section
        JLabel SaveOnOrderLabel = new JLabel("Save on Your Order:");
        SaveOnOrderLabel.setBounds(600, 380, 200, 20);
        SaveOnOrderLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        JTextField voucherCodeField = createPlaceholderField("Enter Voucher Code");
        voucherCodeField.setBounds(600, 410, 250, 30);

        JButton SubmitVoucherButton = new JButton("Submit");
        SubmitVoucherButton.setBounds(875, 411, 100, 30);
        SubmitVoucherButton.setBackground(new Color(0, 128, 255));
        SubmitVoucherButton.setForeground(Color.WHITE);

        // Payment summary
        JLabel paymentSummaryLabel = new JLabel("Payment Summary:");
        paymentSummaryLabel.setBounds(600, 460, 200, 20);
        paymentSummaryLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

        JTextArea paymentSummary = new JTextArea();
        paymentSummary.setText(generatePaymentSummary());
        paymentSummary.setEditable(false);
        paymentSummary.setBounds(600, 490, 360, 200); // Increased height
        paymentSummary.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        paymentSummary.setBackground(new Color(245, 245, 245));

        // Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(600, 700, 360, 40);
        checkoutButton.setBackground(new Color(255, 128, 0));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font(Font.DIALOG, Font.BOLD, 16));

        // Add button functionality
        AddBtn.addActionListener(e -> {

            String itemName = AddField.getText().trim();
            int quantity = (int) quantitySpinner.getValue();

            // Call addItem method from cart class
            currentCart.addItem(itemName, quantity);

            // Update itemQuantities map and cart display
            itemQuantities.put(itemName, itemQuantities.getOrDefault(itemName, 0) + quantity);
            cartItems.setText(generateCartText());  // Display updated cart items
            paymentSummary.setText(generatePaymentSummary());  // Update payment summary

            JOptionPane.showMessageDialog(frame, "Item added successfully!");

            AddField.setText("Item Name");  // Reset input field
            quantitySpinner.setValue(1);    // Reset quantity spinner
        });

        // Remove button functionality
        removeItemButton.addActionListener(e -> {
            String itemName = RemoveField.getText().trim();
            int quantityToRemove = (int) removeQuantitySpinner.getValue();

            if (itemName.isEmpty() || itemName.equals("Item Name to Remove") || quantityToRemove <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid item name and quantity.");
                return;
            }

            // Check if the item exists in the cart
            int currentQuantity = itemQuantities.getOrDefault(itemName, 0);
            if (currentQuantity <= 0) {
                JOptionPane.showMessageDialog(frame, "Item not found in the cart. Add it first.");
                return;
            }

            // Call removeItem method from cart class
            int quantityRemoved = Math.min(currentQuantity, quantityToRemove);
            currentCart.removeItem(itemName, quantityRemoved);

            // Update itemQuantities map and cart display
            if (quantityRemoved >= currentQuantity) {
                itemQuantities.remove(itemName);  // Remove item completely if quantity reaches 0
            } else {
                itemQuantities.put(itemName, currentQuantity - quantityRemoved);
            }

            cartItems.setText(generateCartText());  // Display updated cart items
            paymentSummary.setText(generatePaymentSummary());  // Update payment summary

            JOptionPane.showMessageDialog(frame, "Item removed successfully!");

            RemoveField.setText("Item Name to Remove");  // Reset input field
            removeQuantitySpinner.setValue(1);          // Reset quantity spinner
        });


        // Submit voucher functionality
        SubmitVoucherButton.addActionListener(e -> {
            String promoCode = voucherCodeField.getText().trim();

            if (promoCode.isEmpty() || promoCode.equals("Enter Voucher Code")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid voucher code.");
                return;
            }

            // Create an instance of cart and calculate the total before discount
            currentCart.total = calculateDiscounted();  // Set the current total before applying discount

            // Calculate the discounted total using the promo code
            double discountedTotal = currentCart.calcDiscount(promoCode);


            if (discountedTotal != currentCart.total){

                // Calculate the final total after applying the discount and adding fees
                double newTotal = discountedTotal + deliveryFee + serviceFee;

                // Update the payment summary
                paymentSummary.setText("  Subtotal (after discount): EGP " + String.format("%.2f", discountedTotal) + "\n"
                        + "  Delivery fee:              EGP " + String.format("%.2f", deliveryFee) + "\n"
                        + "  Service fee:               EGP " + String.format("%.2f", serviceFee) + "\n"
                        + "  ------------------------\n"
                        + "  Total Amount:              EGP " + String.format("%.2f", newTotal) + "\n");

                // Update the checkout button text
                checkoutButton.setText("Checkout  EGP " + String.format("%.3f", newTotal));
            }
        });

        // Add components to frame
        frame.add(header);
        frame.add(scrollPane);
        frame.add(AddItem);
        frame.add(AddField);
        frame.add(quantityLabel);
        frame.add(quantitySpinner);
        frame.add(AddBtn);
        frame.add(RemoveItem);
        frame.add(RemoveField);
        frame.add(removeQuantityLabel);
        frame.add(removeQuantitySpinner);
        frame.add(removeItemButton);
        frame.add(specialRequestLabel);
        frame.add(specialRequestField);
        frame.add(specialRequestButton);
        frame.add(SaveOnOrderLabel);
        frame.add(voucherCodeField);
        frame.add(SubmitVoucherButton);
        frame.add(paymentSummaryLabel);
        frame.add(paymentSummary);
        frame.add(checkoutButton);

        // Show frame
        frame.setVisible(true);
    }

    private static JTextField createPlaceholderField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Arial", Font.ITALIC, 14));
        field.setForeground(Color.GRAY);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setFont(new Font("Arial", Font.PLAIN, 14));
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setFont(new Font("Arial", Font.ITALIC, 14));
                    field.setForeground(Color.GRAY);
                }
            }
        });
        return field;
    }

    private static String generateCartText() {
        if (itemQuantities.isEmpty()) {
            return "Your cart is empty.";
        }

        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
            text.append(entry.getKey()).append(" x ").append(entry.getValue()).append("\n");
        }
        return text.toString();
    }

    private static String generatePaymentSummary() {
        double subtotal = calculateDiscounted(); // This calculates the subtotal before fees
        double total = subtotal;  // Initialize total as subtotal

        // If the cart is empty (no items), set delivery and service fees to 0
        double deliveryFeeToApply = subtotal > 0 ? deliveryFee : 0;
        double serviceFeeToApply = subtotal > 0 ? serviceFee : 0;

        // Add delivery and service fees if items are in the cart
        total += deliveryFeeToApply + serviceFeeToApply;

        return "  Subtotal:                 EGP " + String.format("%.2f", subtotal) + "\n"
                + "  Delivery fee:             EGP " + String.format("%.2f", deliveryFeeToApply) + "\n"
                + "  Service fee:              EGP " + String.format("%.2f", serviceFeeToApply) + "\n"
                + "  ------------------------\n"
                + "  Total Amount:             EGP " + String.format("%.2f", total) + "\n";
    }

    private static double calculateDiscounted() {
        // If there are no items in the cart, return 0
        if (itemQuantities.isEmpty()) {
            return 0;
        }
        double subtotal = 0;
        for (String itemName : itemQuantities.keySet()) {
            // Add the item cost to the subtotal
            subtotal += itemQuantities.get(itemName);
        }
        return subtotal;
    }

}
