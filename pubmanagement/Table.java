package pubmanagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a table in the bar.
 * Each table can seat up to 4 clients.
 */
public class Table {
    private static final int MAX_CAPACITY = 4;  // Maximum number of people at a table
    private List<Client> seatedClients;

    /**
     * Constructor to initialize an empty table.
     */
    public Table() {
        this.seatedClients = new ArrayList<>();  // Start with an empty list of seated clients
    }

    // Get the list of seated clients
    public List<Client> getSeatedClients() {
        return seatedClients;
    }

    // Check if there is space for more clients
    public boolean hasSpace() {
        return seatedClients.size() < MAX_CAPACITY;
    }

    // Seat a client at the table if there is room
    public boolean seatClient(Client client) {
        if (hasSpace()) {
            seatedClients.add(client);
            System.out.println(client.getNickname() + " is seated at the table.");
            return true;
        } else {
            System.out.println("The table is full. " + client.getNickname() + " cannot be seated.");
            return false;
        }
    }

    // Remove a client from the table
    public void removeClient(Client client) {
        seatedClients.remove(client);
        System.out.println(client.getNickname() + " has left the table.");
    }

    // Get the number of available seats
    public int getAvailableSeats() {
        return MAX_CAPACITY - seatedClients.size();
    }
}
