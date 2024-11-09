package belote;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.Suit;


public class Player {
    private final String name;
    private String teamName;
    private final List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.teamName = null;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Card> getHand() {
        return hand;
    }

    // Methods
    public void receiveCards(List<Card> cards) {
        hand.addAll(cards);
        System.out.println(name + " received cards.");
    }

    public Card playCard(Scanner scanner, Suit leadSuit) {
        System.out.println(name + ", it's your turn to play.");
        displayHand();

        int choice = -1;
        while (choice < 1 || choice > hand.size()) {
            System.out.println("Select a card to play (1-" + hand.size() + "):");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        Card selectedCard = hand.remove(choice - 1);
        System.out.println(name + " played: " + selectedCard);
        return selectedCard;
    }

    public void displayHand() {
        System.out.println(name + "'s hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i));
        }
    }

    public void resetHand() {
        hand.clear();
    }
}
