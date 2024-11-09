package belote;

import java.util.Scanner;

public class MainBeloteGame {
    public static void startBeloteGame() {
        Scanner scanner = new Scanner(System.in);
        BeloteGame game = new BeloteGame();
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\nBelote Game Menu:");
            System.out.println("1. Add Player");
            System.out.println("2. Start Game");
            System.out.println("3. Play Game");
            System.out.println("4. Reset Game");
            System.out.println("5. Exit");

            int choice = getIntInput(scanner, 1, 5);

            switch (choice) {
                case 1 -> {
                    if (game.getAllPlayers().size() < 4) {
                        System.out.print("Enter player name: ");
                        String name = scanner.nextLine();
                        game.addPlayerToTeam(scanner, new Player(name));
                    } else {
                        System.out.println("All player slots are full.");
                    }
                }
                case 2 -> {
                    if (!game.isGameStarted()) {
                        game.startGame(scanner);
                    } else {
                        System.out.println("Game is already started.");
                    }
                }
                case 3 -> {
                    if (game.isGameStarted()) {
                        boolean continuePlaying = game.playGame(scanner);
                        if (!continuePlaying) {
                            keepRunning = false; // If players choose to exit mid-game, stop the loop
                        }
                    } else {
                        System.out.println("You need to start the game first.");
                    }
                }
                case 4 -> {
                    game.resetGame();
                    System.out.println("Game has been reset.");
                }
                case 5 -> {
                    keepRunning = false;
                    System.out.println("Exiting the game. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getIntInput(Scanner scanner, int min, int max) {
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
}
