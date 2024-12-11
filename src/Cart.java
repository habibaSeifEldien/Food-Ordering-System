import java.util.Scanner;

public class Cart {
    public float Total=0;
    Restaurant r;
    private Scanner scanner = new Scanner(System.in);

    public Cart()
    {
        this.r=new Restaurant();
    }
    public void CalcDiscount() //Calculate Discount using Promocodes
    {
        System.out.println("what is the PromoCode?");
        String  PromoCode=scanner.nextLine();

        for(dish d:r.getmenu().getDishes())
        {
            if(d.Selceted)
            {
                this.Total+=d.getPrice();
            }
        }

        float dis = 0;
        if (PromoCode.equals("PC-20")) {
            dis = 20/100; // discount 20%
        } else if (PromoCode.equals("PC-30")) {
            dis = 30/100; // discount 30%
        } else if (PromoCode.equals("PC-40")) {
            dis = 40/100; // discount 40%
        } else if (PromoCode.equals("PC-50")) {
            dis = 50/100; // discount 50%
        } else {
            System.out.println("Invalid promo code");
        }
        this.Total= this.Total - (this.Total * dis);
        System.out.println("Discount: " + (dis*100) + "%");

}

    public void UpdateOrder() {
        Scanner scanner = new Scanner(System.in);
        boolean EndUpdate = false;
        while (!EndUpdate) {
            System.out.println("Add Dish / Remove Dish");
            System.out.println("1 - Add dish");
            System.out.println("2 - Remove dish");
            System.out.println("3 - Finished Updating");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the dish you want to add : ");
                    String add_updated_dish = scanner.nextLine();
                    dish updated = null;
                    for (dish d : r.getmenu().getDishes()) {
                        boolean isEqual = true;
                        if (d.getName().length() == add_updated_dish.length()) {
                            for (int i = 0; i < d.getName().length(); i++) {
                                if (d.getName().charAt(i) != add_updated_dish.charAt(i)) {
                                    isEqual = false;
                                    break;
                                }
                            }
                        } else {
                            isEqual = false;
                        }

                        if (isEqual) {
                            updated = d;
                            break;
                        }
                    }
                    if (updated != null) {
                        updated.Selceted = true;
                        System.out.println(updated.getName() + " has been added to your cart.");
                    } else {
                        System.out.println("Dish not found!");
                    }
                    break;

                case 2:
                    System.out.println("Enter the name of the dish to remove:");
                    String removed_dish = scanner.nextLine();
                    for (dish d : r.getmenu().getDishes()) {
                        boolean isEqual = true;
                        if (d.getName().length() == removed_dish.length()) {
                            for (int i = 0; i < d.getName().length(); i++) {
                                if (d.getName().charAt(i) != removed_dish.charAt(i)) {
                                    isEqual = false;
                                    break;
                                }
                            }
                        } else {
                            isEqual = false;
                        }

                        if (isEqual && d.Selceted) {
                            d.Selceted = false;
                            System.out.println(d.getName() + " has been removed from your cart.");
                            break;
                        }
                    }
                    break;

                case 3:
                    EndUpdate = true;
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
}
}

}