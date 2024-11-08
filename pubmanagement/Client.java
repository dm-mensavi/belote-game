package pubmanagement;

import utils.Gender;
import java.util.List;

/**
 * Class representing a client in the bar.
 */
public class Client extends Human {
    private Drink favoriteDrink;
    private Drink backupDrink;
    private int alcoholLevel;
    private Gender gender;
    private boolean canReceiveDrink;
    private String clothingOrJewelry;  // T-shirt for males, jewelry for females

    // Qualifiers used when alcohol level exceeds a certain threshold
    private static final int ALCOHOL_THRESHOLD = 5;  // Alcohol level at which clients use qualifiers
    private static final String[] MALE_QUALIFIERS = {"doll", "my pretty"};
    private static final String[] FEMALE_QUALIFIERS = {"handsome", "cutie"};

    /**
     * Constructor for the Client class.
     *
     * @param firstName         The client's first name.
     * @param nickname          The client's nickname.
     * @param wallet            The client's wallet amount.
     * @param significantShout  The client's significant shout.
     * @param gender            The client's gender.
     * @param favoriteDrink     The client's favorite drink.
     * @param backupDrink       The client's backup drink.
     * @param clothingOrJewelry Clothing for males (T-shirt), jewelry for females.
     */
    public Client(String firstName, String nickname, double wallet, String significantShout, Gender gender,
                  Drink favoriteDrink, Drink backupDrink, String clothingOrJewelry) {
        super(firstName, nickname, wallet, significantShout);
        this.gender = gender;
        this.favoriteDrink = favoriteDrink;
        this.backupDrink = backupDrink;
        this.alcoholLevel = 0;  // Initial alcohol level is 0
        this.canReceiveDrink = true;  // By default, the client can receive drinks
        this.clothingOrJewelry = clothingOrJewelry;
    }

    // Getters and Setters
    public Drink getFavoriteDrink() {
        return favoriteDrink;
    }

    public void setFavoriteDrink(Drink favoriteDrink) {
        this.favoriteDrink = favoriteDrink;
        saveClientData();
    }

    public Drink getBackupDrink() {
        return backupDrink;
    }

    public void setBackupDrink(Drink backupDrink) {
        this.backupDrink = backupDrink;
        saveClientData();
    }

    public int getAlcoholLevel() {
        return alcoholLevel;
    }

    public Gender getGender() {
        return gender;
    }

    public void changeGender(Gender newGender, String newClothingOrJewelry) {
        this.gender = newGender;
        this.clothingOrJewelry = newClothingOrJewelry;
        speak("I have changed gender to " + newGender + " and now wear " + clothingOrJewelry + ".");
        saveClientData();
    }

    public boolean canReceiveDrink() {
        return canReceiveDrink;
    }

    public void setCanReceiveDrink(boolean canReceiveDrink) {
        this.canReceiveDrink = canReceiveDrink;
        saveClientData();
    }

    public String getClothingOrJewelry() {
        return clothingOrJewelry;
    }

    // Methods

    /**
     * Client drinks a drink and increases alcohol level if the drink is alcoholic.
     */
    @Override
    public void drink(Drink drink) {
        speak("I drink a " + drink.getName() + ".");
        if (drink.isAlcoholic()) {
            alcoholLevel += drink.getAlcoholPoints();
            checkForQualifierUsage();
            saveClientData();  // Save updated alcohol level
        }
    }

    /**
     * Introduces the client with gender-specific details.
     */
    @Override
    public void introduce() {
        super.introduce();
        if (gender == Gender.MALE) {
            speak("I'm wearing a " + clothingOrJewelry + " T-shirt.");
        } else {
            speak("I'm wearing " + clothingOrJewelry + " jewelry.");
        }
        speak("My favorite drink is " + favoriteDrink.getName() + ".");
    }

    /**
     * Receive a drink from another person (such as the bartender).
     */
    public void receiveDrink(Drink drink) {
        if (canReceiveDrink) {
            if (getWallet() >= drink.getSalePrice()) {
                drink(drink);  // Client drinks the offered drink
                pay(drink.getSalePrice());
                speak("I have received a " + drink.getName() + ".");
            } else {
                speak("I don't have enough money to pay for this drink.");
            }
        } else {
            speak("I cannot receive any more drinks.");
        }
    }

    /**
     * Checks if the client has exceeded the alcohol threshold and uses appropriate qualifiers when addressing waitstaff.
     */
    private void checkForQualifierUsage() {
        if (alcoholLevel >= ALCOHOL_THRESHOLD) {
            if (gender == Gender.MALE) {
                speak("addresses the waitress as " + getRandomQualifier(MALE_QUALIFIERS) + ".");
            } else if (gender == Gender.FEMALE) {
                speak("addresses the waiter as " + getRandomQualifier(FEMALE_QUALIFIERS) + ".");
            }
        }
    }

    /**
     * Get a random qualifier based on the client's gender.
     * @param qualifiers Array of qualifiers to choose from.
     * @return A random qualifier.
     */
    private String getRandomQualifier(String[] qualifiers) {
        int randomIndex = (int) (Math.random() * qualifiers.length);
        return qualifiers[randomIndex];
    }

    /**
     * Client pays for the drink and deducts the amount from their wallet.
     * 
     * @param amount The amount to pay.
     */
    public void pay(double amount) {
        if (getWallet() >= amount) {
            setWallet(getWallet() - amount);  // Deduct the amount from the wallet
            speak("I paid " + amount + " euros.");
            saveClientData();
        } else {
            speak("I don't have enough money to pay for this.");
        }
    }

    /**
     * General round: All clients express their significant cry in unison.
     */
    public static void expressSignificantCry(List<Client> clients) {
        for (Client client : clients) {
            client.speak(client.getSignificantShout());
        }
    }

    // Method to save updated client data to clients.txt file
    public void saveClientData() {
        // Read existing clients data, update this client's data, and save back to file
        PubEnvironment.updateClientInDatabase(this);
    }

    @Override
    protected void saveHumanData() {
        saveClientData();
    }
}

