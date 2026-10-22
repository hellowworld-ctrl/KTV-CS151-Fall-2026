package customer;

import java.util.ArrayList;

import menu.Beverage;
import menu.MenuItem;
import reservation.Reservation;
import reservation.Room;

// a customer who books a room and pays a tab
// alcohol requires age 21 or older and an id
public class Customer {

    public static final int DRINKING_AGE = 21;

    private static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    private static int nextId = 1;

    private int customerId;
    private String name;
    private String phoneNumber;
    private int age;
    private boolean hasId;
    private double tab;
    private Reservation reservation;

    public Customer(String name, String phoneNumber, int age) {
        this.customerId = 0;
        this.name = "";
        this.phoneNumber = "";
        this.age = 0;
        this.hasId = false;
        this.tab = 0;
        this.reservation = null;

        setName(name);
        setPhoneNumber(phoneNumber);
        setAge(age);
        if (this.name.equals("") || this.phoneNumber.equals("") || this.age < 1) {
            return;
        }
        if (allCustomers.size() >= MenuItem.MAX_ITEMS) {
            System.out.println("customer list is full");
            return;
        }

        this.customerId = nextId++;
        allCustomers.add(this);
    }

    public static ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static int getCustomerCount() {
        return allCustomers.size();
    }

    public static Customer findCustomer(String name) {
        if (name == null) {
            return null;
        }
        String cleaned = name.trim();
        for (int i = 0; i < allCustomers.size(); i++) {
            if (allCustomers.get(i).name.equalsIgnoreCase(cleaned)) {
                return allCustomers.get(i);
            }
        }
        return null;
    }

    public static boolean removeCustomer(String name) {
        Customer found = findCustomer(name);
        if (found == null) {
            System.out.println("could not find that customer");
            return false;
        }
        if (found.reservation != null && found.reservation.isActive()) {
            System.out.println(found.name + " still has an active room");
            return false;
        }
        allCustomers.remove(found);
        found.customerId = 0;
        return true;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public boolean hasId() {
        return hasId;
    }

    public double getTab() {
        return tab;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setName(String name) {
        if (name == null || name.trim().equals("")) {
            System.out.println("name cannot be empty");
            return;
        }
        this.name = name.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().equals("")) {
            System.out.println("phone number cannot be empty");
            return;
        }
        String cleaned = phoneNumber.trim();
        for (int i = 0; i < allCustomers.size(); i++) {
            if (allCustomers.get(i) != this
                    && allCustomers.get(i).phoneNumber.equals(cleaned)) {
                System.out.println("a customer with that phone number already exists");
                return;
            }
        }
        this.phoneNumber = cleaned;
    }

    public void setAge(int age) {
        if (age < 1) {
            System.out.println("age has to be at least 1");
            return;
        }
        this.age = age;
    }

    public Reservation bookRoom(Room room, int numberOfGuests, int hours) {
        if (customerId == 0) {
            System.out.println("customer was not added");
            return null;
        }
        if (reservation != null && reservation.isActive()) {
            System.out.println(name + " already has a room");
            return null;
        }
        if (room == null) {
            System.out.println("reservation needs a room");
            return null;
        }

        Reservation booking = new Reservation(name, room, numberOfGuests, hours);
        if (booking.getHours() <= 0 || booking.getNumberOfGuests() <= 0) {
            return null;
        }
        if (!booking.confirmReservation()) {
            return null;
        }
        reservation = booking;
        addCharge(booking.calculateTotal());
        return booking;
    }

    public boolean cancelBooking() {
        if (reservation == null || !reservation.isActive()) {
            System.out.println("no active reservation to cancel");
            return false;
        }
        double roomCost = reservation.calculateTotal();
        if (!reservation.cancelReservation()) {
            return false;
        }
        if (tab >= roomCost) {
            tab = Math.round((tab - roomCost) * 100.0) / 100.0;
        }
        return true;
    }

    public boolean extendStay(int additionalHours) {
        if (reservation == null || !reservation.isActive()) {
            System.out.println("no active reservation to extend");
            return false;
        }
        if (!reservation.extendReservation(additionalHours)) {
            return false;
        }
        addCharge(reservation.getRoom().calculateCost(additionalHours));
        return true;
    }

    public boolean addCharge(double amount) {
        if (amount < 0) {
            System.out.println("charge cannot be negative");
            return false;
        }
        tab = Math.round((tab + amount) * 100.0) / 100.0;
        return true;
    }

    public double pay(double amount) {
        if (amount <= 0) {
            System.out.println("payment has to be more than 0");
            return 0;
        }
        amount = Math.round(amount * 100.0) / 100.0;
        if (amount > tab) {
            System.out.println("payment is more than the tab");
            return 0;
        }
        tab = Math.round((tab - amount) * 100.0) / 100.0;
        return amount;
    }

    // under 21 can show up, but the id does not let them buy alcohol
    public boolean showId() {
        if (customerId == 0) {
            System.out.println("customer was not added");
            return false;
        }
        if (age < DRINKING_AGE) {
            System.out.println(name + " has to be " + DRINKING_AGE + " or older for alcohol");
            return false;
        }
        hasId = true;
        return true;
    }

    public double orderDrink(Beverage drink, int amount) {
        if (customerId == 0 || reservation == null || !reservation.isActive()) {
            System.out.println(name + " needs an active room to order");
            return -1;
        }
        if (drink == null) {
            System.out.println("that drink is not on the menu");
            return -1;
        }
        if (drink.needsIdCheck() && (age < DRINKING_AGE || !hasId)) {
            System.out.println(name + " has to be " + DRINKING_AGE
                    + " or older and show an id for alcohol");
            return -1;
        }

        double total = drink.buy(amount, hasId);
        if (total < 0) {
            return -1;
        }
        addCharge(total);
        return total;
    }

    public double amountDue() {
        return tab;
    }

    @Override
    public String toString() {
        return name + " (#" + customerId + ", age " + age + ", tab $"
                + String.format("%.2f", tab) + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Customer customer = (Customer) other;
        if (phoneNumber.equals("")) {
            return false;
        }
        return phoneNumber.equals(customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }
}
