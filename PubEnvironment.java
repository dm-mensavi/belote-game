
import utils.*;
import java.util.Scanner;

import pubmanagement.Bar;
import pubmanagement.Bartender;
import pubmanagement.Client;
import pubmanagement.Drink;
import pubmanagement.Human;
import pubmanagement.Patronne;
import pubmanagement.Server;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class PubEnvironment {
    // File paths for storing data
    private static final String DRINKS_FILE = "./data/drinks.txt";
    private static final String STAFF_FILE = "./data/staff.txt";
    private static final String CLIENTS_FILE = "./data/clients.txt";

    public static void initializePub() {
        Scanner scanner = new Scanner(System.in);

        // Read drinks, staff, and clients from files
        List<Drink> drinks = loadDrinksFromFile(DRINKS_FILE);
        List<Human> staff = loadStaffFromFile(STAFF_FILE);
        List<Client> clients = loadClientsFromFile(CLIENTS_FILE, drinks);

        // Create a bar with the loaded staff
        Patronne marie = (Patronne) staff.stream().filter(h -> h instanceof Patronne).findFirst().orElse(null);
        Bartender paul = (Bartender) staff.stream().filter(h -> h instanceof Bartender).findFirst().orElse(null);
        Bar bar = new Bar("At Marie's Pub", marie, paul);

        // Add servers to the bar
        staff.stream()
            .filter(h -> h instanceof Server)
            .forEach(server -> bar.addServer((Server) server));

        // Add clients to the bar
        clients.forEach(bar::addClient);

        // Start interactive gameplay
        System.out.println("Welcome to Pub Management Simulation!");
        boolean keepPlaying = true;

        while (keepPlaying) {
            System.out.println("Please choose your role:");
            System.out.println("1. Patronne");
            System.out.println("2. Bartender");
            System.out.println("3. Server");
            System.out.println("4. Client");
            System.out.println("5. Quit");

            int roleChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (roleChoice) {
                case 1 -> RoleActions.patronneActions(scanner, marie, bar);
                case 2 -> RoleActions.bartenderActions(scanner, paul, bar);
                case 3 -> RoleActions.serverActions(scanner, bar);
                case 4 -> RoleActions.clientActions(scanner, bar);
                case 5 -> {
                    System.out.println("Thanks for playing! Goodbye.");
                    keepPlaying = false;
                    saveDrinksToFile(drinks, DRINKS_FILE);
                    saveStaffToFile(staff, STAFF_FILE);
                    saveClientsToFile(clients, CLIENTS_FILE);  // Save updated data
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }

    // Method to load drinks from a file
    public static List<Drink> loadDrinksFromFile(String filePath) {
        List<Drink> drinks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
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
                String firstName = data[0];
                String nickname = data[1];
                double wallet = Double.parseDouble(data[2]);
                String shout = data[3];
                String role = data[4];
                if (role.equals("PATRONNE")) {
                    staff.add(new Patronne(firstName, nickname, wallet, shout, "At Marie's Pub"));
                } else if (role.equals("BARTENDER")) {
                    staff.add(new Bartender(firstName, nickname, wallet, shout));
                } else if (role.equals("SERVER")) {
                    Gender gender = Gender.valueOf(data[5].toUpperCase());
                    int strengthOrCharm = Integer.parseInt(data[6]);
                    staff.add(new Server(firstName, nickname, wallet, shout, gender, strengthOrCharm));
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
                String firstName = data[0];
                String nickname = data[1];
                double wallet = Double.parseDouble(data[2]);
                String shout = data[3];
                Gender gender = Gender.valueOf(data[4].toUpperCase());
                Drink favoriteDrink = drinks.stream().filter(d -> d.getName().equals(data[5])).findFirst().orElse(null);
                Drink backupDrink = drinks.stream().filter(d -> d.getName().equals(data[6])).findFirst().orElse(null);
                String clothingOrJewelry = data[7];
                clients.add(new Client(firstName, nickname, wallet, shout, gender, favoriteDrink, backupDrink, clothingOrJewelry));
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
                writer.write(drink.getName() + "," + drink.getSalePrice() + "," + drink.getPurchasePrice() + "," + drink.getAlcoholPoints());
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
                    writer.write(human.getFirstName() + "," + human.getNickname() + "," + human.getWallet() + "," + human.getSignificantShout() + ",PATRONNE");
                } else if (human instanceof Bartender) {
                    writer.write(human.getFirstName() + "," + human.getNickname() + "," + human.getWallet() + "," + human.getSignificantShout() + ",BARTENDER");
                } else if (human instanceof Server) {
                    Server server = (Server) human;
                    writer.write(server.getFirstName() + "," + server.getNickname() + "," + server.getWallet() + "," + server.getSignificantShout() + "," + server.getGender() + "," + server.getStrength());
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
                writer.write(client.getFirstName() + "," + client.getNickname() + "," + client.getWallet() + "," + client.getSignificantShout() + "," + client.getGender() + "," + client.getFavoriteDrink().getName() + "," + client.getBackupDrink().getName() + "," + client.getClothingOrJewelry());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving clients to file: " + e.getMessage());
        }
    }
}
