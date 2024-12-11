import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends Registration{
    public int adminID;
    public  Admin currentAdmin = null;
    public static ArrayList<Admin> admins = new ArrayList<>();
    private static  File adminfile= new File("C:\\Users\\asus\\IdeaProjects\\thotho\\src\\Adminn.txt");

    public Admin (String FirstName, String LastName, String email, String password,int adminID){
        super(FirstName, LastName, email, password);
        this.adminID=adminID;
    }
    public Admin(){
        super();
    }
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getAdminID() {
        return adminID;
    }

    @Override
    public  boolean login(String enteredEmail, String enteredPassword) {
        for (Admin admin : admins) {
            if (admin.getEmail().equals(enteredEmail) && admin.getPassword().equals(enteredPassword)) {
                currentAdmin = admin;
                System.out.println("\"Login successful for admin: " +enteredEmail+ " , " +enteredPassword );
                saveToFile();
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

    @Override
    public void saveToFile() {
        try (FileWriter writer = new FileWriter(adminfile, true)) { // 'true' appends to the file instead of overwriting
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
        admins.clear(); // Clear existing data

        try (Scanner fileScanner = new Scanner(adminfile)) { // Create File object for the Scanner
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
            System.out.println("Data loaded successfully!");
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
}
}



}
