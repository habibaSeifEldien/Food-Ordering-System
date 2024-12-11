import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Registration {
    protected String FirstName;
    protected String LastName;
    protected String email;
    protected String password;

    public Registration(String FirstName, String LastName, String email, String password)
    {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.email = email;
        this.password = password;
    }
    public Registration(){}
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public abstract boolean login(String enteredEmail , String enteredPassword );
    public abstract void logout();


    private static final String adminfile = "admin (2).txt";
    private static final String userfile = "user (2).txt";

    public abstract void saveToFile();


    public abstract void loadFromFile();




}
