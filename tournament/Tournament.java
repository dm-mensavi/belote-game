package tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing a belote tournament.
 */
public class Tournament {
    private List<Team> teams;
    private List<Match> matches;
    private boolean isOngoing;

    /**
     * Constructor for the Tournament class.
     */
    public Tournament() {
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.isOngoing = false;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    /**
     * Registers a team for the tournament if it hasn't started and if the team is unique.
     * @param team the team to register.
     */
    public void registerTeam(Team team) {
        if (isOngoing) {
            System.out.println("The tournament has already started. No more teams can join.");
        } else if (teams.contains(team)) {
            System.out.println("Team " + team.getTeamName() + " is already registered.");
        } else {
            teams.add(team);
            System.out.println("Team " + team.getTeamName() + " has been registered for the tournament.");
        }
    }

    /**
     * Starts the tournament by generating matches if there are enough teams.
     */
    public void startTournament(Scanner scanner) throws Exception {
        while (teams.size() < 2) {
            System.out.println("A tournament requires at least 2 teams.");
            System.out.println("Please register a new team.");
    
            // Register a new team
            System.out.print("Enter team name: ");
            String teamName = scanner.nextLine();
            Team team = new Team(teamName);
    
            // Add two players to the team
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
    
            registerTeam(team);
        }
    
        isOngoing = true;
        generateMatches();
        System.out.println("The belote tournament has started with " + teams.size() + " teams!");
    }
    
    // Helper method to convert skill choice to skill level string
    private String convertSkillChoiceToLevel(int choice) {
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
    
    // Method to handle integer input with range checking
    private int getIntInput(Scanner scanner, int min, int max) {
        int input;
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
    
    /**
     * Generates all matches for the tournament.
     */
    private void generateMatches() {
        matches.clear();
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                matches.add(new Match(teams.get(i), teams.get(j)));
            }
        }
    }

    /**
     * Displays the score sheet for the tournament.
     */
    public void displayScoreSheet() {
        System.out.println("Current Tournament Score Sheet:");
        for (Team team : teams) {
            System.out.println(team.getTeamName() + ": " + team.getScore() + " points");
        }
    }

    /**
     * Updates the ranks of the teams based on their scores.
     */
    public void updateTeamRanks() {
        teams.sort((t1, t2) -> Integer.compare(t2.getScore(), t1.getScore()));
        System.out.println("Team ranks updated.");
    }

    /**
     * Ends the tournament and announces the winner.
     */
    public void endTournament() {
        if (!isOngoing) {
            System.out.println("The tournament hasn't started yet.");
            return;
        }
        isOngoing = false;
        Team winner = getWinner();
        if (winner != null) {
            System.out.println("The tournament winner is: " + winner.getTeamName() + " with " + winner.getScore() + " points!");
        }
    }

    /**
     * Determines and returns the winner of the tournament.
     * @return the winning team.
     */
    public Team getWinner() {
        if (teams.isEmpty()) {
            return null;
        }
        return teams.get(0);
    }
}
