package pubmanagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the supplier who delivers drinks to the bar.
 */
public class Supplier extends Human {
    private double amountOwed;  // Amount owed to the supplier by the bar
    private List<DrinkDelivery> pendingDeliveries;  // List of pending deliveries

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
        this.amountOwed = 0.0;
        this.pendingDeliveries = new ArrayList<>();
    }

    // Getters
    public double getAmountOwed() {
        return amountOwed;
    }

    // Methods

    /**
     * Delivers 5 units of each drink to the bar and updates the bar's stock.
     *
     * @param bar    The bar receiving the delivery.
     * @param drinks The list of drinks available for delivery.
     */
    public void deliverDrinks(Bar bar, List<Drink> drinks) {
        System.out.println("\nDelivering drinks to the bar...");

        for (Drink drink : drinks) {
            int quantity = 5; // Fixed quantity of 5 units for each drink

            // Add to pending deliveries
            pendingDeliveries.add(new DrinkDelivery(drink, quantity));

            // Update the bar's stock immediately
            bar.receiveStock(drink, quantity);

            // Calculate the amount owed for this drink
            double amountForDrink = drink.getPurchasePrice() * quantity;
            amountOwed += amountForDrink;

            System.out.println(getNickname() + " delivered " + quantity + " units of " + drink.getName() + ".");
            System.out.println("Amount owed for this drink: " + amountForDrink + " euros.");
            System.out.println("---------------------------------------------------");
        }

        if (pendingDeliveries.isEmpty()) {
            System.out.println("No drinks were delivered.");
        } else {
            System.out.println("====================================================");
            System.out.println("Total amount owed for this delivery: " + amountOwed + " euros.");
            System.out.println("====================================================");
        }
    }

    /**
     * Gets paid by the bar owner (Patronne).
     *
     * @param patronne The boss paying the supplier.
     */
    public void getPaid(Patronne patronne) {
        if (amountOwed <= 0) {
            System.out.println(getNickname() + " has no pending deliveries to be paid for.");
            return;
        }

        System.out.println(getNickname() + " requests payment of " + amountOwed + " euros from " + patronne.getNickname() + ".");

        if (patronne.getWallet() >= amountOwed) {
            patronne.setWallet(patronne.getWallet() - amountOwed);  // Deduct amount from the Patronne's wallet
            setWallet(getWallet() + amountOwed);  // Add amount to Supplier's wallet
            System.out.println(getNickname() + " was paid " + amountOwed + " euros by " + patronne.getNickname() + ".");
            // Save updated wallets
            PubEnvironment.updateStaffInDatabase(patronne);
            PubEnvironment.updateStaffInDatabase(this);

            // Reset the amount owed and clear pending deliveries
            amountOwed = 0.0;
            pendingDeliveries.clear();
        } else {
            System.out.println(patronne.getNickname() + " does not have enough money to pay the supplier.");
        }
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

    // Inner class to represent a delivery item
    private static class DrinkDelivery {
        private Drink drink;
        private int quantity;

        public DrinkDelivery(Drink drink, int quantity) {
            this.drink = drink;
            this.quantity = quantity;
        }
    }
}
