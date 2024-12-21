package GUI;

import classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class UserGUI extends JPanel {
    private User user = new User();
    private BufferedImage backgroundImage;  // Field to hold background image

    public UserGUI() {
        // Load background image
        loadBackgroundImage("C:\\Users\\asus\\IdeaProjects\\thotho\\src\\b.jpg");  // Update with the correct path

        // Set layout to null for manual positioning of components
        setLayout(null);  // Remove any layout manager

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setBackground(new Color(255, 140, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(550, 400, 120, 40);  // Position at (550, 400)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email;
                String password;
                user.loadFromFile();
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

                if (user.login(email, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create Account button
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setPreferredSize(new Dimension(200, 40));
        createAccountButton.setBackground(new Color(255, 140, 0));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        createAccountButton.setBounds(750, 400, 150, 40);  // Position at (750, 400)
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField firstNameField = new JTextField();
                JTextField lastNameField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField passwordField = new JPasswordField();
                JTextField addressField = new JTextField();
                JTextField mobileNumberField = new JTextField();
                JTextField countryField = new JTextField();
                JTextField ageField = new JTextField();
                JTextField genderField = new JTextField();

                Object[] message = {
                        "First Name:", firstNameField,
                        "Last Name:", lastNameField,
                        "Email:", emailField,
                        "Password:", passwordField,
                        "Address:", addressField,
                        "Mobile number:", mobileNumberField,
                        "Country:", countryField,
                        "Age:", ageField,
                        "Gender:", genderField,
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Create Account", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String email = emailField.getText();
                        String password = passwordField.getText();
                        String address = addressField.getText();
                        String country = countryField.getText();
                        int mobileNumber = 0;
                        int age = 0;

                        try {
                            if (ageField.getText() == null || ageField.getText().trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Age field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            age = Integer.parseInt(ageField.getText().trim());
                            if (age <= 0) {
                                JOptionPane.showMessageDialog(null, "Age must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } catch (NumberFormatException ee) {
                            JOptionPane.showMessageDialog(null, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String gender = genderField.getText();
                        try {
                            mobileNumber = Integer.parseInt(mobileNumberField.getText());
                        } catch (NumberFormatException ee) {
                            JOptionPane.showMessageDialog(null, "Invalid input, please enter a valid mobile number.", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Exit if mobile number is invalid
                        }
                        String result = user.createAccount(firstName, lastName, email, password, address, mobileNumber, country, age, gender);
                        if(result.equalsIgnoreCase("Please enter a valid email address (Ex: johndoe@gmail.com)")) {
                            JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        else if(result.equalsIgnoreCase("Password must be at least 5 characters long")) {
                            JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        JOptionPane.showMessageDialog(null, result);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "An error occurred while creating the account.", "Error", JOptionPane.ERROR_MESSAGE);
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
        backButton.setBounds(60, 740, 120, 40);  // Position at (60, 740)
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and show the AdminOrUser frame again
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(UserGUI.this);
                frame.getContentPane().removeAll();
                frame.add(new AdminOrUser());
                frame.revalidate();
                frame.repaint();
            }
        });

        // Add buttons to the panel
        add(loginButton);
        add(createAccountButton);
        add(backButton);

        // Ensure the panel resizes properly and fits the parent window
        setPreferredSize(new Dimension(1000, 600)); // Ensure the panel fits the window size
    }

    // Method to load background image
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

    // Override paintComponent to display the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);  // Draw image to fill the panel
        }
    }
}
