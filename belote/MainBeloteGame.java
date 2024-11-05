package belote;

import java.util.Scanner;

public class MainBeloteGame {
    public static void startBeloteGame() {
        Scanner scanner = new Scanner(System.in);
        BeloteGame game = new BeloteGame();

        System.out.println("Are you a Dealer or a Player? (Enter 'dealer' or 'player')");
        String role = scanner.nextLine().toLowerCase();

        if (role.equals("dealer")) {
            dealerActions(scanner, game);
        } else if (role.equals("player")) {
            playerActions(scanner, game);
        } else {
            System.out.println("Invalid role. Exiting.");
        }
    }

    private static void dealerActions(Scanner scanner, BeloteGame game) {
        boolean keepRunning = true;
    
        while (keepRunning) {
            System.out.println("\nDealer Menu:");
            System.out.println("Select an option:");
    
            int option = 1;
            boolean canAddPlayer = game.getTeam1().size() < 2 || game.getTeam2().size() < 2;
    
            // Display "Add Player" only if there is space on the table
            if (canAddPlayer && !game.isGameStarted()) {
                System.out.println(option + ". Add Player to Team");
                option++;
            }
    
            // Display "Start Game" only if the game has not started and both teams are full
            if (!game.isGameStarted() && !canAddPlayer) {
                System.out.println(option + ". Start Game");
                option++;
            }
    
            // Display game options only after the game has started
            if (game.isGameStarted()) {
                System.out.println(option + ". Deal Cards");
                option++;
                System.out.println(option + ". Play Round");
                option++;
                System.out.println(option + ". Declare Winner");
                option++;
            }
    
            System.out.println(option + ". Exit");
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1 -> {
                    if (canAddPlayer && !game.isGameStarted()) {
                        System.out.print("Enter player name: ");
                        String name = scanner.nextLine();
                        game.addPlayerToTeam(scanner, new Player(name));
                    } else if (!game.isGameStarted() && !canAddPlayer) {
                        game.startGame();
                    } else if (game.isGameStarted()) {
                        game.dealCards();
                    } else {
                        System.out.println("Invalid option.");
                    }
                }
                case 2 -> {
                    if (!game.isGameStarted() && !canAddPlayer) {
                        game.startGame();
                    } else if (game.isGameStarted()) {
                        game.playRound();
                    } else {
                        keepRunning = false;
                    }
                }
                case 3 -> {
                    if (game.isGameStarted()) {
                        game.declareWinner();
                    } else {
                        System.out.println("Invalid option.");
                    }
                }
                case 4 -> keepRunning = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    

    private static void playerActions(Scanner scanner, BeloteGame game) {
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        game.addPlayerToTeam(scanner, player);

        if (game.isGameStarted()) {
            game.dealCards();
            game.playRound();
        } else {
            System.out.println("Waiting for other players to join...");
        }
    }
}
