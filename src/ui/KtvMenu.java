package ui;

import java.util.ArrayList;
import java.util.Scanner;

import customer.Customer;
import ktv.KTVSystem;
import menu.Beverage;
import menu.FoodItem;
import reservation.Room;

// text menu for the ktv
// this is the only Scanner in the repo
public class KtvMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static KTVSystem ktv = new KTVSystem("KTV");

    public static void main(String[] args) {
        FoodItem.loadKtvFood();
        Beverage.loadKtvDrinks();
        System.out.println("Type EXIT in all caps at any prompt to quit");

        boolean running = true;
        while (running) {
            int choice = openMenu("Main Menu", new String[] {
                    "Customer", "Food", "Drinks", "Room", "Reservation"
            }, "Exit");
            if (choice == 1) {
                customerMenu();
            } else if (choice == 2) {
                foodMenu();
            } else if (choice == 3) {
                drinkMenu();
            } else if (choice == 4) {
                roomMenu();
            } else if (choice == 5) {
                reservationMenu();
            } else {
                running = false;
            }
        }
        System.out.println("goodbye");
    }

    // prints a numbered menu and returns the choice
    private static int openMenu(String title, String[] choices, String last) {
        System.out.println();
        System.out.println(title);
        System.out.println("-----------------");
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ". " + choices[i]);
        }
        int lastNumber = choices.length + 1;
        System.out.println(lastNumber + ". " + last);
        return readChoice(1, lastNumber);
    }

    // every prompt comes through here, so EXIT works anywhere
    private static String readLine() {
        String line = scanner.nextLine().trim();
        if (line.equals("EXIT")) {
            System.out.println("Thanks for checking out the KTV");
            System.exit(0);
        }
        return line;
    }

    private static String ask(String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    private static int askInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(ask(prompt));
            } catch (NumberFormatException e) {
                System.out.println("enter a whole number");
            }
        }
    }

    // keeps asking until the number is in range
    private static int readChoice(int min, int max) {
        while (true) {
            System.out.print("Enter a choice (" + min + " - " + max + "): ");
            try {
                int choice = Integer.parseInt(readLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // not a number, ask again
            }1
            System.out.println("enter a number from " + min + " to " + max);
        }
    }

    private static void customerMenu() {
        String[] choices = new String[] {
                "Add a customer", "Show all customers", "Find a customer",
                "Show an ID", "Book a room", "Order a drink", "Pay the tab"
        };
        boolean back = false;
        while (!back) {
            int choice = openMenu("Customer Menu", choices, "Back to main menu");
            if (choice == 1) {
                ktv.addCustomer(new Customer(ask("Name: "), ask("Phone number: "), askInt("Age: ")));
            } else if (choice == 2) {
                showAll(ktv.getCustomers(), "no customers yet");
            } else if (choice == 3) {
                Customer customer = ktv.findCustomer(ask("Customer name: "));
                if (customer == null) {
                    System.out.println("could not find that customer");
                } else {
                    System.out.println(customer);
                }
            } else if (choice == 5) {
                ktv.makeReservation(ktv.findCustomer(ask("Customer name: ")),
                        ktv.findRoom(askInt("Room id: ")),
                        askInt("Guests: "), askInt("Hours: "));
            } else if (choice == choices.length + 1) {
                back = true;
            } else {
                System.out.println("not filled in yet");
            }
        }
    }

    private static void foodMenu() {
        String[] choices = new String[] {
                "Show the food menu", "Order for a group", "Make a dish spicier"
        };
        boolean back = false;
        while (!back) {
            int choice = openMenu("Food Menu", choices, "Back to main menu");
            if (choice == 1) {
                showAll(FoodItem.getAllFood(), "no food yet");
            } else if (choice == choices.length + 1) {
                back = true;
            } else {
                System.out.println("not filled in yet");
            }
        }
    }

    private static void drinkMenu() {
        String[] choices = new String[] {
                "Show the drink menu", "Order a drink", "Buy a bucket",
                "Start happy hour"
        };
        boolean back = false;
        while (!back) {
            int choice = openMenu("Drink Menu", choices, "Back to main menu");
            if (choice == 1) {
                showAll(Beverage.getAllDrinks(), "no drinks yet");
            } else if (choice == choices.length + 1) {
                back = true;
            } else {
                System.out.println("not filled in yet");
            }
        }
    }

    private static void roomMenu() {
        String[] choices = new String[] {
                "Add a room", "Show all rooms", "Check the cost"
        };
        boolean back = false;
        while (!back) {
            int choice = openMenu("Room Menu", choices, "Back to main menu");
            if (choice == 1) {
                ktv.addRoom(new Room(askInt("Room id: "), askInt("Capacity: "),
                        askInt("Hourly rate: ")));
            } else if (choice == 2) {
                showAll(ktv.getRooms(), "no rooms yet");
            } else if (choice == 3) {
                Room room = ktv.findRoom(askInt("Room id: "));
                if (room == null) {
                    System.out.println("could not find that room");
                } else {
                    System.out.println("$" + room.calculateCost(askInt("Hours: ")));
                }
            } else {
                back = true;
            }
        }
    }

    private static void reservationMenu() {
        String[] choices = new String[] {
                "Show a reservation", "Extend a stay", "Cancel a reservation"
        };
        boolean back = false;
        while (!back) {
            int choice = openMenu("Reservation Menu", choices, "Back to main menu");
            if (choice == choices.length + 1) {
                back = true;
            } else {
                Customer customer = ktv.findCustomer(ask("Customer name: "));
                if (customer == null || customer.getReservation() == null) {
                    System.out.println("no reservation");
                } else if (choice == 1) {
                    System.out.println(customer.getReservation());
                } else if (choice == 2) {
                    customer.extendStay(askInt("Extra hours: "));
                } else {
                    customer.cancelBooking();
                }
            }
        }
    }

    private static void showAll(ArrayList<?> items, String empty) {
        if (items.size() == 0) {
            System.out.println(empty);
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i));
        }
    }
}
