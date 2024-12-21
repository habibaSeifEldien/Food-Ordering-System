package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class AdminOrUser extends JFrame implements ActionListener {
    JButton Adminbtn, Userbtn;
    JLabel header, msg;
    private BufferedImage backgroundImage;

    AdminOrUser() {
        loadBackgroundImage("C:\\Users\\asus\\IdeaProjects\\thotho\\src\\admin_user-01.png");  // Change to the correct path of your image

        header = new JLabel("Tasty Trail");
        header.setBounds(460, 5, 600, 200);  // Position at the top
        header.setHorizontalAlignment(JLabel.CENTER);  // Center the text
        header.setFont(new Font("MV BOLI", Font.BOLD, 40));
        header.setForeground(new Color(188, 63, 21));

        // Footer label
        msg = new JLabel("Please Select Your Role");
        msg.setBounds(300, 800, 1000, 50);  // Position at the bottom
        msg.setHorizontalAlignment(JLabel.CENTER);  // Center the text
        msg.setFont(new Font("MV BOLI", Font.PLAIN, 20));
        msg.setForeground(new Color(188, 63, 21));


        Adminbtn = new JButton();
        Adminbtn.setBounds(500, 600, 200, 50);  // As per the original position
        Adminbtn.addActionListener(this);
        Adminbtn.setText("Admin");
        Adminbtn.setFocusable(false);
        Adminbtn.setHorizontalTextPosition(JButton.CENTER);
        Adminbtn.setVerticalTextPosition(JButton.BOTTOM);
        Adminbtn.setFont(new Font("Comic Sans", Font.BOLD, 25));
        Adminbtn.setIconTextGap(-15);
        Adminbtn.setForeground(Color.white);
        Adminbtn.setBackground(new Color(188, 63, 21));
        Adminbtn.setBorder(BorderFactory.createEtchedBorder());


        Userbtn = new JButton();
        Userbtn.setBounds(800, 600, 200, 50);  // As per the original position
        Userbtn.addActionListener(this);
        Userbtn.setText("User");
        Userbtn.setFocusable(false);
        Userbtn.setHorizontalTextPosition(JButton.CENTER);
        Userbtn.setVerticalTextPosition(JButton.BOTTOM);
        Userbtn.setFont(new Font("Comic Sans", Font.BOLD, 25));
        Userbtn.setIconTextGap(-15);
        Userbtn.setForeground(Color.white);
        Userbtn.setBackground(new Color(188, 63, 21));
        Userbtn.setBorder(BorderFactory.createEtchedBorder());

        // Set the frame layout and size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        // Maximize window on startup
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximizes the window when opened

        // Make the window non-resizable (optional, depends on your preference)
        this.setResizable(true);  // Allow resizing after maximization

        // Set the frame visibility
        this.setVisible(true);

        // Create a custom JPanel for the background and add components
        JPanel contentPanel = new CustomPanel();
        contentPanel.setLayout(null);  // Set absolute layout for custom positioning
        contentPanel.add(header);
        contentPanel.add(msg);
        contentPanel.add(Adminbtn);
        contentPanel.add(Userbtn);

        // Set content pane
        this.setContentPane(contentPanel);

        // Refresh the frame to properly display everything
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // If Admin button is clicked, replace content with Admin GUI
        if (e.getSource() == Adminbtn) {
            System.out.println("You are an Admin");
            getContentPane().removeAll();
            AdminGUI adminGUI = new AdminGUI();
            adminGUI.setLayout(null);  // Ensure Admin GUI uses absolute positioning
            adminGUI.setBounds(0, 0, getWidth(), getHeight()); // Make AdminGUI fill the frame
            this.add(adminGUI);
            revalidate();
            repaint();
        }
        // If User button is clicked, replace content with User GUI
        else if (e.getSource() == Userbtn) {
            System.out.println("You are a User");
            // Clear the current content
            getContentPane().removeAll();
            // Create and add the User GUI components
            UserGUI userGUI = new UserGUI();
            userGUI.setLayout(null);  // Ensure User GUI uses absolute positioning
            userGUI.setBounds(0, 0, getWidth(), getHeight()); // Make UserGUI fill the frame
            this.add(userGUI);

            // Refresh the frame
            revalidate();
            repaint();
        }
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

    // Custom JPanel to handle the background image painting
    class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);  // Ensure the panel is rendered properly
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);  // Draw the background image
            }
        }
    }

    public static void main(String[] args) {
        new AdminOrUser();
    }
}
