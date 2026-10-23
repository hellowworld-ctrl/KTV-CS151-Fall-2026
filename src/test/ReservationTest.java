package test;

import reservation.Reservation;
import reservation.Room;

public class ReservationTest {

    public static void main(String[] args) {

        testConfirmReservation();
        testTooManyGuests();
        testExtendReservation();
        testCalculateTotal();
        testCancelReservation();

        System.out.println("All Reservation tests passed!");
    }

    public static void testConfirmReservation() {
        Room room = new Room(1, 6, 40.00);
        Reservation reservation = new Reservation("Sky", room, 4, 2);

        boolean result = reservation.confirmReservation();

        if (!result) {
            throw new AssertionError(
                    "Expected reservation to be confirmed");
        }

        if (!reservation.isActive()) {
            throw new AssertionError(
                    "Expected reservation to be active");
        }

        if (room.isAvailable()) {
            throw new AssertionError(
                    "Expected room to be unavailable after confirmation");
        }
    }

    public static void testTooManyGuests() {
        Room room = new Room(2, 4, 30.00);
        Reservation reservation = new Reservation("Sky", room, 6, 2);

        boolean result = reservation.confirmReservation();

        if (result) {
            throw new AssertionError(
                    "Expected reservation to fail when there are too many guests");
        }

        if (reservation.isActive()) {
            throw new AssertionError(
                    "Expected reservation to remain inactive");
        }
    }

    public static void testExtendReservation() {
        Room room = new Room(3, 6, 40.00);
        Reservation reservation = new Reservation("Sky", room, 4, 2);

        reservation.confirmReservation();
        reservation.extendReservation(1);

        if (reservation.getHours() != 3) {
            throw new AssertionError(
                    "Expected reservation to be 3 hours");
        }
    }

    public static void testCalculateTotal() {
        Room room = new Room(4, 6, 40.00);
        Reservation reservation = new Reservation("Sky", room, 4, 2);

        double total = reservation.calculateTotal();

        if (total != 80.00) {
            throw new AssertionError(
                    "Expected total to be $80.00, but got $" + total);
        }
    }

    public static void testCancelReservation() {
        Room room = new Room(5, 6, 40.00);
        Reservation reservation = new Reservation("Sky", room, 4, 2);

        reservation.confirmReservation();
        reservation.cancelReservation();

        if (reservation.isActive()) {
            throw new AssertionError(
                    "Expected reservation to be inactive after cancellation");
        }

        if (!room.isAvailable()) {
            throw new AssertionError(
                    "Expected room to be available after cancellation");
        }
    }
}
