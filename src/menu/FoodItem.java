package menu;

import java.util.ArrayList;

public class FoodItem extends MenuItem {

    private static ArrayList<FoodItem> allFood = new ArrayList<FoodItem>();
    private static boolean loaded = false;

    private int spiceLevel;
    private int servings;

    public FoodItem(String name, double price, int stock, int spiceLevel, int servings) {
        super(name, price, stock);
        this.spiceLevel = -1;
        this.servings = 0;

        if (getName().equals("") || price < 0 || stock < 0) {
            return;
        }
        if (findFood(name) != null) {
            System.out.println(name + " is already on the food menu");
            return;
        }
        if (allFood.size() >= MAX_ITEMS) {
            System.out.println("food menu is full");
            return;
        }

        updateSpice(spiceLevel);
        changeServings(servings);
        if (this.spiceLevel == -1 || this.servings == 0) {
            return;
        }
        allFood.add(this);
    }

    public static void loadKtvFood() {
        if (loaded) {
            return;
        }
        loaded = true;

        // plates people usually get in a ktv room
        new FoodItem("French Fries", 8, 20, 0, 3);
        new FoodItem("Popcorn Chicken", 12, 15, 1, 3);
        new FoodItem("Salt and Pepper Wings", 14, 12, 2, 3);
        new FoodItem("Fruit Platter", 16, 10, 0, 4);
        new FoodItem("Spring Rolls", 9, 15, 0, 3);
        new FoodItem("Beef Skewers", 15, 12, 1, 2);
        new FoodItem("Edamame", 7, 20, 0, 2);
        new FoodItem("Sunflower Seeds", 5, 30, 0, 4);
        new FoodItem("Fried Rice", 11, 10, 0, 2);
        new FoodItem("Takoyaki", 10, 10, 0, 2);
    }

    public static FoodItem findFood(String name) {
        if (name == null) {
            return null;
        }
        for (int i = 0; i < allFood.size(); i++) {
            if (allFood.get(i).getName().equalsIgnoreCase(name)) {
                return allFood.get(i);
            }
        }
        return null;
    }

    public static ArrayList<FoodItem> getAllFood() {
        return allFood;
    }

    public static boolean removeFood(String name) {
        for (int i = 0; i < allFood.size(); i++) {
            if (allFood.get(i).getName().equalsIgnoreCase(name)) {
                allFood.remove(i);
                return true;
            }
        }
        System.out.println("could not find " + name + " on the food menu");
        return false;
    }

    public int getSpiceLevel() {
        return spiceLevel;
    }

    public int getServings() {
        return servings;
    }

    public void setSpiceLevel(int spiceLevel) {
        updateSpice(spiceLevel);
    }

    public void setServings(int servings) {
        changeServings(servings);
    }

    // 0 none, 1 mild, 2 medium, 3 hot
    public void updateSpice(int level) {
        if (level < 0 || level > 3) {
            System.out.println("spice level has to be 0, 1, 2, or 3");
            return;
        }
        spiceLevel = level;
    }

    public void changeServings(int servings) {
        if (servings < 1 || servings > 8) {
            System.out.println("servings has to be from 1 to 8");
            return;
        }
        this.servings = servings;
    }

    // bigger share plate, price goes up 2 dollars a person
    public void addToPlate(int extraPeople) {
        if (extraPeople <= 0) {
            System.out.println("extra people has to be at least 1");
            return;
        }
        int updated = servings + extraPeople;
        if (updated > 8) {
            System.out.println("a plate can only serve up to 8");
            return;
        }
        servings = updated;
        setPrice(getPrice() + (2 * extraPeople));
    }

    @Override
    public String describeItem() {
        return "serves " + servings + ", spice " + spiceLevel;
    }

    @Override
    public boolean needsIdCheck() {
        return false;
    }

    public static void main(String[] args) {
        loadKtvFood();
        Beverage.loadKtvDrinks();

        ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
        for (int i = 0; i < allFood.size(); i++) {
            menu.add(allFood.get(i));
        }
        ArrayList<Beverage> drinks = Beverage.getAllDrinks();
        for (int i = 0; i < drinks.size(); i++) {
            menu.add(drinks.get(i));
        }

        System.out.println("KTV Menu");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(menu.get(i).toString());
        }
    }
}
