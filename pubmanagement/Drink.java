package pubmanagement;

/**
 * Class representing a drink in the bar.
 */
public class Drink {
    private String name;
    private boolean isAlcoholic;
    private int alcoholPoints;  // Points per glass for alcoholic drinks
    private double salePrice;   // Price the customer pays
    private double purchasePrice; // Price the bar pays to acquire the drink

    /**
     * Constructor for non-alcoholic drinks.
     *
     * @param name          The name of the drink.
     * @param salePrice     The price at which the drink is sold to customers.
     * @param purchasePrice The price at which the bar purchases the drink.
     */
    public Drink(String name, double salePrice, double purchasePrice) {
        this.name = name;
        this.isAlcoholic = false;  // Non-alcoholic drinks
        this.alcoholPoints = 0;    // Non-alcoholic drinks have no alcohol points
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
    }

    /**
     * Constructor for alcoholic drinks.
     *
     * @param name          The name of the drink.
     * @param salePrice     The price at which the drink is sold to customers.
     * @param purchasePrice The price at which the bar purchases the drink.
     * @param alcoholPoints The number of alcohol points in the drink.
     */
    public Drink(String name, double salePrice, double purchasePrice, int alcoholPoints) {
        this.name = name;
        this.isAlcoholic = true;   // Alcoholic drinks
        this.alcoholPoints = alcoholPoints;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
    }

    // Getters
    public String getName() {
        return name;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public int getAlcoholPoints() {
        return alcoholPoints;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    // Setters
    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
