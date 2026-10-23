package test;

import reservation.Room;

public class RoomTest {

    public static void main(String[] args) {

        testCalculateCost();
        testCanFit();
        testCheckIn();
        testCheckOut();

        System.out.println("All Room tests passed!");
    }

    public static void testCalculateCost() {
        Room room = new Room(1, 6, 40.00);

        double result = room.calculateCost(2);

        if (result != 80.00) {
            throw new AssertionError(
                    "Expected cost to be $80.00, but got $" + result);
        }
    }

    public static void testCanFit() {
        Room room = new Room(2, 6, 40.00);

        if (!room.canFit(4)) {
            throw new AssertionError(
                    "Expected 4 guests to fit in a room with capacity 6");
        }

        if (room.canFit(7)) {
            throw new AssertionError(
                    "Expected 7 guests to not fit in a room with capacity 6");
        }
    }

    public static void testCheckIn() {
        Room room = new Room(3, 6, 40.00);

        room.checkIn();

        if (room.isAvailable()) {
            throw new AssertionError(
                    "Expected room to be unavailable after check-in");
        }
    }

    public static void testCheckOut() {
        Room room = new Room(4, 6, 40.00);

        room.checkIn();
        room.checkOut();

        if (!room.isAvailable()) {
            throw new AssertionError(
                    "Expected room to be available after checkout");
        }
    }
}