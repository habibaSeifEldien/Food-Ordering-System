package classes;
import classes.Registration;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Admin extends Registration {
    public int adminID;
    public Admin currentAdmin = null;
    public static ArrayList<Admin> admins = new ArrayList<>();
    private static File adminfile = new File("C:\\Users\\asus\\IdeaProjects\\Food Ordering Managment\\src\\txt\\Adminn.txt");

    public Admin(String FirstName, String LastName, String email, String password, int adminID) {
        super(FirstName, LastName, email, password);
        this.adminID = adminID;
    }

    public Admin() {
        super();
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getAdminID() {
        return adminID;
    }

    public static String createAccount(String FirstName, String LastName, String email, String password, int adminID) {

        if (FirstName == null || FirstName.trim().isEmpty() || LastName == null || LastName.trim().isEmpty()
                || email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty() || adminID <= 0) {
            return "All fields must be filled correctly";

        }

        else if (password.length() < 5) {
            return "Password must be at least 5 characters long";

        }

        else if (!email.contains("@") || !email.contains(".") || !email.contains(("com"))) {
            return "Please enter a valid email address (Ex: johndoe@gmail.com)";

        }


        Scanner scanner = new Scanner(System.in);
        Admin newAdmin = new Admin(FirstName, LastName, email, password, adminID);
        admins.add(newAdmin);
        newAdmin.saveToFile();
        return "Account created successfully";
    }


    @Override
    public  boolean login(String enteredEmail, String enteredPassword) {
        for (Admin admin : admins) {
            if (admin.getEmail().equals(enteredEmail) && admin.getPassword().equals(enteredPassword)) {
                currentAdmin = admin;
                System.out.println("\"Login successful for admin: " +enteredEmail+ " , " +enteredPassword );
                return true;
            }
        }
        System.out.println("\"Login failed . Invalid email or password");
        return false;
    }

    @Override
    public void logout(){
        if (currentAdmin != null){
            System.out.println("logout successful for admin : "+ currentAdmin.getAdminID());
            currentAdmin = null;
        }
        else {
            System.out.println("This admin is not currently logged in");
        }
    }


    public void changeUserName(String Firstname,String lastname)
    {
        setFirstName(Firstname);
        setLastName(lastname);
    }
    public void changePassword(String pass)
    {
        setPassword(pass);
    }

    @Override
    public String toString() {
        return " Name: " + getFirstName()+getLastName() +
                "\n password: " + getPassword() +
                "\nEmail: " + getEmail() +
                "\nId: " + getAdminID();

    }

    @Override
    public void saveToFile() {
        try (FileWriter writer = new FileWriter(adminfile)) {
            for (Admin admin : admins) {
                // Write each user's details on a new line
                writer.write(admin.FirstName + ',' + admin.LastName + ',' +admin.email + ',' + admin.password+ ',' + admin.adminID + '\n');
                writer.write("\n");
            }
            System.out.println("Admins saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile() {
        admins.clear();
        try (Scanner fileScanner = new Scanner(adminfile)) {
            while (fileScanner.hasNextLine()) {
                String detailsLine = fileScanner.nextLine().trim();
                if (detailsLine.isEmpty()) continue;

                String[] details = detailsLine.split(",");

                if (details.length < 5) {
                    System.out.println("Skipping invalid data line: " + detailsLine);
                    continue;
                }

                try {
                    String firstname = details[0].trim();
                    String lastname = details[1].trim();
                    String email = details[2].trim();
                    String pass = details[3].trim();
                    int adminid = Integer.parseInt(details[4].trim());

                    Admin ad = new Admin(firstname, lastname, email, pass, adminid);
                    admins.add(ad);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping admin with invalid Admin ID: " + detailsLine);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

}