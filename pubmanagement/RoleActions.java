package pubmanagement;

import utils.*;

import java.util.Scanner;
import java.util.List;

public class RoleActions {

    // Actions for Patronne
    public static void patronneActions(Scanner scanner, Patronne marie, Bar bar, List<Client> clients) {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("\nYou are the Patronne. What would you like to do?");
            System.out.println("1. Ban a client");
            System.out.println("2. Collect cash from the bartender");
            System.out.println("3. Stop serving a client");
            System.out.println("4. Offer a drink to someone");
            System.out.println("5. Quit to main menu");

            int action = PubEnvironment.getIntInput(scanner, 1, 5);

            switch (action) {
                case 1 -> {
                    Client clientToBan = chooseClient(scanner, clients);
                    if (clientToBan != null) {
                        marie.banClient(clientToBan, bar);
                        clients.remove(clientToBan); // Remove from clients list
                        PubEnvironment.removeClientFromDatabase(clientToBan); // Remove from database
                        System.out.println(clientToBan.getNickname() + " has been removed from the bar.");
                    }
                }
                case 2 -> marie.collectCashFromRegister(bar.getBartender(), 50.0);
                case 3 -> {
                    Client clientToStop = chooseClient(scanner, clients);
                    if (clientToStop != null) {
                        marie.stopServing(clientToStop);
                    }
                }
                case 4 -> {
                    System.out.println("Who would you like to offer a drink to?");
                    Client clientToOffer = chooseClient(scanner, clients);
                    if (clientToOffer != null) {
                        Drink drinkToOffer = chooseDrink(scanner, bar.getAvailableDrinks());
                        if (drinkToOffer != null) {
                            marie.offerDrink(clientToOffer, drinkToOffer);
                        }
                    }
                }
                case 5 -> keepGoing = false; // Return to main menu
                default -> System.out.println("Invalid action, try again.");
            }

        }
    }

    // Actions for Bartender
    public static void bartenderActions(Scanner scanner, Bartender bartender, Bar bar, List<Client> clients,
            List<Drink> drinks) {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("\nYou are the Bartender. What would you like to do?");
            System.out.println("1. Serve a client");
            System.out.println("2. Announce a general round");
            System.out.println("3. Manage stock");
            System.out.println("4. Quit to main menu");

            int action = PubEnvironment.getIntInput(scanner, 1, 4);

            switch (action) {
                case 1 -> {
                    Client chosenClient = chooseClient(scanner, clients);
                    if (chosenClient != null) {
                        Drink chosenDrink = chooseDrink(scanner, bar.getAvailableDrinks());
                        if (chosenDrink != null) {
                            bar.serveDrink(chosenClient, chosenDrink);
                        }
                    }
                }
                case 2 -> bar.offerGeneralRound();
                case 3 -> manageStock(scanner, bar, drinks);
                case 4 -> keepGoing = false; // Return to main menu
                default -> System.out.println("Invalid action, try again.");
            }
        }
    }

  // Actions for Server
public static void serverActions(Scanner scanner, Bar bar, List<Client> clients, List<Drink> drinks) {
    boolean keepGoing = true;

    List<Server> servers = bar.getServers();

    while (true) {
        System.out.println("\nYou are a Server. Choose an option:");
        System.out.println("1. Select an existing server");
        System.out.println("2. Add a custom server");
        System.out.println("3. Quit to main menu");

        int serverChoice = PubEnvironment.getIntInput(scanner, 1, 3);
        if (serverChoice == -1) return;

        Server chosenServer = null;

        if (serverChoice == 1) {
            if (servers.isEmpty()) {
                System.out.println("There are no servers available.");
                return;
            }

            System.out.println("\nChoose a server from the available list:");
            for (int i = 0; i < servers.size(); i++) {
                System.out.println((i + 1) + ". " + servers.get(i).getFirstName());
            }
            int serverIndex = PubEnvironment.getIntInput(scanner, 1, servers.size());
            if (serverIndex == -1) return; // Invalid input
            chosenServer = servers.get(serverIndex - 1);

        } else if (serverChoice == 2) {
            // Create a custom server
            System.out.print("Enter server's first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter server's nickname: ");
            String nickname = scanner.nextLine();
            System.out.print("Enter server's wallet balance: ");
            double wallet = PubEnvironment.getDoubleInput(scanner);

            chosenServer = new Server(firstName, nickname, wallet);
            servers.add(chosenServer);  // Add the custom server to the list
            System.out.println("Custom server added: " + chosenServer.getFirstName());
        } else if (serverChoice == 3) {
            return; // Return to main menu
        }

        if (chosenServer != null) {
            while (keepGoing) {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. View Profile");
                System.out.println("2. Take an order from a client");
                System.out.println("3. Serve a client");
                System.out.println("4. Quit to main menu");

                int action = PubEnvironment.getIntInput(scanner, 1, 4);

                switch (action) {
                    case 1 -> {
                        // View server profile
                        System.out.println("\nServer Profile:");
                        System.out.println("Name: " + chosenServer.getFirstName());
                        System.out.println("Nickname: " + chosenServer.getNickname());
                        System.out.println("Wallet: " + chosenServer.getWallet());
                    }
                    case 2 -> {
                        // Take order from a client
                        Client client = chooseClient(scanner, clients);
                        if (client != null) {
                            chosenServer.receiveOrder(client);
                        }
                    }
                    case 3 -> {
                        // Serve a client
                        Client client = chooseClient(scanner, clients);
                        if (client != null) {
                            Drink drink = chooseDrink(scanner, bar.getAvailableDrinks());
                            if (drink != null) {
                                chosenServer.serve(client, drink);
                            }
                        }
                    }
                    case 4 -> keepGoing = false; // Return to main menu
                    default -> System.out.println("Invalid action, try again.");
                }
            }
        }
    }
}


