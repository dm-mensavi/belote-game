package pubmanagement;

import utils.Gender;

/**
 * Class representing a server in the bar.
 */
public class Server extends Human {
    private int strength;  // For male servers (biceps size)
    private int charm;     // For female servers (charm factor)
    private Gender gender;

    /**
     * Constructor for the Server class.
     *
     * @param firstName        The server's first name.
     * @param nickname         The server's nickname.
     * @param wallet           The server's wallet amount.
     * @param significantShout The server's significant shout.
     * @param gender           The server's gender (MALE or FEMALE).
     * @param strengthOrCharm  The strength (for males) or charm (for females).
     */
    public Server(String firstName, String nickname, double wallet, String significantShout, Gender gender,
                  int strengthOrCharm) {
        super(firstName, nickname, wallet, significantShout);
        this.gender = gender;
        if (gender == Gender.MALE) {
            this.strength = strengthOrCharm;
            this.charm = 0;  // Male servers have no charm factor
        } else {
            this.charm = strengthOrCharm;
            this.strength = 0;  // Female servers have no strength factor
        }
    }

    public Server(String firstName, String nickname, double wallet) {
        super(firstName, nickname, wallet, "Default Shout");
        this.gender = Gender.MALE; // or set a default gender if appropriate
        this.strength = 5;         // default strength or charm level
        this.charm = 0;
    }
    

    // Getters
    public int getStrength() {
        return strength;
    }

    public int getCharm() {
        return charm;
    }

    public Gender getGender() {
        return gender;
    }

    // Methods

    /**
     * Serve a drink to a client.
     * @param client The client to serve.
     * @param drink The drink being served.
     */
    public void serve(Client client, Drink drink) {
        System.out.println(getNickname() + " serves a " + drink.getName() + " to " + client.getNickname() + ".");
        client.drink(drink);
        double price = drink.getSalePrice();
        client.pay(price);

        // Update client's data in the database
        client.saveClientData();

        // Behavioral impact based on server's gender and characteristics
        if (gender == Gender.FEMALE && charm > 5) {
            // Clients consume more when served by a charming waitress
            System.out.println(client.getNickname() + " consumes more due to the charm of " + getNickname() + ".");
            client.drink(drink);  // Simulate the client consuming another drink
            client.saveClientData();  // Update client data
        } else if (gender == Gender.MALE && strength > 5 && client.getAlcoholLevel() > 5) {
            // Strong male servers keep clients in check
            System.out.println(client.getNickname() + " behaves more reasonably due to " + getNickname() + "'s strength.");
        }
    }

    /**
     * Takes an order from a client.
     * @param client The client placing an order.
     */
    public void receiveOrder(Client client) {
        System.out.println(getNickname() + " takes order from " + client.getNickname() + ".");
    }

    /**
     * Servers only drink water.
     * @param drink The drink being offered.
     */
    @Override
    public void drink(Drink drink) {
        if (drink.getName().equalsIgnoreCase("water")) {
            System.out.println(getNickname() + " drinks water.");
        } else {
            System.out.println(getNickname() + " only drinks water.");
        }
    }

    /**
     * Indicate that servers are busy during a general round.
     */
    public void indicateBusy() {
        speak("I'm busy serving clients.");
    }

    @Override
    public void speak(String message) {
        System.out.println("Server: " + message);
    }

    // Method to save updated wallet or other data to staff.txt file
    private void saveServerData() {
        // Read existing staff data, update this server's data, and save back to file
        PubEnvironment.updateStaffInDatabase(this);
    }
}
