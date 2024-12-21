package GUI;
import classes.Restaurant;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileNotFoundException;

public class UserEntering {
    private Restaurant restaurant=new Restaurant();

    public UserEntering() {
        Restaurant.filee.loadFromFile();
        this.restaurant=new Restaurant();

    }
    public void displayUI() {
        JFrame frame = new JFrame("Food Ordering Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton btnRestaurantName = createButton("Enter Restaurant Name");
        JButton btnRestaurantArea = createButton("Enter Restaurant Area");
        JButton btnRestaurantGov = createButton("Enter Restaurant Governate");

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 18));
        outputArea.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(btnRestaurantName, gbc);
        gbc.gridy = 1;
        buttonPanel.add(btnRestaurantArea, gbc);
        gbc.gridy = 2;
        buttonPanel.add(btnRestaurantGov, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(buttonPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        frame.add(scrollPane, gbc);

        btnRestaurantName.addActionListener(e -> handleRestaurantName(frame, outputArea));
        btnRestaurantArea.addActionListener(e -> handleRestaurantArea(frame, outputArea));
        btnRestaurantGov.addActionListener(e -> handleRestaurantGovernate(frame, outputArea));

        frame.setVisible(true);
    }

    private void handleRestaurantName(JFrame frame, JTextArea outputArea) {
        String name = JOptionPane.showInputDialog(frame, "Enter restaurant name: ");
        if (name != null) {
            String result = restaurant.displayMenu(name);
            Restaurant restaurant1=restaurant.Searchforres(name);
            outputArea.setText(result);

            JTextField dishNameField = new JTextField();
            dishNameField.setPreferredSize(new Dimension(300, 30));

            JButton selectDishButton = createButton("Select Dish By Name");
            selectDishButton.addActionListener(e -> {
                String dishName = dishNameField.getText().trim();
                if (dishName != null && !dishName.isEmpty()) {
                    try {
                        restaurant.getmenu().selectDishByName(restaurant1,dishName);
                        //  System.out.println(restaurant.getName().length());
                        outputArea.append("\nDish selected: " + dishName);
                        JOptionPane.showMessageDialog(frame, "You have selected: " + dishName);
                        dishNameField.setText("");
                    } catch (FileNotFoundException ex) {
                        outputArea.append("\nError: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a dish name.");
                }
            });

            JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            selectionPanel.setBackground(Color.WHITE);
            selectionPanel.add(dishNameField);
            selectionPanel.add(selectDishButton);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            frame.add(selectionPanel, gbc);

            frame.revalidate();
            frame.repaint();
        }
    }

    private void handleRestaurantArea(JFrame frame, JTextArea outputArea) {
        String area = JOptionPane.showInputDialog(frame, "Enter restaurant area: ");
        if (area != null) {
            outputArea.setText("Restaurants In " + area + ":\n" + restaurant.displayRestaurantsByArea(area));
        }
    }

    private void handleRestaurantGovernate(JFrame frame, JTextArea outputArea) {
        String governate = JOptionPane.showInputDialog(frame, "Enter restaurant governate: ");
        if (governate != null) {
            outputArea.setText("Restaurants In " + governate + ":\n" + restaurant.displayRestaurantsByGov(governate));
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(239, 76, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(227, 75, 21), 3, true));
        button.setPreferredSize(new Dimension(300, 60));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(242, 130, 0));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(239, 76, 18));
            }
        });
        return button;
    }
}

