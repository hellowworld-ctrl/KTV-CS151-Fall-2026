package reservation;

public class Reservation {

    private static int nextId = 1;

    private int reservationId;
    private String customerName;
    private Room room;
    private int numberOfGuests;
    private int hours;
    private boolean active;

    public Reservation(String customerName, Room room,
                       int numberOfGuests, int hours) {

        this.reservationId = nextId++;
        this.customerName = "";
        this.room = room;
        this.numberOfGuests = 0;
        this.hours = 0;
        this.active = false;

        setCustomerName(customerName);
        setNumberOfGuests(numberOfGuests);
        setHours(hours);
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Room getRoom() {
        return room;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getHours() {
        return hours;
    }

    public boolean isActive() {
        return active;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().equals("")) {
            System.out.println("customer name cannot be empty");
            return;
        }
        this.customerName = customerName.trim();
    }

    public void setNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests <= 0) {
            System.out.println("number of guests has to be at least 1");
            return;
        }
        this.numberOfGuests = numberOfGuests;
    }

    public void setHours(int hours) {
        if (hours <= 0) {
            System.out.println("hours has to be at least 1");
            return;
        }
        this.hours = hours;
    }

    public boolean confirmReservation() {
        if (room == null) {
            System.out.println("reservation needs a room");
            return false;
        }
        
        if (active) {
            System.out.println("reservation is already active");
            return false;
        }

        if (!room.canFit(numberOfGuests)) {
            System.out.println("too many guests for this room");
            return false;
        }

        if (!room.isAvailable()) {
            System.out.println("room is not available");
            return false;
        }

        if (!room.checkIn()) {
            return false;
        }

        active = true;
        return true;
    }

    public boolean cancelReservation() {
        if (!active) {
            System.out.println("reservation is not active");
            return false;
        }

        room.checkOut();
        active = false;
        return true;
    }

    public boolean extendReservation(int additionalHours) {
        if (!active) {
            System.out.println("reservation is not active");
            return false;
        }

        if (additionalHours <= 0) {
            System.out.println("additional hours has to be at least 1");
            return false;
        }

        hours = hours + additionalHours;
        return true;
    }

    public double calculateTotal() {
        return room.calculateCost(hours);
    }

    @Override
    public String toString() {
        String status = "inactive";

        if (active) {
            status = "active";
        }

        return "Reservation " + reservationId
                + " for " + customerName
                + ", Room " + room.getRoomId()
                + ", " + numberOfGuests + " guests"
                + ", " + hours + " hours"
                + ", " + status;
    }

}