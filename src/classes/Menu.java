package classes;
import Exceptions.Dishfound;
import Exceptions.NOTFOUND;
import Exceptions.NonNumericInputException;
import txt.handles;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<dish> dishes;
    Scanner scanner = new Scanner(System.in);
    static handles fille=new handles();

    public Menu() {
        this.dishes = new ArrayList<>();
    }
    public ArrayList<dish> getDishes() {
        return dishes;
    }

    public void addDishtofoundRes(String name, String description, int price, String type, String spicy) throws Dishfound, NonNumericInputException {
        dish d = new dish(type,name, description, price,  spicy);
        for (dish dd : dishes) {
            if (dd.getName().equalsIgnoreCase(name)) {
                throw new Dishfound("Dish with the name '" + name + "' already exists and cannot be added again!");
            }
        }
        dishes.add(d);
    }

    public boolean removeDish(String name) throws NOTFOUND {
        for (dish d : dishes) {
            if (d.getName().equalsIgnoreCase(name)) {
                dishes.remove(d);
                System.out.println(d.getName() + " has been removed.");
                return true;
            }
        }
        throw new NOTFOUND("Dish with name \"" + name + "\" not found.");
    }


    public void selectDishByName(Restaurant ress,String name) throws FileNotFoundException {   // for users
        for (dish dishh : ress.getmenu().getDishes()) {
            if (dishh.getName().equalsIgnoreCase(name)) {
                dishh.setSelceted(true);
            }
            System.out.println("Do you want more ?" + '\n' + "select yes if you want other print no");
            String ye = scanner.nextLine();
            if (ye.equalsIgnoreCase("no")) break;
        }
    }

    public String searchcategroy(String type) {//search by main and like this
        String dod = new String();
        for (dish d : dishes) {
            if (d.getType().equalsIgnoreCase(type)) {
                dod+=("Dish: "+' '+d.getName()+','+"Desciription: "+' '+d.getDescription()+','+"Price: "+' '+d.getPrice()+','+"SpicyLevel: "+' '+d.getSpicy()+'\n');
            }
        }
        return dod;
    }

    public String DisplayOrderbyType() {
        String result = "";
        result += "Main dish:\n" + searchcategroy("Main") + "\n";
        result += "Side dish:\n" + searchcategroy("Side") + "\n";
        result += "Desserts:\n" + searchcategroy("Desserts") + "\n";
        result += "Drinks:\n" + searchcategroy("Drinks") + "\n";

        return result;
    }

    public void updatedish()
    {
        System.out.println("Which dish you need to update ?");
        String name=scanner.nextLine();
        for(dish d:dishes) {
            if (d.getName().equalsIgnoreCase(name)) {
                System.out.println("What do you need to update ?");
                String which = scanner.nextLine();
                if (which.equals("price")) {
                    System.out.println("what is the value of the newest ?");
                    int newprice = scanner.nextInt();
                    d.setPrice(newprice);
                }
                else if(which.equalsIgnoreCase("Description"))
                {
                    System.out.println("what is the new descirption?");
                    String newdes = scanner.nextLine();
                    d.setDescription(newdes);
                }
            }
        }
    }
    @Override
    public String toString() {
        if (dishes.isEmpty()) {
            return "Menu is empty.";
        }
        String result = "Menu:\n";
        for (dish d : dishes) {
            result += d.toString() + "\n";
        }
        return result;
    }

}






























