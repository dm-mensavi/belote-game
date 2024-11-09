package pubmanagement;

import utils.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PubEnvironment {
    // File paths for storing data
    private static final String DRINKS_FILE = "./data/drinks.txt";
    private static final String STAFF_FILE = "./data/staff.txt";
    private static final String CLIENTS_FILE = "./data/clients.txt";

    // Paths to default data files
    private static final String DEFAULT_DRINKS_FILE = "./data/default_drinks.txt";
    private static final String DEFAULT_STAFF_FILE = "./data/default_staff.txt";
    private static final String DEFAULT_CLIENTS_FILE = "./data/default_clients.txt";

    public static void initializePub(Scanner scanner) {
        // Reset data files to default content at the start of the game
        resetDataFilesToDefault();

        // Read drinks, staff, and clients from files
        List<Drink> drinks = loadDrinksFromFile(DRINKS_FILE);
        List<Human> staff = loadStaffFromFile(STAFF_FILE);
        List<Client> clients = loadClientsFromFile(CLIENTS_FILE, drinks);

        // Create a bar with the loaded staff
        Patronne marie = (Patronne) staff.stream().filter(h -> h instanceof Patronne).findFirst().orElse(null);
        if (marie == null) {
            System.out.println("Error: No patronne found in staff data.");
            return;
        }

        Bartender paul = (Bartender) staff.stream().filter(h -> h instanceof Bartender).findFirst().orElse(null);
        if (paul == null) {
            System.out.println("Error: No bartender found in staff data.");
            return;
        }

        // Find the supplier
        Supplier supplier = (Supplier) staff.stream().filter(h -> h instanceof Supplier).findFirst().orElse(null);
        if (supplier == null) {
            System.out.println("Error: No supplier found in staff data.");
        }

        Bar bar = new Bar("At Marie's Pub", marie, paul);

        // Add servers to the bar
        staff.stream()
                .filter(h -> h instanceof Server)
                .forEach(server -> bar.addServer((Server) server));


        // Start interactive gameplay
        System.out.println("Welcome to Pub Management Simulation!");
        boolean keepPlaying = true;

        while (keepPlaying) {
            System.out.println("\nPlease choose your role:");
            System.out.println("1. Patronne");
            System.out.println("2. Bartender");
            System.out.println("3. Server");
            System.out.println("4. Supplier");
            System.out.println("5. Client");
            System.out.println("6. Quit");

            int roleChoice = getIntInput(scanner, 1, 6);

            switch (roleChoice) {
                case 1 -> RoleActions.patronneActions(scanner, marie, bar, clients);
                case 2 -> RoleActions.bartenderActions(scanner, paul, bar, clients, drinks);
                case 3 -> RoleActions.serverActions(scanner, bar, clients, drinks);
                case 4 -> {
                    if (supplier != null) {
                        RoleActions.supplierActions(scanner, supplier, bar, drinks);
                    } else {
                        System.out.println("No supplier is available.");
                    }
                }
                case 5 -> RoleActions.clientActions(scanner, bar, clients, drinks);
                case 6 -> {
                    System.out.println("Thanks for playing! Goodbye.");
                    keepPlaying = false;
                    // Save data before exiting
                    saveDrinksToFile(drinks, DRINKS_FILE);
                    saveStaffToFile(staff, STAFF_FILE);
                    saveClientsToFile(clients, CLIENTS_FILE);
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to reset data files to default content at the start of the game
    private static void resetDataFilesToDefault() {
        try {
            // Copy default_drinks.txt to drinks.txt
            Files.copy(Paths.get(DEFAULT_DRINKS_FILE), Paths.get(DRINKS_FILE), StandardCopyOption.REPLACE_EXISTING);

            // Copy default_staff.txt to staff.txt
            Files.copy(Paths.get(DEFAULT_STAFF_FILE), Paths.get(STAFF_FILE), StandardCopyOption.REPLACE_EXISTING);

            // Copy default_clients.txt to clients.txt
            Files.copy(Paths.get(DEFAULT_CLIENTS_FILE), Paths.get(CLIENTS_FILE), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Data files have been reset to default content.");
        } catch (IOException e) {
            System.out.println("Error resetting data files: " + e.getMessage());
        }
    }

    // Method to load drinks from a file
    public static List<Drink> loadDrinksFromFile(String filePath) {
        List<Drink> drinks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 4)
                    continue;
                String name = data[0];
                double salePrice = Double.parseDouble(data[1]);
                double purchasePrice = Double.parseDouble(data[2]);
                int alcoholPoints = Integer.parseInt(data[3]);
                drinks.add(new Drink(name, salePrice, purchasePrice, alcoholPoints));
            }
        } catch (IOException e) {
            System.out.println("Error loading drinks from file: " + e.getMessage());
        }
        return drinks;
    }

    // Method to load staff from a file
    public static List<Human> loadStaffFromFile(String filePath) {
        List<Human> staff = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5)
                    continue;
                String firstName = data[0];
                String nickname = data[1];
                double wallet = Double.parseDouble(data[2]);
                String shout = data[3];
                String role = data[4];
                if (role.equals("PATRONNE")) {
                    staff.add(new Patronne(firstName, nickname, wallet, shout, "At Marie's Pub"));
                } else if (role.equals("BARTENDER")) {
                    staff.add(new Bartender(firstName, nickname, wallet, shout));
                } else if (role.equals("SERVER") && data.length >= 7) {
                    Gender gender = Gender.valueOf(data[5].toUpperCase());
                    int strengthOrCharm = Integer.parseInt(data[6]);
                    staff.add(new Server(firstName, nickname, wallet, shout, gender, strengthOrCharm));
                } else if (role.equals("SUPPLIER")) {
                    staff.add(new Supplier(firstName, nickname, wallet, shout));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading staff from file: " + e.getMessage());
        }
        return staff;
    }

    // Method to load clients from a file
    public static List<Client> loadClientsFromFile(String filePath, List<Drink> drinks) {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 8)
                    continue;
                String firstName = data[0];
                String nickname = data[1];
                double wallet = Double.parseDouble(data[2]);
                String shout = data[3];
                Gender gender = Gender.valueOf(data[4].toUpperCase());
                Drink favoriteDrink = drinks.stream().filter(d -> d.getName().equals(data[5])).findFirst().orElse(null);
                Drink backupDrink = drinks.stream().filter(d -> d.getName().equals(data[6])).findFirst().orElse(null);
                String clothingOrJewelry = data[7];
                clients.add(new Client(firstName, nickname, wallet, shout, gender, favoriteDrink, backupDrink,
                        clothingOrJewelry));
            }
        } catch (IOException e) {
            System.out.println("Error loading clients from file: " + e.getMessage());
        }
        return clients;
    }

    // Method to save drinks to a file
    public static void saveDrinksToFile(List<Drink> drinks, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Drink drink : drinks) {
                writer.write(drink.getName() + "," + drink.getSalePrice() + "," + drink.getPurchasePrice() + ","
                        + drink.getAlcoholPoints());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving drinks to file: " + e.getMessage());
        }
    }

    // Method to save staff to a file
    public static void saveStaffToFile(List<Human> staff, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Human human : staff) {
                if (human instanceof Patronne) {
                    writer.write(human.getFirstName() + "," + human.getNickname() + "," + human.getWallet() + ","
                            + human.getSignificantShout() + ",PATRONNE");
                } else if (human instanceof Bartender) {
                    writer.write(human.getFirstName() + "," + human.getNickname() + "," + human.getWallet() + ","
                            + human.getSignificantShout() + ",BARTENDER");
                } else if (human instanceof Server) {
                    Server server = (Server) human;
                    writer.write(server.getFirstName() + "," + server.getNickname() + "," + server.getWallet() + ","
                            + server.getSignificantShout() + ",SERVER," + server.getGender() + ","
                            + (server.getGender() == Gender.MALE ? server.getStrength() : server.getCharm()));
                } else if (human instanceof Supplier) {
                    writer.write(human.getFirstName() + "," + human.getNickname() + "," + human.getWallet() + ","
                            + human.getSignificantShout() + ",SUPPLIER");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving staff to file: " + e.getMessage());
        }
    }

    // Method to save clients to a file
    public static void saveClientsToFile(List<Client> clients, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Client client : clients) {
                writer.write(client.getFirstName() + "," + client.getNickname() + "," + client.getWallet() + ","
                        + client.getSignificantShout() + "," + client.getGender() + ","
                        + client.getFavoriteDrink().getName() + "," + client.getBackupDrink().getName() + ","
                        + client.getClothingOrJewelry());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving clients to file: " + e.getMessage());
        }
    }

    // Method to update a client in the database
    public static void updateClientInDatabase(Client client) {
        List<Drink> drinks = loadDrinksFromFile(DRINKS_FILE);
        List<Client> clients = loadClientsFromFile(CLIENTS_FILE, drinks);
        // Replace the old client data with the new one
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getNickname().equals(client.getNickname())) {
                clients.set(i, client);
                break;
            }
        }
        // Save updated clients list back to file
        saveClientsToFile(clients, CLIENTS_FILE);
    }

    // Method to update a staff member in the database
    public static void updateStaffInDatabase(Human staffMember) {
        List<Human> staff = loadStaffFromFile(STAFF_FILE);
        // Replace the old staff data with the new one
        for (int i = 0; i < staff.size(); i++) {
            if (staff.get(i).getNickname().equals(staffMember.getNickname())) {
                staff.set(i, staffMember);
                break;
            }
        }
        // Save updated staff list back to file
        saveStaffToFile(staff, STAFF_FILE);
    }

    // Method to update a drink in the database
    public static void updateDrinkInDatabase(Drink drink) {
        List<Drink> drinks = loadDrinksFromFile(DRINKS_FILE);
        // Replace the old drink data with the new one
        for (int i = 0; i < drinks.size(); i++) {
            if (drinks.get(i).getName().equals(drink.getName())) {
                drinks.set(i, drink);
                break;
            }
        }
        // Save updated drinks list back to file
        saveDrinksToFile(drinks, DRINKS_FILE);
    }

    // Method to remove a client from the database
    public static void removeClientFromDatabase(Client clientToRemove) {
        List<Drink> drinks = loadDrinksFromFile(DRINKS_FILE);
        List<Client> clients = loadClientsFromFile(CLIENTS_FILE, drinks);
        clients.removeIf(client -> client.getNickname().equals(clientToRemove.getNickname()));
        saveClientsToFile(clients, CLIENTS_FILE);
    }

    // Helper method to get integer input with validation
    public static int getIntInput(Scanner scanner, int min, int max) {
        if (min > max) {
            System.out.println("No valid options available.");
            return -1; // Indicate an invalid input
        }
        int input = -1;
        boolean valid = false;
        while (!valid) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } else if (scanner.hasNextLine()) {
                scanner.nextLine(); // Consume invalid input
                System.out.println("Invalid input. Please enter a number.");
            } else {
                System.out.println("No input available.");
                break;
            }
        }
        return input;
    }

    // Helper method to get double input with validation
    public static double getDoubleInput(Scanner scanner) {
        double input = -1;
        boolean valid = false;
        while (!valid) {
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (input >= 0) {
                    valid = true;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } else if (scanner.hasNextLine()) {
                scanner.nextLine(); // Consume invalid input
                System.out.println("Invalid input. Please enter a number.");
            } else {
                System.out.println("No input available.");
                break;
            }
        }
        return input;
    }
}
