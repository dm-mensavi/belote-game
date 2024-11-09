import pubmanagement.PubEnvironment;
import pubmanagement.Bar;
import pubmanagement.Patronne;
import pubmanagement.Bartender;
import tournament.TournamentEnvironment; // Uncommented this line
import belote.MainBeloteGame;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Single Scanner instance
        boolean keepRunning = true;

        // Declare variables for bar-related elements, but only initialize them in the tournament
        Bar bar = null;
        Patronne patronne = new Patronne("Marie", "BossLady", 1000, "Let's celebrate!", null); // Initialize the Patronne
        Bartender bartender = new Bartender("Paul", "MasterPour", 500, "One for everyone!"); // Initialize the Bartender

        while (keepRunning) {
            System.out.println("\nWelcome to the Bar Management and Belote Game App!");
            System.out.println("Please select an option:");
            System.out.println("1. Manage Pub");
            System.out.println("2. Play Belote Game");
            System.out.println("3. Play Belote Tournament");
            System.out.println("4. Quit");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1 -> PubEnvironment.initializePub(scanner);  // This does not affect the bar instance
                    case 2 -> MainBeloteGame.startBeloteGame();
                    case 3 -> {
                        // Initialize the Bar instance only when the user selects "Play Belote Tournament"
                        if (bar == null) {
                            bar = new Bar("At Marie's Pub", patronne, bartender);
                        }
                        TournamentEnvironment.startTournament(scanner, bar);
                    }
                    case 4 -> {
                        System.out.println("Exiting the application. Goodbye!");
                        keepRunning = false;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();  // Close the Scanner here
    }
}
