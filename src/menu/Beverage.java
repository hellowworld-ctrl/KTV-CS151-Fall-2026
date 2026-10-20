package menu;

import java.util.ArrayList;

public class Beverage extends MenuItem {

    private static ArrayList<Beverage> allDrinks = new ArrayList<Beverage>();
    private static boolean loaded = false;

    private String size;
    private boolean alcoholic;
    private String ice;
    private boolean happyHour;

    public Beverage(String name, double price, int stock, String size,
            boolean alcoholic, String ice) {
        super(name, price, stock);
        this.size = "";
        this.ice = "";
        this.alcoholic = alcoholic;
        this.happyHour = false;

        if (getName().equals("") || price < 0 || stock < 0) {
            return;
        }
        if (findDrink(name) != null) {
            System.out.println(name + " is already on the drink menu");
            return;
        }
        if (allDrinks.size() >= MAX_ITEMS) {
            System.out.println("drink menu is full");
            return;
        }

        setSize(size);
        setIce(ice);
        if (this.size.equals("") || this.ice.equals("")) {
            return;
        }
        allDrinks.add(this);
    }

    public static void loadKtvDrinks() {
        if (loaded) {
            return;
        }
        loaded = true;

        // drinks a room usually orders
        new Beverage("Tsingtao", 6, 40, "bottle", true, "none");
        new Beverage("Heineken", 6, 40, "bottle", true, "none");
        new Beverage("Corona", 6, 30, "bottle", true, "none");
        new Beverage("Soju", 14, 20, "bottle", true, "none");
        new Beverage("Jack and Coke", 10, 25, "glass", true, "regular");
        new Beverage("Vodka Cranberry", 10, 25, "glass", true, "regular");
        new Beverage("Plum Wine", 8, 15, "glass", true, "none");
        new Beverage("Milk Tea", 6, 20, "cup", false, "regular");
        new Beverage("Coke", 3, 40, "can", false, "none");
        new Beverage("Sprite", 3, 40, "can", false, "none");
        new Beverage("Red Bull", 5, 20, "can", false, "none");
        new Beverage("Jasmine Tea", 5, 15, "pot", false, "none");
    }

    public static Beverage findDrink(String name) {
        if (name == null) {
            return null;
        }
        for (int i = 0; i < allDrinks.size(); i++) {
            if (allDrinks.get(i).getName().equalsIgnoreCase(name)) {
                return allDrinks.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Beverage> getAllDrinks() {
        return allDrinks;
    }

    public static boolean removeDrink(String name) {
        for (int i = 0; i < allDrinks.size(); i++) {
            if (allDrinks.get(i).getName().equalsIgnoreCase(name)) {
                allDrinks.remove(i);
                return true;
            }
        }
        System.out.println("could not find " + name + " on the drink menu");
        return false;
    }

    public String getSize() {
        return size;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public String getIce() {
        return ice;
    }

    public boolean isHappyHour() {
        return happyHour;
    }

    public void setSize(String size) {
        if (size == null) {
            System.out.println("size cannot be empty");
            return;
        }
        String cleaned = size.trim().toLowerCase();
        if (cleaned.equals("can") || cleaned.equals("bottle") || cleaned.equals("glass")
                || cleaned.equals("cup") || cleaned.equals("pitcher") || cleaned.equals("pot")) {
            this.size = cleaned;
            return;
        }
        System.out.println("size has to be can, bottle, glass, cup, pitcher, or pot");
    }

    public void setIce(String ice) {
        if (ice == null) {
            System.out.println("ice cannot be empty");
            return;
        }
        String cleaned = ice.trim().toLowerCase();
        if (cleaned.equals("none") || cleaned.equals("less")
                || cleaned.equals("regular") || cleaned.equals("extra")) {
            this.ice = cleaned;
            return;
        }
        System.out.println("ice has to be none, less, regular, or extra");
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    public void changeIce(String ice) {
        setIce(ice);
    }

    // 20 percent off alcohol, only once
    public void applyHappyHour() {
        if (!alcoholic) {
            System.out.println(getName() + " is not an alcoholic drink");
            return;
        }
        if (happyHour) {
            System.out.println(getName() + " is already on happy hour");
            return;
        }
        setPrice(getPrice() * 0.8);
        happyHour = true;
    }

    // can becomes a bottle, a glass or cup becomes a pitcher
    public void upsize() {
        if (size.equals("can")) {
            size = "bottle";
            setPrice(getPrice() * 1.5);
            return;
        }
        if (size.equals("glass") || size.equals("cup")) {
            size = "pitcher";
            setPrice(getPrice() * 3);
            return;
        }
        System.out.println(getName() + " is already a " + size);
    }

    @Override
    public String describeItem() {
        String kind = "non-alcoholic";
        if (alcoholic) {
            kind = "alcoholic";
        }
        return size + ", " + kind + ", ice: " + ice;
    }

    @Override
    public boolean needsIdCheck() {
        return alcoholic;
    }
}
