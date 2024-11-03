package pubmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the bar.
 */
public class Bar {
    private String name;
    private Patronne owner;
    private Bartender bartender;
    private List<Server> servers;
    private List<Client> clients;
    private Map<Drink, Integer> stock;  // Key: Drink, Value: Quantity
    private List<Table> tables;  // Tables in the bar

    /**
     * Constructor for the Bar class.
     *
     * @param name    The name of the bar.
     * @param owner   The owner of the bar (Patronne).
     * @param bartender The bartender working at the bar.
     */
    public Bar(String name, Patronne owner, Bartender bartender) {
        this.name = name;
        this.owner = owner;
        this.bartender = bartender;
        this.servers = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.stock = new HashMap<>();
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
        seatClientAtTable(client);  // Attempt to seat the client at a table upon entry
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
        stock.put(drink, stock.getOrDefault(drink, 0) + quantity);  // Add or update stock
        System.out.println("Received " + quantity + " units of " + drink.getName() + ". Current stock: " + stock.get(drink));
    }

    // Stock management: check current stock level for a specific drink
    public int getDrinkStock(Drink drink) {
        return stock.getOrDefault(drink, 0); // Return 0 if the drink is not found
    }

    // Serving drinks
    public boolean serveDrink(Client client, Drink drink) {
        if (!client.canReceiveDrink()) {
            System.out.println(client.getNickname() + " is no longer allowed to be served.");
            return false;  // Client cannot receive drinks
        }

        int currentStock = getDrinkStock(drink);
        if (currentStock > 0) {
            stock.put(drink, currentStock - 1); // Reduce stock by 1
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

    // define a method for getting a list of all servers
    public List<Server> getServers() {
        return servers;
    }
}
