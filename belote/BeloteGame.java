package belote;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing the Belote game.
 */
public class BeloteGame {
    private List<Player> team1 = new ArrayList<>();
    private List<Player> team2 = new ArrayList<>();
    private Deck deck;
    private int team1Score = 0;
    private int team2Score = 0;
    private boolean gameStarted = false;

    public BeloteGame() {
        deck = new Deck();
    }

    public void addPlayerToTeam(Scanner scanner, Player player) {
        if (team1.size() < 2) {
            System.out.println("Team 1 has a spot. Would you like to join? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                team1.add(player);
                System.out.println(player.getName() + " joined Team 1.");
                return;
            }
        }

        if (team2.size() < 2) {
            System.out.println("Team 2 has a spot. Would you like to join? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                team2.add(player);
                System.out.println(player.getName() + " joined Team 2.");
                return;
            }
        }

        if (team1.size() >= 2 && team2.size() >= 2) {
            System.out.println("Both teams are full. Please join another table.");
        }
    }

    public void startGame() {
        if (team1.size() == 2 && team2.size() == 2) {
            deck.shuffle();
            gameStarted = true;
            System.out.println("Game started with Team 1 vs. Team 2.");
        } else {
            System.out.println("Each team must have 2 players to start the game.");
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void dealCards() {
        if (!gameStarted) {
            System.out.println("Game has not started. Start the game first.");
            return;
        }

        System.out.println("Dealing cards...");
        for (Player player : getAllPlayers()) {
            player.receiveHand(deck.dealHand(8));
            System.out.println(player.getName() + "'s hand: " + player.getHand()); // Display each player's hand
        }
    }

    public void playRound() {
        if (!gameStarted) {
            System.out.println("Game has not started.");
            return;
        }

        System.out.println("\nPlaying a round...");
        List<Card> playedCards = new ArrayList<>();
        List<Player> allPlayers = getAllPlayers();
        Player roundWinner = null;
        Card highestCard = null;

        // Each player plays their first card, and we display it
        for (Player player : allPlayers) {
            Card cardPlayed = player.playFirstCard();
            if (cardPlayed != null) {
                System.out.println(player.getName() + " played: " + cardPlayed); // Display card played by each player
                playedCards.add(cardPlayed);

                // Determine the round winner based on the highest card played
                if (highestCard == null || cardPlayed.getRank().getValue() > highestCard.getRank().getValue()) {
                    highestCard = cardPlayed;
                    roundWinner = player;
                }
            }
        }

        // Update score for the winning team
        if (roundWinner != null) {
            if (team1.contains(roundWinner)) {
                team1Score += 10;
                System.out.println("Team 1 wins this round!");
            } else if (team2.contains(roundWinner)) {
                team2Score += 10;
                System.out.println("Team 2 wins this round!");
            }
        }

        System.out.println("Round over. Current Scores - Team 1: " + team1Score + ", Team 2: " + team2Score);
    }

    public void declareWinner() {
        if (!gameStarted) {
            System.out.println("Game has not started.");
            return;
        }

        System.out.println("Final Scores - Team 1: " + team1Score + ", Team 2: " + team2Score);
        if (team1Score > team2Score) {
            System.out.println("Team 1 wins the game!");
        } else if (team2Score > team1Score) {
            System.out.println("Team 2 wins the game!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private List<Player> getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>(team1);
        allPlayers.addAll(team2);
        return allPlayers;
    }

    public List<Player> getTeam1() {
        return team1;
    }
    
    public List<Player> getTeam2() {
        return team2;
    }
    
}
