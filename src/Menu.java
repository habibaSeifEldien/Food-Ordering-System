import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<dish> dishes;
    Scanner scanner = new Scanner(System.in);
    static handles  fille=new handles();

    public Menu() {
        this.dishes = new ArrayList<>();
    }

    public void addDish(String name, String description, int price, String type, String spicy) throws Dishfound{
        dish d = new dish(type,name, description, price,  spicy);
        for (dish dd : dishes) {
            if (dd.getName().equalsIgnoreCase(name)) {
                throw new Dishfound("Dish with the name '" + name + "' already exists and cannot be added again!");
            }
        }
        dishes.add(d);
    }

    // Remove a dish by name
    public void removeDish(String name) {
        for (dish d : dishes) {
            if (d.getName().toLowerCase().equals(name)) {
                dishes.remove(d);
                System.out.println(d.getName() + " has been removed.");
               fille.saveToFile();
                return;
            }
        }
        System.out.println("Dish not found to remove!");
    }

    public ArrayList<dish> getDishes() {
        return dishes;
    }

    public dish getDishByName(String name) {
        for (dish dish : dishes) {
            if (dish.getName().equalsIgnoreCase(name)) {
                return dish;
            }
        }
        return null;
    }

    public void selectDishByName(Restaurant ress) throws FileNotFoundException {   // for users


            for (dish dishh : ress.getmenu().getDishes()) {
                String name = scanner.nextLine();
                if (dishh.getName().equalsIgnoreCase(name)) {
                    dishh.Selceted = true;
                }
                System.out.println("Do you want more ?" + '\n' + "select yes if you want other print no");
                String ye = scanner.nextLine();
                if (ye.equalsIgnoreCase("no")) break;
            }
    }
    public ArrayList<dish> searchcategroy(String type) {//search by main and like this
        ArrayList<dish>dod=new ArrayList<>();
        for (dish d : dishes) {
            if (d.getType().equalsIgnoreCase(type)) {
                dod.add(d);
            }
        }
        return dod;
    }

    public void DisplayOrderbyType()
    {
        System.out.println("Main dish");
       System.out.println( searchcategroy("Main"));
        System.out.println("Side dish");
        System.out.println( searchcategroy("Side"));
        System.out.println("Desserts");
        System.out.println( searchcategroy("Desserts"));
        System.out.println("Drinks");
        System.out.println( searchcategroy("Drinks"));
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
        StringBuilder sb = new StringBuilder("Menu:\n");
        for (dish d : dishes) {
            sb.append(d.toString()).append("\n");
        }
        return sb.toString();
    }

}