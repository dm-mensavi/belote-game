package belote;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private String teamName;
    private String skillLevel;  // Added skillLevel attribute
    private final List<Card> hand = new ArrayList<>();
    private int score = 0;

    // Constructor with name, teamName, and skillLevel
    public Player(String name, String teamName, String skillLevel) {
        this.name = name;
        this.teamName = teamName;
        this.skillLevel = skillLevel;
    }

    // Constructor with only name and teamName
    public Player(String name, String teamName) {
        this.name = name;
        this.teamName = teamName;
    }

    // Constructor with only name
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

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }

    // Methods
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

    public void addScore(int points) {
        score += points;
        System.out.println(name + " earned " + points + " points. Total score: " + score);
    }

    public void resetHand() {
        hand.clear();
    }
}
