package reservation;

public class Room {
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
}
