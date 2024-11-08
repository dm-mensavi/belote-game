package pubmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the bar.
 */
public class Bar {
    private String name;
    private Patronne owner;
    private Bartender bartender;
    private List<Server> servers;
    private List<Client> clients;
    private List<Drink> stockDrinks; // List of drink types in stock
    private List<Integer> stockQuantities; // List of quantities corresponding to each drink
    private List<Table> tables; // Tables in the bar

    /**
     * Constructor for the Bar class.
     *
     * @param name      The name of the bar.
     * @param owner     The owner of the bar (Patronne).
     * @param bartender The bartender working at the bar.
     */
    public Bar(String name, Patronne owner, Bartender bartender) {
        this.name = name;
        this.owner = owner;
        this.bartender = bartender;
        this.servers = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.stockDrinks = new ArrayList<>();
        this.stockQuantities = new ArrayList<>();
        this.tables = new ArrayList<>();
        initializeTables();
    }

    // Initialize tables (for simplicity, let's assume there are 4 tables)
    private void initializeTables() {
        for (int i = 0; i < 4; i++) {
            tables.add(new Table());
        }
        System.out.println("The bar has been set up with 4 tables.");
    }

    // Getters
    public String getName() {
        return name;
    }

    public Patronne getOwner() {
        return owner;
    }

    public List<Table> getTables() {
        return tables;
    }

    public List<Client> getClients() {
        return clients;
    }

    // Methods to manage staff
    public void addServer(Server server) {
        servers.add(server);
        System.out.println(server.getNickname() + " is now working at the bar.");
    }

    public void removeServer(Server server) {
        servers.remove(server);
        System.out.println(server.getNickname() + " has left the bar.");
    }

    // Methods to manage clients
    public void addClient(Client client) {
        clients.add(client);
        System.out.println(client.getNickname() + " enters the bar.");
        seatClientAtTable(client); // Attempt to seat the client at a table upon entry
    }

    public void removeClient(Client client) {
        clients.remove(client);
        System.out.println(client.getNickname() + " leaves the bar.");
    }

    // Seat a client at a table if there is space available
    public boolean seatClientAtTable(Client client) {
        for (Table table : tables) {
            if (table.hasSpace()) {
                return table.seatClient(client);
            }
        }
        System.out.println("No tables available to seat " + client.getNickname() + ".");
        return false;
    }

    // Stock management: receiving stock
    public void receiveStock(Drink drink, int quantity) {
        int index = stockDrinks.indexOf(drink);
        if (index != -1) {
            // Drink already in stock, update quantity
            int currentQuantity = stockQuantities.get(index);
            stockQuantities.set(index, currentQuantity + quantity);
        } else {
            // New drink, add to stock
            stockDrinks.add(drink);
            stockQuantities.add(quantity);
        }
        System.out.println("Bar stock updated: " + drink.getName() + " now has " + getDrinkStock(drink) + " units.");
    }

    // Stock management: check current stock level for a specific drink
    public int getDrinkStock(Drink drink) {
        int index = stockDrinks.indexOf(drink);
        return index != -1 ? stockQuantities.get(index) : 0;
    }

    // Serving drinks
    public boolean serveDrink(Client client, Drink drink) {
        if (!client.canReceiveDrink()) {
            System.out.println(client.getNickname() + " is no longer allowed to be served.");
            return false; // Client cannot receive drinks
        }

        int currentStock = getDrinkStock(drink);
        if (currentStock > 0) {
            int index = stockDrinks.indexOf(drink);
            stockQuantities.set(index, currentStock - 1); // Reduce stock by 1
            bartender.serveDrink(client, drink);
            return true;
        } else {
            System.out.println("Sorry, " + drink.getName() + " is out of stock.");
            return false;
        }
    }

    // Offer a general round
    public void offerGeneralRound() {
        bartender.announceGeneralRound();
        owner.reactToGeneralRound();
        for (Server server : servers) {
            server.indicateBusy();
        }
        Client.expressSignificantCry(clients);
    }

    public Bartender getBartender() {
        return bartender;
    }

    // Get list of servers
    public List<Server> getServers() {
        return servers;
    }

    // Display stock levels
    public void displayStockLevels() {
        System.out.println("Current stock levels:");
        if (stockDrinks.size() == 0){
            System.out.println("No drinks in stock.");
            return;
        }
        for (int i = 0; i < stockDrinks.size(); i++) {
            String drinkName = stockDrinks.get(i).getName();
            int stockQuantity = stockQuantities.get(i);
            if (stockQuantity == 0) {
                System.out.println(drinkName + ": Out of stock");
            } else {
                System.out.println(drinkName + ": " + stockQuantity + " units");
            }
        }
    }

    /**
     * Method to get the available drinks in the bar.
     *
     * @return The list of available drinks.
     */
    public List<Drink> getAvailableDrinks() {
        List<Drink> availableDrinks = new ArrayList<>();
        for (int i = 0; i < stockDrinks.size(); i++) {
            if (stockQuantities.get(i) > 0) {
                availableDrinks.add(stockDrinks.get(i));
            }
        }
        return availableDrinks;
    }

public List<Drink> getFixedDrinks() {
    List<Drink> drinksToSupply = new ArrayList<>();
    List<Drink> availableDrinks = getAvailableDrinks(); // Assuming this method retrieves available drinks
    Random random = new Random();

    for (Drink drink : availableDrinks) {
        int quantity = random.nextInt(3) + 3; // Generates a quantity between 3 and 5
        Drink drinkToSupply = new Drink(drink.getName(), drink.getPurchasePrice(), drink.getSalePrice(), quantity);
        drinksToSupply.add(drinkToSupply);
    }

    return drinksToSupply;
}
    
}
