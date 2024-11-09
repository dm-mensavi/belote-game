package belote;

import utils.Suit;

import java.util.*;

public class BeloteGame {
    private List<Player> team1 = new ArrayList<>();
    private List<Player> team2 = new ArrayList<>();
    private Deck deck;
    private int team1Score = 0;
    private int team2Score = 0;
    private boolean gameStarted = false;
    private Suit trumpSuit;
    private Player dealer;
    private List<Player> playersOrder = new ArrayList<>();

    public BeloteGame() {
        deck = new Deck();
    }

    public void addPlayerToTeam(Scanner scanner, Player player) {
        if (team1.size() < 2) {
            team1.add(player);
            player.setTeamName("Team 1");
            System.out.println(player.getName() + " joined Team 1.");
        } else if (team2.size() < 2) {
            team2.add(player);
            player.setTeamName("Team 2");
            System.out.println(player.getName() + " joined Team 2.");
        } else {
            System.out.println("Both teams are full. Cannot add more players.");
        }
    }

    public void startGame(Scanner scanner) {
        if (team1.size() == 2 && team2.size() == 2) {
            gameStarted = true;
            deck.resetDeck();
            deck.shuffle();
            deck.cut();

            // Determine dealer randomly
            int dealerIndex = new Random().nextInt(4);
            dealer = getAllPlayers().get(dealerIndex);
            System.out.println("Dealer is " + dealer.getName());

            // Set players order starting from the player to the right of the dealer
            setPlayersOrder(dealer);

            // Dealing cards
            dealCards();

            // Determine trump suit
            determineTrumpSuit(scanner);

            // Set trump suit for all cards
            deck.setTrumpSuit(trumpSuit);
            for (Player player : getAllPlayers()) {
                for (Card card : player.getHand()) {
                    if (card.getSuit() == trumpSuit) {
                        card.setTrump(true);
                    } else {
                        card.setTrump(false);
                    }
                }
            }

            System.out.println("Game started with Team 1 vs. Team 2.");
        } else {
            System.out.println("Each team must have 2 players to start the game.");
        }
    }

    public void dealCards() {
        System.out.println("Dealing cards...");
        // First round: 3 cards each
        System.out.println("First round: 3 cards each");
        for (Player player : playersOrder) {
            player.receiveCards(deck.dealCards(3));
        }
        // Second round: 2 cards each
        System.out.println("First round: 2 cards each");
        for (Player player : playersOrder) {
            player.receiveCards(deck.dealCards(2));
        }
        // Flip card for proposed trump
        Card flippedCard = deck.flipCard();
        Suit proposedTrump = flippedCard.getSuit();
        System.out.println("Proposed trump suit is: " + proposedTrump);
        // Ask players if they accept the trump
        boolean trumpAccepted = false;
        for (Player player : playersOrder) {
            System.out.println(
                    player.getName() + ", do you accept the trump suit " + proposedTrump + "? (1. Yes, 2. No)");
            int choice = getIntInput(new Scanner(System.in), 1, 2);
            if (choice == 1) {
                trumpSuit = proposedTrump;
                player.receiveCards(Collections.singletonList(flippedCard));
                System.out.println(player.getName() + " accepted the trump suit.");
                trumpAccepted = true;
                break;
            }
        }
        if (!trumpAccepted) {
            // Second round to choose any trump suit
            for (Player player : playersOrder) {
                System.out.println(player.getName() + ", choose a trump suit or pass:");
                System.out.println("1. Hearts");
                System.out.println("2. Diamonds");
                System.out.println("3. Clubs");
                System.out.println("4. Spades");
                System.out.println("5. Pass");
                int choice = getIntInput(new Scanner(System.in), 1, 5);
                if (choice >= 1 && choice <= 4) {
                    trumpSuit = Suit.values()[choice - 1];
                    System.out.println(player.getName() + " chose trump suit: " + trumpSuit);
                    trumpAccepted = true;
                    break;
                }
            }
            if (!trumpAccepted) {
                System.out.println("No trump suit chosen. Redealing cards.");
                // Redeal cards
                for (Player player : getAllPlayers()) {
                    player.resetHand();
                }
                deck.resetDeck();
                deck.shuffle();
                deck.cut();
                dealCards();
            }
        }
        // Final round: remaining cards
        for (Player player : playersOrder) {
            int remainingCards = 8 - player.getHand().size();
            player.receiveCards(deck.dealCards(remainingCards));
        }
    }

