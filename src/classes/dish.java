package classes;
public class dish {
    private String name;
    private String description;
    private int price;
    private String type;
    private String spicy;
    public boolean Selceted;
    // Constructor
    public dish(String type,String name, String description, int price,  String spicy) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.spicy = spicy;
    }
    public void setSelceted(boolean selected)
    {
        this.Selceted=selected;
    }
    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpicy() {
        return spicy;
    }

    public void setSpicy(String spicy) {
        this.spicy = spicy;
    }

    @Override
    public String toString() {
        return name + "," + description + "," + price + "," + type + "," + spicy;
    }

}