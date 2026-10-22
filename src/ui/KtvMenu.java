package ui;

import java.util.Scanner;

import customer.Customer;
import ktv.KTVSystem;
import menu.Beverage;
import menu.FoodItem;
import reservation.Reservation;
import reservation.Room;

public class KtvMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static KTVSystem ktv = new KTVSystem("KTV");

    public static void main(String[] args) {

        FoodItem.loadKtvFood();
        Beverage.loadKtvDrinks();

        boolean running = true;

        while (running) {

            showMenu();

            int choice = askInt("Enter a choice: ");

            if (choice == 1) {
                addCustomer();

            } else if (choice == 2) {
                addRoom();

            } else if (choice == 3) {
                makeReservation();

            } else if (choice == 4) {
                viewReservation();

            } else if (choice == 5) {
                viewMenu();

            } else if (choice == 6) {
                orderDrink();

            } else if (choice == 7) {
                extendReservation();

            } else if (choice == 8) {
                cancelReservation();

            } else if (choice == 9) {
                payTab();

            } else if (choice == 10) {
                running = false;

            } else {
                System.out.println("Please enter a number from 1 to 10.");
            }
        }

        System.out.println("Goodbye!");
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("KTV SYSTEM");
        System.out.println("-----------------");
        System.out.println("1. Add customer");
        System.out.println("2. Add room");
        System.out.println("3. Make reservation");
        System.out.println("4. View reservation");
        System.out.println("5. View menu");
        System.out.println("6. Order drink");
        System.out.println("7. Extend reservation");
        System.out.println("8. Cancel reservation");
        System.out.println("9. Pay tab");
        System.out.println("10. Exit");
    }

    private static void addCustomer() {
        String name = ask("Name: ");
        String phone = ask("Phone number: ");
        int age = askInt("Age: ");

        Customer customer = new Customer(name, phone, age);

        if (ktv.addCustomer(customer)) {
            System.out.println("Customer added.");
        }
    }

    private static void addRoom() {
        int roomId = askInt("Room ID: ");
        int capacity = askInt("Capacity: ");
        double hourlyRate = askDouble("Hourly rate: ");

        Room room = new Room(roomId, capacity, hourlyRate);

        if (ktv.addRoom(room)) {
            System.out.println("Room added.");
        }
    }

    private static void makeReservation() {
        String name = ask("Customer name: ");
        int roomId = askInt("Room ID: ");
        int guests = askInt("Number of guests: ");
        int hours = askInt("Hours: ");

        Customer customer = ktv.findCustomer(name);
        Room room = ktv.findRoom(roomId);

        Reservation reservation =
                ktv.makeReservation(customer, room, guests, hours);

        if (reservation != null) {
            System.out.println("Reservation confirmed!");
            System.out.println(reservation);
        }
    }

    private static void viewReservation() {
        Customer customer =
                ktv.findCustomer(ask("Customer name: "));

        if (customer == null || customer.getReservation() == null) {
            System.out.println("No reservation found.");
            return;
        }

        System.out.println(customer.getReservation());
    }

    private static void viewMenu() {

        System.out.println();
        System.out.println("FOOD");
        System.out.println("-----------------");

        for (FoodItem food : FoodItem.getAllFood()) {
            System.out.println(food);
        }

        System.out.println();
        System.out.println("DRINKS");
        System.out.println("-----------------");

        for (Beverage drink : Beverage.getAllDrinks()) {
            System.out.println(drink);
        }
    }

    private static void orderDrink() {

        Customer customer =
                ktv.findCustomer(ask("Customer name: "));

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        String drinkName = ask("Drink: ");
        Beverage drink = Beverage.findDrink(drinkName);

        int amount = askInt("How many: ");

        customer.orderDrink(drink, amount);
    }

    private static void extendReservation() {

        Customer customer =
                ktv.findCustomer(ask("Customer name: "));

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        int hours = askInt("Additional hours: ");

        if (customer.extendStay(hours)) {
            System.out.println("Reservation extended.");
            System.out.println(customer.getReservation());
        }
    }

    private static void cancelReservation() {

        Customer customer =
                ktv.findCustomer(ask("Customer name: "));

        if (ktv.cancelReservation(customer)) {
            System.out.println("Reservation cancelled.");
        }
    }

    private static void payTab() {

        Customer customer =
                ktv.findCustomer(ask("Customer name: "));

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println(
                "Current tab: $" +
                String.format("%.2f", customer.amountDue()));

        double amount = askDouble("Payment amount: ");

        if (customer.pay(amount) > 0) {
            System.out.println(
                    "Remaining tab: $" +
                    String.format("%.2f", customer.amountDue()));
        }
    }

    private static String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int askInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(ask(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double askDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(ask(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}