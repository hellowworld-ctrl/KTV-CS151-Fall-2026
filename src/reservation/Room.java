package reservation;

import billing.Billable;

public class Room implements Billable {
    private int roomId;
    private int capacity;
    private double hourlyRate;
    private boolean available;

    public Room(int roomId, int capacity, double hourlyRate) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.hourlyRate = hourlyRate;
        this.available = true;

        setCapacity(capacity);
        setHourlyRate(hourlyRate);
    }

    public int getRoomId() {
        return roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            System.out.println("capacity has to be at least 1");
            return;
        }
        this.capacity = capacity;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            System.out.println("hourly rate cannot be negative");
            return;
        }
        this.hourlyRate = Math.round(hourlyRate * 100.0) / 100.0;
    }

    public boolean canFit(int numberOfGuests) {
        if (numberOfGuests <= 0) {
            System.out.println("number of guests has to be at least 1");
            return false;
        }

        return numberOfGuests <= capacity;
    }

    @Override 
    public double calculateCost(int hours) {
        if (hours <= 0) {
            System.out.println("hours has to be at least 1");
            return 0;
        }

        double total = hourlyRate * hours;
        return Math.round(total * 100.0) / 100.0;
    }

    public boolean checkIn() {
        if (!available) {
            System.out.println("room " + roomId + " is already occupied");
            return false;
        }

        available = false;
        return true;
    }

    public void checkOut() {
        available = true;
    }

    @Override
    public String toString() {
        String status = "available";

        if (!available) {
            status = "occupied";
        }

        return "Room " + roomId + " (capacity " + capacity
                + ", $" + String.format("%.2f", hourlyRate)
                + "/hour, " + status + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        Room room = (Room) other;
        return this.roomId == room.roomId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(roomId);
    }
}
