package pubmanagement;

import java.util.Map;

/**
 * Class representing the supplier who delivers drinks to the bar.
 */
public class Supplier extends Human {

    /**
     * Constructor for the Supplier class.
     *
     * @param firstName        The supplier's first name.
     * @param nickname         The supplier's nickname.
     * @param wallet           The supplier's wallet amount.
     * @param significantShout The supplier's significant shout.
     */
    public Supplier(String firstName, String nickname, double wallet, String significantShout) {
        super(firstName, nickname, wallet, significantShout);
    }

    // Methods

    /**
     * Receives an order from the bartender and delivers drinks to the bar.
     *
     * @param bartender      The bartender placing the order.
     * @param bar           The bar receiving the order.
     * @param order Map of drinks and their quantities to be delivered.
     */
    public void receiveOrder(Bartender bartender, Bar bar, Map<Drink, Integer> order) {
        System.out.println(getNickname() + " received an order from " + bartender.getNickname() + ".");
        deliverDrinks(bar, order);
    }

    /**
     * Delivers drinks to the bar and adds them to the bar's stock.
     *
     * @param bar           The bar receiving the delivery.
     * @param drinksToDeliver Map of drinks and their quantities.
     */
    public void deliverDrinks(Bar bar, Map<Drink, Integer> drinksToDeliver) {
        for (Map.Entry<Drink, Integer> entry : drinksToDeliver.entrySet()) {
            Drink drink = entry.getKey();
            int quantity = entry.getValue();
            bar.receiveStock(drink, quantity);  // Add the delivered drinks to the bar's stock
            System.out.println(getNickname() + " delivered " + quantity + " units of " + drink.getName() + ".");
        }
    }

    /**
     * Gets paid by the bar owner (Patronne).
     *
     * @param patronne The boss paying the supplier.
     * @param amount   The amount to be paid to the supplier.
     */
    public void getPaid(Patronne patronne, double amount) {
        patronne.setWallet(patronne.getWallet() - amount);  // Deduct amount from the Patronne's wallet
        setWallet(getWallet() + amount);  // Add amount to Supplier's wallet
        System.out.println(getNickname() + " was paid " + amount + " euros by " + patronne.getNickname() + ".");
    }

    /**
     * Implementation of the drink method from the Human class.
     * The Supplier doesn't drink, so we just print a message indicating refusal.
     *
     * @param drink The drink being offered to the Supplier.
     */
    @Override
    public void drink(Drink drink) {
        System.out.println(getNickname() + " says: I don't drink while working.");
    }
}
