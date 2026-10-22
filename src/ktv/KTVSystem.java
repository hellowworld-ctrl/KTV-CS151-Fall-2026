package ktv;

import java.util.ArrayList;

import customer.Customer;
import menu.MenuItem;
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
    public boolean addRoom(Room room) {
        if (room == null) {
            System.out.println("room cannot be empty");
            return false;
        }
        if (room.getCapacity() < 1 || room.getHourlyRate() < 0) {
            return false;
        }
        if (findRoom(room.getRoomId()) != null) {
            System.out.println("that room already exists");
            return false;
        }
        if (rooms.size() >= MenuItem.MAX_ITEMS) {
            System.out.println("room list is full");
            return false;
        }
        rooms.add(room);
        return true;
    }

    // adds a customer
    public boolean addCustomer(Customer customer) {
        if (customer == null || customer.getCustomerId() == 0) {
            return false;
        }
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getPhoneNumber().equals(customer.getPhoneNumber())) {
                System.out.println("that customer is already in the ktv");
                return false;
            }
        }
        customers.add(customer);
        return true;
    }

    // makes a reservation for a customer and a room
    public Reservation makeReservation(Customer customer, Room room, int guests, int hours) {
        if (customer == null || room == null) {
            System.out.println("need a customer and a room");
            return null;
        }
        Reservation booking = customer.bookRoom(room, guests, hours);
        if (booking == null) {
            return null;
        }
        reservations.add(booking);
        return booking;
    }

    // cancels a booking and takes it off the list
    public boolean cancelReservation(Customer customer) {
        if (customer == null || customer.getReservation() == null) {
            System.out.println("no reservation");
            return false;
        }
        Reservation booking = customer.getReservation();
        if (!customer.cancelBooking()) {
            return false;
        }
        reservations.remove(booking);
        return true;
    }

    // finds a room by id
    public Room findRoom(int roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId() == roomId) {
                return rooms.get(i);
            }
        }
        return null;
    }

    // finds a customer by name
    public Customer findCustomer(String name) {
        if (name == null) {
            return null;
        }
        String cleaned = name.trim();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getName().equalsIgnoreCase(cleaned)) {
                return customers.get(i);
            }
        }
        return null;
    }

    // returns the ktv name
    public String toString() {
        return name;
    }
}
