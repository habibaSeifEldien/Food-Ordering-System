package GUI;

import classes.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class AdminGUI extends JPanel {
    private Admin admin = new Admin();
    private BufferedImage backgroundImage;  // Define background image field

    public AdminGUI() {
        // Set layout to null for manual positioning of components
        setLayout(null);  // Remove any layout manager

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setBackground(new Color(255, 140, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(550, 400, 120, 40);  // Position at (400, 100)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = "";
                String password = "";
                admin.loadFromFile();

                // Input dialog for email
                while (true) {
                    email = JOptionPane.showInputDialog("Enter Email:");
                    if (email == null) {
                        return;
                    }
                    if (email.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Email field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        break;
                    }
                }

                // Input dialog for password
                while (true) {
                    password = JOptionPane.showInputDialog("Enter Password:");
                    if (password == null) {
                        return;
                    }
                    if (password.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Password field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        break;
                    }
                }

                if (admin.login(email, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Replace the current AdminGUI with RestaurantGUI
                    switchToRestaurantGUI();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create Account button
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setPreferredSize(new Dimension(120, 40));
        createAccountButton.setBackground(new Color(255, 140, 0));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        createAccountButton.setBounds(750, 400, 150, 40);  // Position at (400, 200)
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField firstNameField = new JTextField();
                JTextField lastNameField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField passwordField = new JPasswordField();
                JTextField adminIDField = new JTextField();

                Object[] message = {
                        "First Name:", firstNameField,
                        "Last Name:", lastNameField,
                        "Email:", emailField,
                        "Password:", passwordField,
                        "Admin ID:", adminIDField
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Create Account", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String email = emailField.getText();
                        String password = passwordField.getText();
                        int adminID = Integer.parseInt(adminIDField.getText());

                        // Assuming admin.createAccount() returns a result message
                        String result = admin.createAccount(firstName, lastName, email, password, adminID);
                        if(result.equalsIgnoreCase("Please enter a valid email address (Ex: johndoe@gmail.com)")) {
                            JOptionPane.showMessageDialog(AdminGUI.this, result, "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Prevent further actions if email is invalid
                        } else if(result.equalsIgnoreCase("Password must be at least 5 characters long")) {
                            JOptionPane.showMessageDialog(AdminGUI.this, result, "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Prevent further actions if password is invalid
                        }
                        JOptionPane.showMessageDialog(AdminGUI.this, result);
                        // After account creation, switch to Restaurant GUI
                        switchToRestaurantGUI();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AdminGUI.this, "Invalid Admin ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Create Back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setBackground(new Color(255, 140, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBounds(60, 740, 120, 40);  // Position at (400, 300)
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and show the AdminOrUser frame again
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(AdminGUI.this);
                frame.getContentPane().removeAll();
                frame.add(new AdminOrUser());
                frame.revalidate();
                frame.repaint();
            }
        });

        // Add buttons to the Admin panel
        add(loginButton);
        add(createAccountButton);
        add(backButton);

        // Ensure the panel resizes properly and fills the parent window
        setPreferredSize(new Dimension(1000, 800)); // Make sure the panel fits the window size
    }

    // Switch to Restaurant GUI
    private void switchToRestaurantGUI() {
        // Create and set up the Restaurant GUI
        RestaurantGUI restaurantGUI = new RestaurantGUI();
        // Get the JFrame container
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        // Remove all components of AdminGUI
        frame.dispose();
        frame.getContentPane().removeAll();
        // Add the Restaurant GUI panel
        frame.add(restaurantGUI);
        // Maximize window and refresh the UI
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private void loadBackgroundImage(String path) {
        try {
            backgroundImage = ImageIO.read(new File(path));  // Attempt to load the image
            if (backgroundImage == null) {
                System.out.println("Image not found or not valid.");
            } else {
                System.out.println("Image loaded successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading background image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Ensure the panel is rendered properly
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);  // Draw the background image
        }
    }
}