// Actions for Supplier
public static void supplierActions(Scanner scanner, Supplier supplier, Bar bar) {
    boolean keepGoing = true;

    while (keepGoing) {
        System.out.println("\nYou are the Supplier. What would you like to do?");
        System.out.println("1. Deliver drinks to the bar");
        System.out.println("2. Get paid by the patronne");
        System.out.println("3. Check amount owed");
        System.out.println("4. Quit to main menu");

        int action = PubEnvironment.getIntInput(scanner, 1, 4);

        switch (action) {
            case 1 -> {
                // Deliver fixed quantity of drinks to the bar
                supplier.deliverDrinks(bar);
            }
            case 2 -> {
                // Get paid by the patronne
                supplier.getPaid(bar.getOwner());
            }
            case 3 -> {
                // Check the amount owed by the bar
                System.out.println("The bar owes you " + supplier.getAmountOwed() + " euros.");
            }
            case 4 -> keepGoing = false; // Exit to main menu
            default -> System.out.println("Invalid action, try again.");
        }
    }
}


// Actions for Client
public static void clientActions(Scanner scanner, Bar bar, List<Client> clients, List<Drink> drinks) {
    boolean keepGoing = true;

    System.out.println("\nYou are a Client. Choose a client or create a new one:");
    System.out.println("0. Create a new client");
    for (int i = 0; i < clients.size(); i++) {
        System.out.println((i + 1) + ". " + clients.get(i).getFirstName());
    }
    int clientChoice = PubEnvironment.getIntInput(scanner, 0, clients.size());
    Client chosenClient;

    if (clientChoice == -1)
        return; // Invalid input

    if (clientChoice == 0) {
        chosenClient = createNewClient(scanner, drinks);
        if (chosenClient != null) {
            clients.add(chosenClient);
            bar.addClient(chosenClient);
        } else {
            System.out.println("Failed to create a new client.");
            return;
        }
    } else {
        chosenClient = clients.get(clientChoice - 1);
    }

    while (keepGoing) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View Profile");
        System.out.println("2. Order a drink");
        System.out.println("3. Pay for a drink");
        System.out.println("4. Change gender");
        System.out.println("5. Quit to main menu");

        int action = PubEnvironment.getIntInput(scanner, 1, 5);

        switch (action) {
            case 1 -> {
                viewClientProfile(chosenClient);
            }
            case 2 -> {
                Drink chosenDrink = chooseDrink(scanner, bar.getAvailableDrinks());
                if (chosenDrink != null) {
                    chosenClient.receiveDrink(chosenDrink);
                    // Update client's wallet in the file
                    PubEnvironment.updateClientInDatabase(chosenClient);
                }
            }
            case 3 -> {
                System.out.println("Enter the amount to pay:");
                double amount = PubEnvironment.getDoubleInput(scanner);
                chosenClient.pay(amount);
                // Update client's wallet in the file
                PubEnvironment.updateClientInDatabase(chosenClient);
            }
            case 4 -> {
                System.out.println("Choose your new gender:");
                System.out.println("1. Male");
                System.out.println("2. Female");
                int genderChoice = PubEnvironment.getIntInput(scanner, 1, 2);
                Gender newGender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;
                System.out.print("Enter new clothing/jewelry description: ");
                String newClothing = scanner.nextLine();
                if (newClothing.isEmpty())
                    newClothing = (newGender == Gender.MALE) ? "T-shirt" : "Necklace";
                chosenClient.changeGender(newGender, newClothing);
                chosenClient.introduce(); // Verify the change
                // Update client in the database
                PubEnvironment.updateClientInDatabase(chosenClient);
            }
            case 5 -> keepGoing = false; // Return to main menu
            default -> System.out.println("Invalid action, try again.");
        }
    }
}

