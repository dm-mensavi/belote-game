package belote;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a player in the belote game.
 */
public class Player {
    private String name;
    private String teamName;
    private List<Card> hand;
    private int score;

    public Player(String name, String teamName) {
        this.name = name;
        this.teamName = teamName;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void receiveCard(Card card) {
        hand.add(card);
        System.out.println(name + " received a card: " + card);
    }

    public void receiveHand(List<Card> cards) {
        hand.addAll(cards);
        System.out.println(name + " received a full hand of cards.");
    }

    public void playCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
            System.out.println(name + " played: " + card);
        } else {
            System.out.println(name + " doesn't have this card.");
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
        System.out.println(name + " earned " + points + " points. Total score: " + score);
    }

    public void resetHand() {
        hand.clear();
    }
}
