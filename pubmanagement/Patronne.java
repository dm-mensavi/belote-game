package pubmanagement;

import java.util.List;

/**
 * Class representing the patronne (owner) of the bar.
 */
public class Patronne extends Human {
    private String barName;

    /**
     * Constructor for the Patronne class.
     *
     * @param firstName        The patronne's first name.
     * @param nickname         The patronne's nickname.
     * @param wallet           The patronne's wallet amount.
     * @param significantShout The patronne's significant shout.
     * @param barName          The name of the bar.
     */
    public Patronne(String firstName, String nickname, double wallet, String significantShout, String barName) {
        super(firstName, nickname, wallet, significantShout);
        this.barName = barName;
    }

    // Getters
    public String getBarName() {
        return barName;
    }

    // Methods

    /**
     * Collects money from the cash register when there is too much liquidity.
     *
     * @param bartender The bartender from whom to collect cash.
     * @param threshold The liquidity threshold above which cash is collected.
     */
    public void collectCashFromRegister(Bartender bartender, double threshold) {
        double cash = bartender.getCashRegister();
        if (cash > threshold) {
            double collectedAmount = cash - threshold;
            bartender.addToCashRegister(-collectedAmount); // Reducing the excess cash
            setWallet(getWallet() + collectedAmount); // Adding to Patronne's wallet
            saveWallet(); // Save updated wallet to file
            speak("I have collected " + collectedAmount + " euros from the cash register.");
        } else {
            speak("There is not enough excess cash to collect right now.");
        }
    }

    public void reactToGeneralRound() {
        speak("Everything is fine, business is picking up.");
    }    

    /**
     * Offers a drink to a client without paying for it.
     *
     * @param person The person being offered a drink.
     * @param drink  The drink being offered.
     */
    public void offerDrink(Human person, Drink drink) {
        speak("I'm offering a " + drink.getName() + " to " + person.getNickname() + ".");
        person.drink(drink);
        // Patronne doesn't pay for the drink
    }

    /**
     * Orders the bartender or server to stop serving a client who is no longer fit to drink.
     *
     * @param client The client to stop serving.
     */
    public void stopServing(Client client) {
        client.setCanReceiveDrink(false); // The client can no longer receive drinks
        client.saveClientData();          // Save updated client data
        speak("Stop serving " + client.getNickname() + " immediately.");
    }

    /**
     * Temporarily bans a client from the bar.
     *
     * @param client The client to ban.
     * @param bar    The bar from which to remove the client.
     */
    public void banClient(Client client, Bar bar) {
        List<Client> clients = bar.getClients();
        if (clients.contains(client)) {
            bar.removeClient(client); // Remove the client from the list of active clients
            speak("I am temporarily banning " + client.getNickname() + " from the bar.");
        } else {
            speak(client.getNickname() + " is not currently in the bar.");
        }
    }

    @Override
    public void drink(Drink drink) {
        speak("I am enjoying a " + drink.getName() + ".");
    }

    @Override
    public void speak(String message) {
        super.speak(message + " (Patronne)");
    }

    // Method to save updated wallet to staff.txt file
    private void saveWallet() {
        // Read existing staff data, update this patronne's wallet, and save back to file
        PubEnvironment.updateStaffInDatabase(this);
    }
}
