package tournament;
import pubmanagement.*;
import java.util.Scanner;




/**
 * Class providing the user interface for managing the tournament.
 */
public class TournamentEnvironment {
    public static void startTournament(Scanner scanner, Bar bar) {
        try {
            System.out.println("Tournament Setup:");
            System.out.print("Enter tournament name: ");
            String tournamentName = scanner.nextLine();
            System.out.print("Enter registration fee per team: ");
            double registrationFee = getDoubleInput(scanner);

            Tournament tournament = new Tournament(tournamentName, registrationFee, bar.getBartender(), bar.getOwner());

            // Registration phase
            boolean registrationOpen = true;
            while (registrationOpen) {
                System.out.println("\nRegistration Menu:");
                System.out.println("1. Register a Team");
                System.out.println("2. Start Tournament");
                System.out.println("3. Cancel Tournament");
                int choice = getIntInput(scanner, 1, 3);

                switch (choice) {
                    case 1:
                        registerTeam(scanner, bar, tournament);
                        break;
                    case 2:
                        tournament.startTournament();
                        registrationOpen = false;
                        break;
                    case 3:
                        System.out.println("Tournament canceled.");
                        return;
                }
            }

            // Tournament phase
            boolean tournamentOngoing = true;
            while (tournamentOngoing) {
                System.out.println("\nTournament Menu:");
                System.out.println("1. View Upcoming Matches");
                System.out.println("2. Play a Match");
                System.out.println("3. View Score Sheet");
                System.out.println("4. View Player Stats");
                System.out.println("5. End Tournament");
                int choice = getIntInput(scanner, 1, 5);

                switch (choice) {
                    case 1:
                        viewUpcomingMatches(tournament);
                        break;
                    case 2:
                        playMatch(scanner, tournament);
                        break;
                    case 3:
                        tournament.displayScoreSheet();
                        break;
                    case 4:
                        viewPlayerStats(scanner, tournament);
                        break;
                    case 5:
                        tournament.distributePrizes();
                        tournamentOngoing = false;
                        break;
                }
            }

            // Tournament summary
            displayTournamentSummary(tournament);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerTeam(Scanner scanner, Bar bar, Tournament tournament) {
        try {
            System.out.print("Enter team name: ");
            String teamName = scanner.nextLine();
            Team team = new Team(teamName);

            for (int i = 1; i <= 2; i++) {
                System.out.println("Registering player " + i + " for team " + teamName);
                TournamentPlayer player = createTournamentPlayer(scanner, bar);
                team.addPlayer(player);
            }

            tournament.registerTeam(team);
            System.out.println("Team " + teamName + " registered successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static TournamentPlayer createTournamentPlayer(Scanner scanner, Bar bar) throws Exception {
        System.out.print("Enter player nickname: ");
        String nickname = scanner.nextLine();

        // Search for the player in clients and servers
        Human human = null;
        for (Client client : bar.getClients()) {
            if (client.getNickname().equalsIgnoreCase(nickname)) {
                human = client;
                break;
            }
        }
        if (human == null) {
            for (Server server : bar.getServers()) {
                if (server.getNickname().equalsIgnoreCase(nickname)) {
                    human = server;
                    break;
                }
            }
        }
        if (human == null) {
            throw new Exception("Player not found among clients or servers.");
        }
        if (human instanceof Bartender || human instanceof Patronne) {
            throw new Exception("Bartender and Patronne cannot participate.");
        }

        System.out.print("Enter skill level (beginner, novice, intermediate, average, good, expert): ");
        String skillLevel = scanner.nextLine();

        return new TournamentPlayer(human, skillLevel);
    }

    private static void viewUpcomingMatches(Tournament tournament) {
        System.out.println("\nUpcoming Matches:");
        for (Match match : tournament.getMatches()) {
            if (!match.isPlayed()) {
                System.out.println(match.getTeamA().getTeamName() + " vs " + match.getTeamB().getTeamName());
            }
        }
    }

    private static void playMatch(Scanner scanner, Tournament tournament) {
        System.out.println("\nSelect a match to play:");
        int index = 1;
        for (Match match : tournament.getMatches()) {
            if (!match.isPlayed()) {
                System.out.println(index + ". " + match.getTeamA().getTeamName() + " vs " + match.getTeamB().getTeamName());
            }
            index++;
        }

        int choice = getIntInput(scanner, 1, index - 1);
        Match selectedMatch = tournament.getMatches().get(choice - 1);

        selectedMatch.playMatch();
        System.out.println("Match played:");
        selectedMatch.displayMatchDetails();

        // Bartender displays updated score sheet
        tournament.updateTeamRanks();
        tournament.displayScoreSheet();
    }

    private static void viewPlayerStats(Scanner scanner, Tournament tournament) {
        System.out.print("Enter player nickname to view stats: ");
        String nickname = scanner.nextLine();
        for (Team team : tournament.getTeams()) {
            for (TournamentPlayer player : team.getPlayers()) {
                if (player.getName().equalsIgnoreCase(nickname)) {
                    player.displayStats();
                    return;
                }
            }
        }
        System.out.println("Player not found in the tournament.");
    }

    private static void displayTournamentSummary(Tournament tournament) {
        System.out.println("\nTournament Summary:");
        System.out.println("Number of Players: " + (tournament.getTeams().size() * 2));
        // Assuming each match uses one deck of cards
        System.out.println("Number of Card Decks Used: " + tournament.getMatches().size());
        // Assuming drinks bought are equal to the total number of sets played
        int totalSets = 0;
        for (Match match : tournament.getMatches()) {
            if (match.isPlayed()) {
                totalSets += match.getScoreA() + match.getScoreB();
            }
        }
        System.out.println("Number of Drinks Consumed: " + totalSets);
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
