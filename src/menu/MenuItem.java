package menu;

// stuff every ktv menu item has in common
public abstract class MenuItem {

    public static final int MAX_ITEMS = 100;

    private String name;
    private double price;
    private int stock;
    private boolean available;

    public MenuItem(String name, double price, int stock) {
        this.name = "";
        this.price = 0;
        this.stock = 0;
        this.available = false;
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    // food and drinks describe themselves differently
    public abstract String describeItem();

    // only some drinks need an id
    public abstract boolean needsIdCheck();

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setName(String name) {
        if (name == null || name.trim().equals("")) {
            System.out.println("name cannot be empty");
            return;
        }
        this.name = name.trim();
    }

    public void setPrice(double price) {
        if (price < 0) {
            System.out.println("price cannot be negative");
            return;
        }
        // keep prices at cents
        this.price = Math.round(price * 100.0) / 100.0;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            System.out.println("stock cannot be negative");
            return;
        }
        this.stock = stock;
        this.available = stock > 0;
    }

    public boolean canServe(boolean hasId) {
        if (!available) {
            System.out.println(name + " is sold out");
            return false;
        }
        if (needsIdCheck() && !hasId) {
            System.out.println("need an id to order " + name);
            return false;
        }
        return true;
    }

    // takes some off the shelf if there is enough
    public boolean order(int amount) {
        if (amount <= 0) {
            System.out.println("order amount has to be at least 1");
            return false;
        }
        if (!available || stock < amount) {
            System.out.println("not enough " + name + " left");
            return false;
        }
        stock = stock - amount;
        if (stock == 0) {
            available = false;
        }
        return true;
    }

    public void restock(int amount) {
        if (amount <= 0) {
            System.out.println("restock amount has to be at least 1");
            return;
        }
        setStock(stock + amount);
    }

    public void markSoldOut() {
        stock = 0;
        available = false;
    }

    @Override
    public String toString() {
        String status = "available";
        if (!available) {
            status = "sold out";
        }
        return name + " ($" + String.format("%.2f", price) + ", " + status
                + ", stock " + stock + ") - " + describeItem();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        MenuItem item = (MenuItem) other;
        return this.name.equalsIgnoreCase(item.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