// Method to view client profile
private static void viewClientProfile(Client client) {
    System.out.println("\n--- Client Profile ---");
    System.out.println("Name: " + client.getFirstName() + " " + client.getNickname());
    System.out.println("Gender: " + client.getGender());
    System.out.println("Balance: $" + client.getWallet());
    System.out.println("Favorite Drink: " + (client.getFavoriteDrink() != null ? client.getFavoriteDrink().getName() : "None"));
    System.out.println("Backup Drink: " + (client.getBackupDrink() != null ? client.getBackupDrink().getName() : "None"));
    System.out.println("Significant Shout: " + client.getSignificantShout());
    System.out.println("Clothing/Jewelry: " + client.getClothingOrJewelry());
    System.out.println("----------------------\n");
}

    // Helper method to choose a client
    public static Client chooseClient(Scanner scanner, List<Client> clients) {
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
            return null;
        }
        System.out.println("Choose a client:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i).getFirstName());
        }
        int clientChoice = PubEnvironment.getIntInput(scanner, 1, clients.size());
        if (clientChoice == -1)
            return null; // Invalid input
        return clients.get(clientChoice - 1);
    }

    // Helper method to choose a drink
    public static Drink chooseDrink(Scanner scanner, List<Drink> drinks) {
        if (drinks.isEmpty()) {
            System.out.println("No drinks available.");
            return null;
        }
        System.out.println("Choose a drink:");
        for (int i = 0; i < drinks.size(); i++) {
            System.out.println((i + 1) + ". " + drinks.get(i).getName() + " - $" + drinks.get(i).getSalePrice());
        }
        int drinkChoice = PubEnvironment.getIntInput(scanner, 1, drinks.size());
        if (drinkChoice == -1)
            return null; // Invalid input
        return drinks.get(drinkChoice - 1);
    }

    // Helper method to create a new client
    private static Client createNewClient(Scanner scanner, List<Drink> drinks) {
        System.out.println("Creating a new client.");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        if (firstName.isEmpty())
            firstName = "Anonymous";

        System.out.print("Enter nickname: ");
        String nickname = scanner.nextLine();
        if (nickname.isEmpty())
            nickname = "Anon";

        System.out.print("Enter wallet balance: ");
        double wallet = PubEnvironment.getDoubleInput(scanner);

        System.out.print("Enter significant shout: ");
        String shout = scanner.nextLine();
        if (shout.isEmpty())
            shout = "Cheers!";

        System.out.println("Choose gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        int genderChoice = PubEnvironment.getIntInput(scanner, 1, 2);
        Gender gender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;

        System.out.print("Enter clothing or jewelry description: ");
        String clothingOrJewelry = scanner.nextLine();
        if (clothingOrJewelry.isEmpty())
            clothingOrJewelry = (gender == Gender.MALE) ? "T-shirt" : "Necklace";

        System.out.println("Choose favorite drink:");
        Drink favoriteDrink = chooseDrink(scanner, drinks);

        System.out.println("Choose backup drink:");
        Drink backupDrink = chooseDrink(scanner, drinks);

        if (favoriteDrink == null || backupDrink == null) {
            System.out.println("Failed to choose drinks.");
            return null;
        }

        return new Client(firstName, nickname, wallet, shout, gender, favoriteDrink, backupDrink, clothingOrJewelry);
    }

    // Helper method to manage stock
    private static void manageStock(Scanner scanner, Bar bar, List<Drink> drinks) {
        System.out.println("Managing stock.");
        System.out.println("1. View stock levels");
        System.out.println("2. Add stock");
        int choice = PubEnvironment.getIntInput(scanner, 1, 2);

        switch (choice) {
            case 1 -> {
                bar.displayStockLevels();
            }
            case 2 -> {
                System.out.println("Choose a drink to add stock:");
                Drink drink = chooseDrink(scanner, drinks);
                if (drink != null) {
                    System.out.print("Enter quantity to add: ");
                    int quantity = PubEnvironment.getIntInput(scanner, 1, Integer.MAX_VALUE);
                    bar.receiveStock(drink, quantity);
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}
