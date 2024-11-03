package pubmanagement;

/**
 * Class representing the bartender in the bar.
 */
public class Bartender extends Human {
    private double cashRegister;  // Amount of cash in the cash register
    private boolean isServing;     // Status of the bartender (serving or not)

    /**
     * Constructor for the Bartender class.
     *
     * @param firstName        The bartender's first name.
     * @param nickname         The bartender's nickname.
     * @param wallet           The bartender's wallet amount.
     * @param significantShout The bartender's significant shout.
     */
    public Bartender(String firstName, String nickname, double wallet, String significantShout) {
        super(firstName, nickname, wallet, significantShout);
        this.cashRegister = 0;  // Initialize cash register to 0
        this.isServing = true;   // Bartender is initially serving
    }

    // Getters
    public double getCashRegister() {
        return cashRegister;
    }

    // Methods

    /**
     * Serves a drink to a client.
     * @param client The client to serve.
     * @param drink  The drink being served.
     */
    public void serveDrink(Client client, Drink drink) {
        if (isServing) {
            System.out.println(getNickname() + " serves a " + drink.getName() + " to " + client.getNickname() + "coco");
            client.drink(drink);
            double price = drink.getSalePrice();
            client.pay(price);
            cashRegister += price;  // Add the sale to the cash register
        } else {
            System.out.println(getNickname() + " is not serving right now.coco");
        }
    }

    /**
     * Offers a drink to a person without charging.
     * @param person The person being offered a drink.
     * @param drink  The drink being offered.
     */
    public void offerDrink(Human person, Drink drink) {
        System.out.println(getNickname() + " offers a " + drink.getName() + " to " + person.getNickname() + "coco");
        double cost = drink.getPurchasePrice();
        cashRegister -= cost;  // Bartender pays from the cash register
        person.drink(drink);
    }

    /**
     * Manages the stock of drinks in the bar.
     * This method updates the stock levels for the given drinks.
     * 
     * @param bar The bar to manage stock for.
     * @param drink The drink to manage.
     * @param quantity The quantity of the drink.
     */
    public void manageStock(Bar bar, Drink drink, int quantity) {
        System.out.println(getNickname() + " is managing stock for " + drink.getName() + "coco");
        
        // Check current stock before updating
        int currentStock = bar.getDrinkStock(drink);
        System.out.println("Current stock of " + drink.getName() + ": " + currentStock + "coco");

        // Update stock in the bar
        bar.receiveStock(drink, quantity);
        System.out.println("Updated stock of " + drink.getName() + " to " + (currentStock + quantity) + " units.coco");
    }

    /**
     * Announces a general round.
     */
    public void announceGeneralRound() {
        System.out.println(getNickname() + ": GENERAL ROUND!");
    }    

    public void addToCashRegister(double amount) {
        cashRegister += amount;  // Update the cash register with the specified amount
    }    

    /**
     * Drinks a drink. Bartenders only drink water.
     * @param drink The drink being offered.
     */
    @Override
    public void drink(Drink drink) {
        if (drink.isAlcoholic()) {
            System.out.println(getNickname() + " does not drink alcoholic beverages. coco");
        } else {
            System.out.println(getNickname() + " drinks " + drink.getName() + "coco");
        }
    }

    @Override
    public void speak(String message) {
        System.out.println("Bartender: " + message + "coco");
    }
}
