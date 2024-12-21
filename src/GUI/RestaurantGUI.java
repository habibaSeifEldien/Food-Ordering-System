package GUI;

import Exceptions.Dishfound;
import Exceptions.NOTFOUND;
import Exceptions.NonNumericInputException;
import classes.Admin;
import classes.Restaurant;
import classes.dish;
import txt.handles;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RestaurantGUI extends JPanel {

    private static handles fileHandler = new handles();  // Assuming Handles is your file handler class
    private static JFrame frame;
    private static JPanel panelContainer;
    private static CardLayout cardLayout;
    private static Menu menu = new Menu();  // Assuming Menu is your menu class
    private static Admin admin = new Admin();  // Assuming Admin is your admin class

    // Constructor for RestaurantGUI
    public RestaurantGUI() {
        // Load data from files
        fileHandler.loadFromFile();
        admin.loadFromFile();

        // Initialize the GUI components
        setLayout(new BorderLayout());  // Set a layout manager (BorderLayout in this case)
        initializeGUI();
    }

    public void initializeGUI() {
        // Create JFrame
        frame = new JFrame("Restaurant Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);  // Initial size, but will be overridden by maximization
        frame.getContentPane().setBackground(Color.orange);
        frame.setResizable(true);  // Make the frame resizable (can be useful for user interaction)

        // Maximize the frame to take the full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // This maximizes the window on startup
        frame.setUndecorated(false);  // Optional: This removes the window borders and title bar (set to true to remove)

        // Set up CardLayout and panels
        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        // Add panels for different views
        panelContainer.add(createMainPanel(), "Home");
        panelContainer.add(createAddRestaurantPanel(), "Add Restaurant");
        panelContainer.add(createDisplayDishesPanel(), "Display Dishes");
        panelContainer.add(createRemoveRestaurantPanel(), "Remove Restaurant");
        panelContainer.add(createManageDishesPanel(), "Manage Dishes");
        panelContainer.add(createSettingsPanel(), "Settings");

        panelContainer.add(createAddDishPanel(), "Add Dish");
        panelContainer.add(createRemoveDishPanel(), "Remove Dish");
        panelContainer.add(createUpdateDishPanel(), "Update Dish");

        // Add panel container to frame
        frame.add(panelContainer, BorderLayout.CENTER);
        cardLayout.show(panelContainer, "Home");  // Show the "Home" panel initially

        // Make frame visible
        frame.setVisible(true);
    }



    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        JLabel title = new JLabel("Dish & Restaurant Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(227, 75, 21)); // Red-orange color
        mainPanel.add(title, BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton addRestaurantButton = createButton("Add Restaurant");
        JButton removeRestaurantButton = createButton("Remove Restaurant");
        JButton displayAllDishesButton = createButton("Display All Dishes");
        JButton manageDishesButton = createButton("Manage Dishes");
        JButton settingsButton = createButton("Settings");

        buttonsPanel.add(addRestaurantButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(removeRestaurantButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(displayAllDishesButton, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10); // Add larger spacing
        buttonsPanel.add(manageDishesButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(settingsButton, gbc);

        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        manageDishesButton.addActionListener(e -> cardLayout.show(panelContainer, "Manage Dishes"));

        addRestaurantButton.addActionListener(e -> cardLayout.show(panelContainer, "Add Restaurant"));
        removeRestaurantButton.addActionListener(e -> cardLayout.show(panelContainer, "Remove Restaurant"));
        displayAllDishesButton.addActionListener(e -> cardLayout.show(panelContainer, "Display Dishes"));
        settingsButton.addActionListener(e -> cardLayout.show(panelContainer, "Settings"));

        return mainPanel;
    }

    private JPanel createAddRestaurantPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        panel.add(nameLabel, gbc);
        gbc.gridx++;
        JTextField nameField = new JTextField(20);
        nameField.setFont(fieldFont);
        panel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(labelFont);
        panel.add(categoryLabel, gbc);
        gbc.gridx++;
        JTextField categoryField = new JTextField(20);
        categoryField.setFont(fieldFont);
        panel.add(categoryField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel governmentLabel = new JLabel("Government:");
        governmentLabel.setFont(labelFont);
        panel.add(governmentLabel, gbc);
        gbc.gridx++;
        JTextField governmentField = new JTextField(20);
        governmentField.setFont(fieldFont);
        panel.add(governmentField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel areaLabel = new JLabel("Area:");
        areaLabel.setFont(labelFont);
        panel.add(areaLabel, gbc);
        gbc.gridx++;
        JTextField areaField = new JTextField(20);
        areaField.setFont(fieldFont);
        panel.add(areaField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel feeLabel = new JLabel("Delivery Fee:");
        feeLabel.setFont(labelFont);
        panel.add(feeLabel, gbc);
        gbc.gridx++;
        JTextField feeField = new JTextField(20);
        feeField.setFont(fieldFont);
        panel.add(feeField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Restaurant");
        styleButton(addButton);
        addButton.setFont(new Font("Arial", Font.BOLD, 14)); // تكبير زر الإضافة
        addButton.addActionListener(e -> addRestaurant(nameField, categoryField, governmentField, areaField, feeField));
        panel.add(addButton, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel dishNameLabel = new JLabel("Dish Name:");
        dishNameLabel.setFont(labelFont);
        panel.add(dishNameLabel, gbc);
        gbc.gridx++;
        JTextField dishNameField = new JTextField(20);
        dishNameField.setFont(fieldFont);
        panel.add(dishNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel descriptionLabel = new JLabel("Dish Description:");
        descriptionLabel.setFont(labelFont);
        panel.add(descriptionLabel, gbc);
        gbc.gridx++;
        JTextField descriptionField = new JTextField(20);
        descriptionField.setFont(fieldFont);
        panel.add(descriptionField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel priceLabel = new JLabel("Dish Price:");
        priceLabel.setFont(labelFont);
        panel.add(priceLabel, gbc);
        gbc.gridx++;
        JTextField priceField = new JTextField(20);
        priceField.setFont(fieldFont);
        panel.add(priceField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel typeLabel = new JLabel("Dish Type:");
        typeLabel.setFont(labelFont);
        panel.add(typeLabel, gbc);
        gbc.gridx++;
        JTextField typeField = new JTextField(20);
        typeField.setFont(fieldFont);
        panel.add(typeField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel spicyLabel = new JLabel("Dish Spicy Level:");
        spicyLabel.setFont(labelFont);
        panel.add(spicyLabel, gbc);
        gbc.gridx++;
        JTextField spicyField = new JTextField(20);
        spicyField.setFont(fieldFont);
        panel.add(spicyField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton addDishButton = new JButton("Add Dish");
        styleButton(addDishButton);
        addDishButton.setFont(new Font("Arial", Font.BOLD, 14)); // تكبير زر الإضافة
        addDishButton.addActionListener(e -> addDish(dishNameField, descriptionField, priceField, typeField, spicyField));
        panel.add(addDishButton, gbc);
        gbc.gridy++;
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // تكبير زر العودة
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Home"));
        panel.add(backButton, gbc);
        return panel;
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.ORANGE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 25));
        button.setFocusPainted(false);
    }

    private void addRestaurant(JTextField nameField, JTextField categoryField, JTextField governmentField, JTextField areaField, JTextField feeField) {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String government = governmentField.getText().trim();
        String area = areaField.getText().trim();
        String feeText = feeField.getText().trim();

        if (name.isEmpty() || category.isEmpty() || government.isEmpty() || area.isEmpty() || feeText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            float fee = Float.parseFloat(feeText);
            Restaurant restaurant = new Restaurant(name, category, government, area, fee);
            fileHandler.restaurants.add(restaurant);
            fileHandler.saveToFile();
            JOptionPane.showMessageDialog(frame, "Restaurant added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(panelContainer, "Home");
            updateDishesDisplay();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid delivery fee!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDish(JTextField dishNameField, JTextField descriptionField, JTextField priceField, JTextField typeField, JTextField spicyField) {
        String dishName = dishNameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceText = priceField.getText().trim();
        String type = typeField.getText().trim();
        String spicy = spicyField.getText().trim();

        if (dishName.isEmpty() || description.isEmpty() || priceText.isEmpty() || type.isEmpty() || spicy.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all dish fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int price = Integer.parseInt(priceText);
            if (fileHandler.restaurants.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please add a restaurant first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Restaurant lastRestaurant = fileHandler.restaurants.get(fileHandler.restaurants.size() - 1);
            lastRestaurant.getmenu().addDishtofoundRes(dishName, description, price, type, spicy);
            fileHandler.saveToFile();
            JOptionPane.showMessageDialog(frame, "Dish added successfully to " + lastRestaurant.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid price format!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createRemoveRestaurantPanel() {
        fileHandler.loadFromFile();
        fileHandler.saveToFile();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel removeLabel = new JLabel("Enter Restaurant Name to Remove:");
        removeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        removeLabel.setForeground(Color.ORANGE);
        removeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(removeLabel, gbc);
        gbc.gridy++;
        JTextField removeNameField = new JTextField(20);
        removeNameField.setPreferredSize(new Dimension(300, 50));
        panel.add(removeNameField, gbc);
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton removeButton = new JButton("Remove Restaurant");
        styleButton(removeButton);
        removeButton.setPreferredSize(new Dimension(200, 50));
        removeButton.addActionListener(e -> removeRestaurant(removeNameField));
        buttonPanel.add(removeButton);
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.setPreferredSize(new Dimension(200, 50));  // عرض 200px وارتفاع 50px
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Home"));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);
        return panel;
    }

    private void removeRestaurant(JTextField removeNameField) {
        String restaurantName = removeNameField.getText().trim();
        if (restaurantName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid restaurant name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Restaurant restaurantToRemove = null;
        for (Restaurant restaurant : fileHandler.restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                restaurantToRemove = restaurant;
                break;
            }
        }
        if (restaurantToRemove != null) {
            fileHandler.restaurants.remove(restaurantToRemove);
            fileHandler.saveToFile();
            fileHandler.loadFromFile();
            JOptionPane.showMessageDialog(frame, "Restaurant removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(panelContainer, "Home");
            updateDishesDisplay();
        } else {
            JOptionPane.showMessageDialog(frame, "Restaurant not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDishesDisplay() {
        JPanel displayPanel = createDisplayDishesPanel();
        panelContainer.add(displayPanel, "Display Dishes");
        cardLayout.show(panelContainer, "Display Dishes");
    }

    private JPanel createDisplayDishesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        for (Restaurant restaurant : fileHandler.restaurants) {
            textArea.append(restaurant.displayAllDishes());
            textArea.append("\n");
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(480, 200));
        panel.add(scrollPane);
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Home"));
        panel.add(backButton);
        return panel;
    }

    //==============================================================================
    private static JPanel createManageDishesPanel() {
        JPanel manageDishesPanel = new JPanel(new BorderLayout(50, 50));
        manageDishesPanel.setBorder(BorderFactory.createLineBorder(new Color(227, 75, 21)));  // Red border

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridy++;
        JButton addDishButton = createButton("Add Dish");
        JButton removeDishButton = createButton("Remove Dish");
        JButton updateDishButton = createButton("Update Dish");

        gbc.gridy++;
        buttonsPanel.add(addDishButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(removeDishButton, gbc);
        gbc.gridy++;
        buttonsPanel.add(updateDishButton, gbc);

        manageDishesPanel.add(buttonsPanel, BorderLayout.CENTER);

        addDishButton.addActionListener(e -> cardLayout.show(panelContainer, "Add Dish"));
        updateDishButton.addActionListener(e -> cardLayout.show(panelContainer, "Update Dish"));
        removeDishButton.addActionListener(e -> cardLayout.show(panelContainer, "Remove Dish"));

        JButton backButton = createBackButton();
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Home"));
        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(backButton, BorderLayout.WEST);
        manageDishesPanel.add(backPanel, BorderLayout.SOUTH);

        return manageDishesPanel;
    }

    private static JPanel createAddDishPanel() {
        JPanel addDishPanel = new JPanel(new BorderLayout(80, 80));
        addDishPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JComboBox<String> restaurantDropdown = new JComboBox<>();
        for (Restaurant restaurant : fileHandler.restaurants) {
            restaurantDropdown.addItem(restaurant.getName());
        }
        // Restaurant Selection Panel
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel restaurantLabel = new JLabel("Select Restaurant:");
        JButton selectRestaurantButton = createButton("Select Restaurant");

        // Check if the restaurantDropdown has items
        if (restaurantDropdown.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "No restaurants available. Please add restaurants first.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        topPanel.add(restaurantLabel);
        topPanel.add(restaurantDropdown);
        topPanel.add(selectRestaurantButton);
        addDishPanel.add(topPanel, BorderLayout.NORTH);

        // Dish Details Form (Initially Hidden)
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField spiceField = new JTextField();
        JButton addButton = createButton("Add Dish");

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Spicy level:"));
        formPanel.add(spiceField);
        formPanel.add(addButton);

        formPanel.setVisible(false);
        addDishPanel.add(formPanel, BorderLayout.CENTER);

        selectRestaurantButton.addActionListener(e -> {
            String selectedRestaurant = (String) restaurantDropdown.getSelectedItem();
            Restaurant selected = findRestaurantByName(selectedRestaurant);
            if (selected != null) {
                formPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a valid restaurant.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addButton.addActionListener(e -> {

                String name = nameField.getText();
                String description = descriptionField.getText();
                int price = Integer.parseInt(priceField.getText());
                String type = typeField.getText();
                String spice = spiceField.getText();
                String selectedRestaurant = (String) restaurantDropdown.getSelectedItem();
                Restaurant selected = findRestaurantByName(selectedRestaurant);

                if (selected != null) {
                    try {
                        selected.getmenu().addDishtofoundRes(name, description, price, type, spice);
                        JOptionPane.showMessageDialog(frame, "Dish added successfully!");

                    } catch (Dishfound ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (NonNumericInputException ex) {
                        throw new RuntimeException(ex);
                    }
                    fileHandler.saveToFile();
                }

        });

        JButton backButton = createBackButton();
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Manage Dishes"));
        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(backButton, BorderLayout.WEST);
        addDishPanel.add(backPanel, BorderLayout.SOUTH);

        return addDishPanel;
    }

    private static JPanel createRemoveDishPanel() {
        JPanel removeDishPanel = new JPanel(new BorderLayout(100, 100));
        removeDishPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        JComboBox<String> restaurantDropdown = new JComboBox<>();
        for (Restaurant restaurant : fileHandler.restaurants) {
            restaurantDropdown.addItem(restaurant.getName());
        }
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel restaurantLabel = new JLabel("Select Restaurant:");
        JButton selectRestaurantButton = createButton("Select Restaurant");

        // Check if the restaurantDropdown has items
        if (restaurantDropdown.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "No restaurants available. Please add restaurants first.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        topPanel.add(restaurantLabel);
        topPanel.add(restaurantDropdown);
        topPanel.add(selectRestaurantButton);
        removeDishPanel.add(topPanel, BorderLayout.NORTH);

        JTextField nameField = new JTextField();
        JButton removeButton = createButton("Remove Dish");
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        formPanel.add(new JLabel(" Name: "));
        formPanel.add(nameField);
        formPanel.add(removeButton);
        removeDishPanel.add(formPanel, BorderLayout.CENTER);

        selectRestaurantButton.addActionListener(e -> {
            String selectedRestaurant = (String) restaurantDropdown.getSelectedItem();
            Restaurant selected = findRestaurantByName(selectedRestaurant);
            if (selected != null) {
                formPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a valid restaurant.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeButton.addActionListener(e -> {
            String name = nameField.getText();
            Restaurant selectedRestaurant = findRestaurantByName((String) restaurantDropdown.getSelectedItem());
            if (selectedRestaurant != null) {
                boolean removed = false;
                try {
                    removed = selectedRestaurant.getmenu().removeDish(name);
                } catch (NOTFOUND ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                fileHandler.saveToFile();

                if (removed) {
                    JOptionPane.showMessageDialog(frame, "Dish removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Dish not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = createBackButton();
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Manage Dishes"));
        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(backButton, BorderLayout.WEST);
        removeDishPanel.add(backPanel, BorderLayout.SOUTH);

        return removeDishPanel;
    }


    private JPanel createUpdateDishPanel() {
        JPanel updateDishPanel = new JPanel(new BorderLayout(80, 80));  // Adjusted spacing
        updateDishPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        JComboBox<String> restaurantDropdown = new JComboBox<>();
        for (Restaurant restaurant : fileHandler.restaurants) {
            restaurantDropdown.addItem(restaurant.getName());
        }
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));  // Adjusted grid layout
        JLabel restaurantLabel = new JLabel("Select Restaurant:");
        JButton selectRestaurantButton = createButton("Select Restaurant");
        topPanel.add(restaurantLabel);
        topPanel.add(restaurantDropdown);
        topPanel.add(selectRestaurantButton);
        updateDishPanel.add(topPanel, BorderLayout.NORTH);

        JTextField nameField = new JTextField();
        JButton updatePriceButton = createButton("Update Price");
        JButton updateDescriptionButton = createButton("Update Description");
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 15, 15));  // Adjusted spacing
        formPanel.add(new JLabel(" Name: "));
        formPanel.add(nameField);
        formPanel.add(updatePriceButton);
        formPanel.add(updateDescriptionButton);
        updateDishPanel.add(formPanel, BorderLayout.CENTER);

        selectRestaurantButton.addActionListener(e -> {
            String selectedRestaurant = (String) restaurantDropdown.getSelectedItem();
            Restaurant selected = findRestaurantByName(selectedRestaurant);
            if (selected != null) {
                formPanel.setVisible(true);  // Show the form only after selecting a restaurant
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a valid restaurant.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updatePriceButton.addActionListener(e -> {
            String name = nameField.getText();
            String newPrice = JOptionPane.showInputDialog("Enter new price:");
            try {
                int price = Integer.parseInt(newPrice);
                Restaurant selectedRestaurant = findRestaurantByName((String) restaurantDropdown.getSelectedItem());
                if (selectedRestaurant != null) {
                    for (dish d : selectedRestaurant.getmenu().getDishes()) {
                        if (d.getName().equalsIgnoreCase(name)) {
                            d.setPrice(price);
                            fileHandler.saveToFile();
                            JOptionPane.showMessageDialog(frame, "Price updated successfully!");
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Dish not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateDescriptionButton.addActionListener(e -> {
            String name = nameField.getText();
            String newDescription = JOptionPane.showInputDialog("Enter new description:");
            Restaurant selectedRestaurant = findRestaurantByName((String) restaurantDropdown.getSelectedItem());
            if (selectedRestaurant != null) {
                for (dish d : selectedRestaurant.getmenu().getDishes()) {
                    if (d.getName().equalsIgnoreCase(name)) {
                        d.setDescription(newDescription);
                        fileHandler.saveToFile();
                        JOptionPane.showMessageDialog(frame, "Description updated successfully!");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Dish not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = createBackButton();
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Manage Dishes"));
        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(backButton, BorderLayout.WEST);
        updateDishPanel.add(backPanel, BorderLayout.SOUTH);

        return updateDishPanel;
    }

    private static Restaurant findRestaurantByName(String name) {
        for (Restaurant restaurant : fileHandler.restaurants) {
            if (restaurant.getName().equalsIgnoreCase(name)) {
                return restaurant;
            }
        }
        return null;
    }

    private static JPanel createSettingsPanel() {
        JFrame frame = null;
        JPanel settingsPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Settings Panel", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        settingsPanel.add(label, BorderLayout.CENTER);

        JButton changeUserNameButton = createButton("Change Username");
        JButton changePasswordButton = createButton("Change Password");
        JButton displayInfoButton = createButton("Display Info");

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;

        buttonsPanel.add(changeUserNameButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(changePasswordButton, gbc);

        gbc.gridy++;
        buttonsPanel.add(displayInfoButton, gbc);

        settingsPanel.add(buttonsPanel, BorderLayout.CENTER);

        changeUserNameButton.addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog("Enter new first name:");
            String lastName = JOptionPane.showInputDialog("Enter new last name:");
            //  admin.changeUserName(firstName, lastName);
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.saveToFile();
            JOptionPane.showMessageDialog(frame, "Username updated successfully!");
        });

        changePasswordButton.addActionListener(e -> {
            String newPassword = JOptionPane.showInputDialog("Enter new password:");
            admin.setPassword(newPassword);
            admin.saveToFile();
            JOptionPane.showMessageDialog(frame, "Password updated successfully!");
        });

        displayInfoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, admin.toString());
        });

        JButton backButton = createBackButton();
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Home"));
        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(backButton, BorderLayout.WEST);
        settingsPanel.add(backPanel, BorderLayout.SOUTH);

        // Using cardLayout to navigate to the main panel
        backButton.addActionListener(e -> cardLayout.show(panelContainer, "Main Panel"));

        return settingsPanel;
    }

    private static JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(239, 76, 18));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setFocusPainted(false);

        return backButton;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 24));

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(239, 76, 18));
        button.setFocusPainted(false);

        button.setBorder(BorderFactory.createLineBorder(new Color(227, 75, 21), 3, true));

        button.setPreferredSize(new Dimension(300, 70));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(242, 130, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(239, 76, 18));
            }
        });

        return button;
    }


}