package ktv;

import java.util.ArrayList;

import customer.Customer;
import reservation.Reservation;
import reservation.Room;

// the ktv holds the rooms, customers, and reservations
public class KTVSystem {

    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Customer> customers;
    private ArrayList<Reservation> reservations;

    // makes an empty ktv
    public KTVSystem(String name) {
        this.name = name;
        this.rooms = new ArrayList<Room>();
        this.customers = new ArrayList<Customer>();
        this.reservations = new ArrayList<Reservation>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    // adds a room
    public void addRoom(Room room) {
    }

    // adds a customer
    public void addCustomer(Customer customer) {
    }

    // makes a reservation for a customer and a room
    public Reservation makeReservation(Customer customer, Room room, int guests, int hours) {
        return null;
    }

    // finds a room by id
    public Room findRoom(int roomId) {
        return null;
    }

    // finds a customer by name
    public Customer findCustomer(String name) {
        return null;
    }

    // returns the ktv name
    public String toString() {
        return name;
    }
}
