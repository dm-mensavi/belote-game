package belote;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the belote game.
 */
public class BeloteGame {
    private List<Player> players;
    private Deck deck;
    @SuppressWarnings("unused")
    private int team1Score;
    @SuppressWarnings("unused")
    private int team2Score;

    public BeloteGame() {
        players = new ArrayList<>();
        deck = new Deck();
        team1Score = 0;
        team2Score = 0;
    }

    // Methods to manage the game
    public void addPlayer(Player player) {
        if (players.size() < 4) {
            players.add(player);
            System.out.println(player.getName() + " has joined the game.");
        } else {
            System.out.println("The game already has 4 players.");
        }
    }

    public void startGame() {
        if (players.size() != 4) {
            System.out.println("A belote game requires 4 players.");
            return;
        }

        deck.shuffle();
        deck.cut();
        dealCards();

        System.out.println("Belote game started!");
    }

    public void dealCards() {
        // Deal cards to each player (8 cards per player)
        for (Player player : players) {
            player.receiveHand(deck.dealHand(8));
        }
    }

    public void playRound() {
        // This would contain the game logic for each round (simplified here)
        System.out.println("Playing a round...");
        // For example, each player plays a card from their hand
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                Card card = player.getHand().get(0);
                player.playCard(card);
            }
        }
    }

    public void declareWinner() {
        int team1TotalScore = players.get(0).getScore() + players.get(2).getScore();
        int team2TotalScore = players.get(1).getScore() + players.get(3).getScore();

        if (team1TotalScore > team2TotalScore) {
            System.out.println("Team 1 wins with a score of " + team1TotalScore + "!");
        } else if (team2TotalScore > team1TotalScore) {
            System.out.println("Team 2 wins with a score of " + team2TotalScore + "!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}
