package tournament;

import pubmanagement.Bar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class providing the user interface for managing the tournament.
 */
public class TournamentEnvironment {
    public static void startTournament(Scanner scanner, Bar bar) {
        Tournament tournament = new Tournament();

        System.out.println("Tournament Setup");
        System.out.println("----------------");
        System.out.print("Enter tournament name: ");
        String tournamentName = scanner.nextLine();
        System.out.print("Enter registration fee per team: ");
        double registrationFee = getDoubleInput(scanner);

        System.out.println("Registration phase:");
        boolean registrationOpen = true;
        while (registrationOpen) {
            System.out.println("\nRegistration Menu:");
            System.out.println("1. Register a Team");
            System.out.println("2. Start Tournament");
            System.out.println("3. Cancel Tournament");
            int choice = getIntInput(scanner, 1, 3);

            switch (choice) {
                case 1 -> registerTeam(scanner, tournament);
                case 2 -> {
                    try {
                        tournament.startTournament(scanner);
                        registrationOpen = false;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Tournament canceled.");
                    return;
                }
            }
        }

        // Tournament phase
        boolean tournamentOngoing = true;
        while (tournamentOngoing) {
            System.out.println("\nTournament Menu:");
            System.out.println("1. View Upcoming Matches");
            System.out.println("2. Play a Match");
            System.out.println("3. View Score Sheet");
            System.out.println("4. End Tournament");
            int choice = getIntInput(scanner, 1, 4);

            switch (choice) {
                case 1 -> viewUpcomingMatches(tournament);
                case 2 -> playMatch(scanner, tournament);
                case 3 -> tournament.displayScoreSheet();
                case 4 -> {
                    tournament.endTournament();
                    tournamentOngoing = false;
                }
            }
        }
    }

    private static void registerTeam(Scanner scanner, Tournament tournament) {
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        Team team = new Team(teamName);

        for (int i = 1; i <= 2; i++) {
            System.out.print("Enter player " + i + " name for team " + teamName + ": ");
            String playerName = scanner.nextLine();

            // Display skill level options
            System.out.println("Choose skill level for " + playerName + ":");
            System.out.println("1. Beginner");
            System.out.println("2. Novice");
            System.out.println("3. Intermediate");
            System.out.println("4. Average");
            System.out.println("5. Good");
            System.out.println("6. Expert");

            // Get the skill level choice from the user
            int skillChoice = getIntInput(scanner, 1, 6);
            String skillLevel = convertSkillChoiceToLevel(skillChoice);

            TournamentPlayer player = new TournamentPlayer(playerName, skillLevel);
            team.addPlayer(player);
            System.out.println(playerName + " joined team " + teamName + " with skill level " + skillLevel);
        }

        tournament.registerTeam(team);
    }

    // Helper method to convert skill choice to skill level string
    private static String convertSkillChoiceToLevel(int choice) {
        return switch (choice) {
            case 1 -> "Beginner";
            case 2 -> "Novice";
            case 3 -> "Intermediate";
            case 4 -> "Average";
            case 5 -> "Good";
            case 6 -> "Expert";
            default -> "Unknown";
        };
    }

    private static void viewUpcomingMatches(Tournament tournament) {
        System.out.println("Upcoming Matches:");
        boolean hasUpcomingMatches = false;
    
        for (Match match : tournament.getMatches()) {
            if (!match.isPlayed()) {
                System.out.println(match.getTeamA().getTeamName() + " vs " + match.getTeamB().getTeamName());
                hasUpcomingMatches = true;
            }
        }
    
        if (!hasUpcomingMatches) {
            System.out.println("There are no upcoming matches at the moment.");
        }
    }
    

    private static void playMatch(Scanner scanner, Tournament tournament) {
        System.out.println("Select a match to play:");

        // Filter unplayed matches
        List<Match> unplayedMatches = new ArrayList<>();
        for (Match match : tournament.getMatches()) {
            if (!match.isPlayed()) {
                unplayedMatches.add(match);
            }
        }

        // Check if there are unplayed matches
        if (unplayedMatches.isEmpty()) {
            System.out.println("No available matches to play at the moment.");
            return;
        }

        // Display the unplayed matches
        int index = 1;
        for (Match match : unplayedMatches) {
            System.out.println(index + ". " + match.getTeamA().getTeamName() + " vs " + match.getTeamB().getTeamName());
            index++;
        }

        // Get user input for match selection
        int choice = getIntInput(scanner, 1, unplayedMatches.size());
        Match selectedMatch = unplayedMatches.get(choice - 1);

        // Play the selected match
        selectedMatch.playMatch();
        tournament.updateTeamRanks();
        tournament.displayScoreSheet();
    }

    private static int getIntInput(Scanner scanner, int min, int max) {
        int input = -1;
        while (true) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private static double getDoubleInput(Scanner scanner) {
        double input = -1;
        while (true) {
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }
}