    public void determineTrumpSuit(Scanner scanner) {
        // Trump suit has been determined during the dealing process
        System.out.println("Trump suit is: " + trumpSuit);
    }

    public boolean playGame(Scanner scanner) {
        if (!gameStarted) {
            System.out.println("Game has not started.");
            return false;
        }

        int rounds = 8; // Total number of rounds
        for (int i = 0; i < rounds; i++) {
            System.out.println("Select 1 to continue. (1. Yes, 2. Exit)");
            int continueChoice = getIntInput(scanner, 1, 2);
            if (continueChoice == 2) {
                System.out.println("Exiting the game mid-round. Goodbye!");
                resetGame();
                gameStarted = false;
                return false; // Return false to indicate the game should stop
            }
            playRound(scanner);
        }

        declareWinner();
        return true; // Return true to indicate the game completed successfully
    }

    public void playRound(Scanner scanner) {
        System.out.println("\nStarting a new round...");


        List<Card> playedCards = new ArrayList<>();
        Suit leadSuit = null;
        Player roundWinner = null;
        Card highestCard = null;

        for (Player player : playersOrder) {
            Card cardPlayed = player.playCard(scanner, leadSuit);
            if (leadSuit == null) {
                leadSuit = cardPlayed.getSuit();
            }
            playedCards.add(cardPlayed);

            // Determine the highest card
            if (highestCard == null) {
                highestCard = cardPlayed;
                roundWinner = player;
            } else {
                if (compareCards(cardPlayed, highestCard, leadSuit, trumpSuit) > 0) {
                    highestCard = cardPlayed;
                    roundWinner = player;
                }
            }
        }

        // Update score for the winning team
        int points = calculateRoundPoints(playedCards);
        if (team1.contains(roundWinner)) {
            team1Score += points;
            System.out.println("Team 1 wins this round and gains " + points + " points!");
        } else if (team2.contains(roundWinner)) {
            team2Score += points;
            System.out.println("Team 2 wins this round and gains " + points + " points!");
        }

        System.out.println("Round over. Current Scores - Team 1: " + team1Score + ", Team 2: " + team2Score);

        // Rotate the players order so the winner starts the next round
        setPlayersOrder(roundWinner);
    }

    private int calculateRoundPoints(List<Card> playedCards) {
        int points = 0;
        for (Card card : playedCards) {
            points += card.getCardPoints();
        }
        return points;
    }

    private int compareCards(Card card1, Card card2, Suit leadSuit, Suit trumpSuit) {
        // Trump suit always wins over non-trump suits
        if (card1.getSuit() == trumpSuit && card2.getSuit() != trumpSuit) {
            return 1;
        }
        if (card1.getSuit() != trumpSuit && card2.getSuit() == trumpSuit) {
            return -1;
        }

        // If both cards are of the same suit, compare by rank
        if (card1.getSuit() == card2.getSuit()) {
            return Integer.compare(card1.getRank().ordinal(), card2.getRank().ordinal());
        }

        // Lead suit wins if neither card is a trump suit
        if (card1.getSuit() == leadSuit) {
            return 1;
        }
        if (card2.getSuit() == leadSuit) {
            return -1;
        }

        // Otherwise, cards are of different suits and neither is the lead or trump suit
        return 0;
    }

    public void declareWinner() {
        System.out.println("Final Scores - Team 1: " + team1Score + ", Team 2: " + team2Score);
        if (team1Score > team2Score) {
            System.out.println("Team 1 wins the game!");
        } else if (team2Score > team1Score) {
            System.out.println("Team 2 wins the game!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    // Add isGameStarted() method
    public boolean isGameStarted() {
        return gameStarted;
    }

    private void setPlayersOrder(Player startingPlayer) {
        playersOrder.clear();
        List<Player> allPlayers = getAllPlayers();
        int startIndex = allPlayers.indexOf(startingPlayer);
        for (int i = 0; i < allPlayers.size(); i++) {
            playersOrder.add(allPlayers.get((startIndex + i) % allPlayers.size()));
        }
    }

    public List<Player> getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>(team1);
        allPlayers.addAll(team2);
        return allPlayers;
    }

    private int getIntInput(Scanner scanner, int min, int max) {
        int input = -1;
        boolean valid = false;
        while (!valid) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        return input;
    }

    public void resetGame() {
        team1Score = 0;
        team2Score = 0;
        gameStarted = false;
        trumpSuit = null;
        dealer = null;
        playersOrder.clear();
        for (Player player : getAllPlayers()) {
            player.resetHand();
        }
        deck.resetDeck();
    }
}
