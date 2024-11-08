import java.util.Scanner;
import belote.MainBeloteGame;
import tournament.TournamentEnvironment;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

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
                    case 1 -> PubEnvironment.initializePub();
                    case 2 -> MainBeloteGame.startBeloteGame();
                    case 3 -> TournamentEnvironment.startTournament();
                    case 4 -> {
                        System.out.println("Exiting the application. Goodbye!");
                        keepRunning = false;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();  // Clear invalid input
            }
        }

        scanner.close();
    }
}
