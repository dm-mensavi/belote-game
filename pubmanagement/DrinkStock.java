package pubmanagement;

/**
 * Class representing the stock of a drink in the bar.
 */
public class DrinkStock {
    private Drink drink;
    private int quantity;

    /**
     * Constructor for the DrinkStock class.
     *
     * @param drink The drink object.
     * @param quantity The quantity of the drink in stock.
     */
    public DrinkStock(Drink drink, int quantity) {
        this.drink = drink;
        this.quantity = quantity;
    }

    // Getter for the drink
    public Drink getDrink() {
        return drink;
    }

    // Getter for the quantity
    public int getQuantity() {
        return quantity;
    }

    // Method to add quantity
    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    // Method to remove quantity
    public void removeQuantity(int amount) {
        if (amount <= this.quantity) {
            this.quantity -= amount;
        } else {
            System.out.println("Insufficient stock to remove " + amount + " units.");
        }
    }
}
