package customer;

import java.util.ArrayList;

import reservation.Reservation;
import reservation.Room;

// a customer who books a room and pays a tab
public class Customer {

    // list of every customer
    private static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    // next id to give out
    private static int nextId = 1;

    private int customerId;
    private String name;
    private String phoneNumber;
    private int age;
    private boolean hasId;
    private double tab;
    private Reservation reservation;

    // makes a new customer
    public Customer(String name, String phoneNumber, int age) {
        this.customerId = nextId++;
        this.name = "";
        this.phoneNumber = "";
        this.age = 0;
        this.hasId = false;
        this.tab = 0;
        this.reservation = null;

        setName(name);
        setPhoneNumber(phoneNumber);
        setAge(age);
    }

    // returns every customer
    public static ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    // returns how many customers there are
    public static int getCustomerCount() {
        return allCustomers.size();
    }

    // finds a customer by name
    public static Customer findCustomer(String name) {
        return null;
    }

    // removes a customer by name
    public static boolean removeCustomer(String name) {
        return false;
    }

    // returns the customer id
    public int getCustomerId() {
        return customerId;
    }

    // returns the name
    public String getName() {
        return name;
    }

    // returns the phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // returns the age
    public int getAge() {
        return age;
    }

    // returns true if they showed an id
    public boolean hasId() {
        return hasId;
    }

    // returns the tab
    public double getTab() {
        return tab;
    }

    // returns their reservation
    public Reservation getReservation() {
        return reservation;
    }

    // sets the name
    public void setName(String name) {
        this.name = name;
    }

    // sets the phone number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // sets the age
    public void setAge(int age) {
        this.age = age;
    }

    // books a room for this customer
    public Reservation bookRoom(Room room, int numberOfGuests, int hours) {
        return null;
    }

    // cancels the current booking
    public boolean cancelBooking() {
        return false;
    }

    // adds more hours to the booking
    public boolean extendStay(int additionalHours) {
        return false;
    }

    // adds a charge to the tab
    public boolean addCharge(double amount) {
        return false;
    }

    // pays some of the tab
    public double pay(double amount) {
        return 0;
    }

    // marks that the customer showed an id
    public boolean showId() {
        return false;
    }

    // returns how much they owe
    public double amountDue() {
        return 0;
    }

    // returns the customer info
    @Override
    public String toString() {
        return "";
    }

    // checks if two customers are the same
    @Override
    public boolean equals(Object other) {
        return false;
    }

    // returns a hash code for this customer
    @Override
    public int hashCode() {
        return 0;
    }
}
