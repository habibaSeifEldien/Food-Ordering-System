package classes;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cart extends JFrame {
    public double total;
    public Restaurant r;

    public Cart() {
        this.total = 0;
        this.r = new Restaurant();
    }
    public void setTotal(double total)
    {
        this.total=total;
    }
    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }



    public void addItem(String itemName, int quantity) {
        for (dish d : r.getmenu().getDishes()) {
            if (itemName.isEmpty() || itemName.equals("Item Name") || quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid item name and quantity.");
                return;
            }
            if (d.getName().equalsIgnoreCase(itemName) && d.Selceted) {
                setQuantity(getQuantity() + quantity); // Increase by specified quantity
                total += quantity * d.getPrice();         // Update the total price
            }
        }
    }

    public void removeItem(String itemName, int quantity) {
        boolean itemSelected = false;
        for (dish d : r.getmenu().getDishes()) {
            if (d.getName().equalsIgnoreCase(itemName) && d.Selceted && getQuantity() > 0) {
                int newQuantity = getQuantity() - quantity;
                if (newQuantity < 0) {
                    quantity = getQuantity(); // Remove only available quantity
                }
                setQuantity(getQuantity() - quantity); // Decrease by specified quantity
                total -= quantity * d.getPrice();         // Update the total price

                if (getQuantity() == 0) {
                    d.Selceted = false; // Deselect the dish if quantity is zero
                }
                itemSelected = true;
            }
        }
        if (!itemSelected) {
            System.out.println("Dish not found in the cart.");
        }
    }


    public double calcTotal() {
        for (dish d : r.getmenu().getDishes()) {
            if (d.Selceted) {
                total += d.getPrice();
            }
        }
        return total;
    }

    public double calcDiscount(String promoCode) {
        double discount = 0;
        if (promoCode.equals("PC-20")) {
            discount = 0.20;
            JOptionPane.showMessageDialog(this, "Promo code applied successfully!");
        } else if (promoCode.equals("PC-30")) {
            discount = 0.30;
            JOptionPane.showMessageDialog(this, "Promo code applied successfully!");
        } else if (promoCode.equals("PC-40")) {
            discount = 0.40;
            JOptionPane.showMessageDialog(this, "Promo code applied successfully!");
        } else if (promoCode.equals("PC-50")) {
            discount = 0.50;
            JOptionPane.showMessageDialog(this, "Promo code applied successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid promo code. No discount applied.");
            return total;
        }
        return total - (total * discount);
    }


    public void otherNotes(String notes) {
        if (notes.equalsIgnoreCase("no")) {
            JOptionPane.showMessageDialog(this, "Have a nice meal!");
        } else {
            JOptionPane.showMessageDialog(this, "Special request added: " + notes);
        }
    }

}
