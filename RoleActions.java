
import utils.Gender;

import java.util.Scanner;

import pubmanagement.Bar;
import pubmanagement.Bartender;
import pubmanagement.Client;
import pubmanagement.Drink;
import pubmanagement.Patronne;
import pubmanagement.Server;

public class RoleActions {

    // Actions for Patronne
    public static void patronneActions(Scanner scanner, Patronne marie, Bar bar) {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("\nYou are the Patronne. What would you like to do?");
            System.out.println("1. Ban a client");
            System.out.println("2. Collect cash from the bartender");
            System.out.println("3. Stop serving a client");
            System.out.println("4. Quit to main menu");

            int action = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (action) {
                case 1 -> {
                    Client clientToBan = chooseClient(scanner, bar);
                    marie.banClient(clientToBan, bar);
                }
                case 2 -> marie.collectCashFromRegister(bar.getBartender(), 50.0);
                case 3 -> {
                    Client clientToStop = chooseClient(scanner, bar);
                    marie.stopServing(clientToStop);
                }
                case 4 -> keepGoing = false;  // Return to main menu
                default -> System.out.println("Invalid action, try again.");
            }
        }
    }

    // Actions for Bartender
    public static void bartenderActions(Scanner scanner, Bartender bartender, Bar bar) {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("\nYou are the Bartender. What would you like to do?");
            System.out.println("1. Serve a client");
            System.out.println("2. Announce a general round");
            System.out.println("3. Quit to main menu");

            int action = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (action) {
                case 1 -> {
                    Client chosenClient = chooseClient(scanner, bar);
                    Drink chosenDrink = chooseDrink(scanner);
                    bar.serveDrink(chosenClient, chosenDrink);
                }
                case 2 -> {
                    // bartender.announceGeneralRound();
                    bar.offerGeneralRound();
                }
                case 3 -> keepGoing = false;  // Return to main menu
                default -> System.out.println("Invalid action, try again.");
            }
        }
    }

    // Actions for Server
    public static void serverActions(Scanner scanner, Bar bar) {
        boolean keepGoing = true;

        System.out.println("\nYou are a Server. Choose a server:");
        System.out.println("1. Alice");
        System.out.println("2. John");
        System.out.println("3. Charlie");
        System.out.println("4. Diana");

        int serverChoice = scanner.nextInt();
        Server chosenServer = switch (serverChoice) {
            case 1 -> bar.getServers().get(0);
            case 2 -> bar.getServers().get(1);
            case 3 -> bar.getServers().get(2);
            case 4 -> bar.getServers().get(3);
            default -> bar.getServers().get(0);
        };

        while (keepGoing) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Take an order from a client");
            System.out.println("2. Serve a client");
            System.out.println("3. Quit to main menu");

            int action = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (action) {
                case 1 -> {
                    Client client = chooseClient(scanner, bar);
                    chosenServer.receiveOrder(client);
                }
                case 2 -> {
                    Client client = chooseClient(scanner, bar);
                    Drink drink = chooseDrink(scanner);
                    chosenServer.serve(client, drink);
                }
                case 3 -> keepGoing = false;  // Return to main menu
                default -> System.out.println("Invalid action, try again.");
            }
        }
    }

    // Actions for Client
    public static void clientActions(Scanner scanner, Bar bar) {
        boolean keepGoing = true;

        System.out.println("\nYou are a Client. Choose a client:");
        Client chosenClient = chooseClient(scanner, bar);

        while (keepGoing) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Order a drink");
            System.out.println("2. Pay for a drink");
            System.out.println("3. Change gender");
            System.out.println("4. Quit to main menu");

            int action = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (action) {
                case 1 -> {
                    Drink chosenDrink = chooseDrink(scanner);
                    chosenClient.receiveDrink(chosenDrink);
                }
                case 2 -> {
                    System.out.println("Enter the amount to pay:");
                    double amount = scanner.nextDouble();
                    chosenClient.pay(amount);
                }
                case 3 -> {
                    System.out.println("Choose your new gender:");
                    System.out.println("1. Male");
                    System.out.println("2. Female");
                    int genderChoice = scanner.nextInt();
                    Gender newGender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;
                    System.out.println("Enter new clothing/jewelry description:");
                    scanner.nextLine();  // Consume newline
                    String newClothing = scanner.nextLine();
                    chosenClient.changeGender(newGender, newClothing);
                    chosenClient.introduce();  // Verify the change
                }
                case 4 -> keepGoing = false;  // Return to main menu
                default -> System.out.println("Invalid action, try again.");
            }
        }
    }

    // Helper method to choose a client
    public static Client chooseClient(Scanner scanner, Bar bar) {
        System.out.println("Choose a client:");
        System.out.println("1. Johnny");
        System.out.println("2. Em");
        System.out.println("3. Mike");
        System.out.println("4. Sophie");
        int clientChoice = scanner.nextInt();
        return switch (clientChoice) {
            case 1 -> bar.getClients().get(0);
            case 2 -> bar.getClients().get(1);
            case 3 -> bar.getClients().get(2);
            case 4 -> bar.getClients().get(3);
            default -> bar.getClients().get(0);
        };
    }

    // Helper method to choose a drink
    public static Drink chooseDrink(Scanner scanner) {
        System.out.println("Choose a drink:");
        System.out.println("1. Beer");
        System.out.println("2. Wine");
        System.out.println("3. Whiskey");
        System.out.println("4. Vodka");
        int drinkChoice = scanner.nextInt();
        return switch (drinkChoice) {
            case 1 -> new Drink("Beer", 5.00, 2.00, 3);
            case 2 -> new Drink("Wine", 7.00, 3.00, 4);
            case 3 -> new Drink("Whiskey", 10.00, 4.00, 6);
            case 4 -> new Drink("Vodka", 8.00, 3.50, 5);
            default -> new Drink("Beer", 5.00, 2.00, 3);
        };
    }
}
