package tournament;


import belote.Player;
import tournament.Team;
import tournament.Tournament;


import java.util.Scanner;

public class TournamentEnvironment {
    public static void startTournament() {
        Scanner scanner = new Scanner(System.in);
        Tournament tournament = new Tournament();

        System.out.println("Starting the Belote Tournament Setup...");
        
        // Register Teams and Players
        for (int i = 1; i <= 2; i++) {
            System.out.print("Enter name for Team " + i + ": ");
            String teamName = scanner.nextLine();
            Team team = new Team(teamName);

            for (int j = 1; j <= 2; j++) {
                System.out.print("Enter player " + j + " name for " + teamName + ": ");
                String playerName = scanner.nextLine();
                System.out.print("Enter skill level (beginner, average, expert): ");
                String skillLevel = scanner.nextLine();
                
                Player player = new Player(playerName, teamName, skillLevel);
                team.addPlayer(player);
            }

            tournament.registerTeam(team);
        }

        // Start Tournament
        tournament.startTournament();

        // Play Matches
        boolean tournamentRunning = true;
        while (tournamentRunning) {
            System.out.println("\nTournament Menu:");
            System.out.println("1. Play Match");
            System.out.println("2. View Scoreboard");
            System.out.println("3. End Tournament");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> playMatch(scanner, tournament);
                case 2 -> tournament.displayScoreBoard();
                case 3 -> {
                    tournament.endTournament();
                    tournamentRunning = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void playMatch(Scanner scanner, Tournament tournament) {
        System.out.println("Select two teams for the match:");
        
        // Display available teams
        for (int i = 0; i < tournament.getTeams().size(); i++) {
            System.out.println((i + 1) + ". " + tournament.getTeams().get(i).getTeamName());
        }

        int team1Index = scanner.nextInt() - 1;
        int team2Index = scanner.nextInt() - 1;
        scanner.nextLine();  // Consume newline

        if (team1Index < 0 || team1Index >= tournament.getTeams().size() ||
            team2Index < 0 || team2Index >= tournament.getTeams().size() ||
            team1Index == team2Index) {
            System.out.println("Invalid team selection. Please try again.");
            return;
        }

        Team team1 = tournament.getTeams().get(team1Index);
        Team team2 = tournament.getTeams().get(team2Index);

        tournament.playMatch(team1, team2);
    }
}
