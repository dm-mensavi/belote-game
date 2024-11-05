package belote;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private String teamName;
    private final List<Card> hand = new ArrayList<>();
    private int score = 0;

    public Player(String name, String teamName) {
        this.name = name;
        this.teamName = teamName;
    }

    public Player(String name) {
        this.name = name;
        this.teamName = null;
    }

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

    public void receiveHand(List<Card> cards) {
        hand.addAll(cards);
        System.out.println(name + " received a full hand of cards.");
    }

    public Card playFirstCard() {
        if (!hand.isEmpty()) {
            Card card = hand.remove(0);  // Remove the first card in the hand
            System.out.println(name + " played: " + card);
            return card;  // Return the card that was played
        } else {
            System.out.println(name + " has no cards left to play.");
            return null;
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
