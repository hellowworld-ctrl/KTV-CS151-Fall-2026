package menu;

import java.util.ArrayList;

public class Beverage extends MenuItem {

    private static ArrayList<Beverage> allDrinks = new ArrayList<Beverage>();
    private static boolean loaded = false;

    private String size;
    private boolean alcoholic;
    private String ice;
    private boolean happyHour;
    private double priceBeforeHappyHour;

    public Beverage(String name, double price, int stock, String size,
            boolean alcoholic, String ice) {
        super(name, price, stock);
        this.size = "";
        this.ice = "";
        this.alcoholic = alcoholic;
        this.happyHour = false;
        this.priceBeforeHappyHour = getPrice();

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
        String cleaned = name.trim();
        for (int i = 0; i < allDrinks.size(); i++) {
            if (allDrinks.get(i).getName().equalsIgnoreCase(cleaned)) {
                return allDrinks.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Beverage> getAllDrinks() {
        return allDrinks;
    }

    public static boolean removeDrink(String name) {
        if (name == null) {
            System.out.println("could not find that drink on the drink menu");
            return false;
        }
        String cleaned = name.trim();
        for (int i = 0; i < allDrinks.size(); i++) {
            if (allDrinks.get(i).getName().equalsIgnoreCase(cleaned)) {
                allDrinks.remove(i);
                return true;
            }
        }
        System.out.println("could not find " + cleaned + " on the drink menu");
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

    // 20 percent off alcohol, only once. bulk deals still use this price
    public void applyHappyHour() {
        if (!alcoholic) {
            System.out.println(getName() + " is not an alcoholic drink");
            return;
        }
        if (happyHour) {
            System.out.println(getName() + " is already on happy hour");
            return;
        }
        priceBeforeHappyHour = getPrice();
        setPrice(priceBeforeHappyHour * 0.8);
        happyHour = true;
    }

    public void endHappyHour() {
        if (!happyHour) {
            System.out.println(getName() + " is not on happy hour");
            return;
        }
        setPrice(priceBeforeHappyHour);
        happyHour = false;
    }

    // can becomes a bottle, a glass or cup becomes a pitcher
    public void upsize() {
        if (happyHour) {
            System.out.println("end happy hour before changing the size of " + getName());
            return;
        }
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

    // bucket is 6 cans or bottles and the room pays for 5
    // separate from the 10 percent bulk deal, so they do not stack
    public double buyBucket(boolean hasId) {
        if (!size.equals("can") && !size.equals("bottle")) {
            System.out.println(getName() + " does not come in a bucket");
            return -1;
        }
        if (!canServe(hasId)) {
            return -1;
        }
        if (getStock() < 6) {
            System.out.println("not enough " + getName() + " for a bucket");
            return -1;
        }
        double total = Math.round(getPrice() * 5 * 100.0) / 100.0;
        if (!order(6)) {
            return -1;
        }
        System.out.println("bucket of 6 " + getName() + " (pay for 5) for $"
                + String.format("%.2f", total));
        return total;
    }

    // tea, a cup, or a pitcher can be refilled for half the current price
    public double refill(boolean hasId) {
        if (!size.equals("cup") && !size.equals("pitcher") && !size.equals("pot")) {
            System.out.println(getName() + " cannot be refilled");
            return -1;
        }
        if (!canServe(hasId)) {
            return -1;
        }
        double total = Math.round(getPrice() * 0.5 * 100.0) / 100.0;
        if (!order(1)) {
            return -1;
        }
        System.out.println("refilled " + getName() + " for $" + String.format("%.2f", total));
        return total;
    }

    @Override
    public String describeItem() {
        String kind = "non-alcoholic";
        if (alcoholic) {
            kind = "alcoholic";
        }
        String special = "";
        if (happyHour) {
            special = ", happy hour";
        }
        return size + ", " + kind + ", ice: " + ice + special;
    }

    @Override
    public boolean needsIdCheck() {
        return alcoholic;
    }
}
