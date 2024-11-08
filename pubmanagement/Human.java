package pubmanagement;

/**
 * Abstract class representing a human in the bar.
 */
public abstract class Human {
    private String firstName;
    private String nickname;
    private double wallet;
    private String significantShout;

    public Human(String firstName, String nickname, double wallet, String significantShout) {
        this.firstName = firstName;
        this.nickname = nickname;
        this.wallet = wallet;
        this.significantShout = significantShout;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getNickname() {
        return nickname;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
        saveHumanData();
    }

    public String getSignificantShout() {
        return significantShout;
    }

    // General method for speaking
    public void speak(String message) {
        System.out.println(getFirstName() + ": " + message);
    }

    // Abstract method for drinking (to be implemented by subclasses)
    public abstract void drink(Drink drink);
    
    // Introduce method to be overridden by subclasses
    public void introduce() {
        speak("Hello, my name is " + getFirstName() + " and I go by " + getNickname() + ".");
    }

    // Method to save updated human data to the appropriate file
    protected void saveHumanData() {
        // This method can be overridden by subclasses if needed
    }
}
