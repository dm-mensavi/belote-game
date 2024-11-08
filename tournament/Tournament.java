package tournament;

import belote.BeloteGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing a belote tournament.
 */
public class Tournament {
    private List<Team> teams;
    private boolean isOngoing;

    /**
     * Constructor for the Tournament class.
     */
    public Tournament() {
        this.teams = new ArrayList<>();
        this.isOngoing = false;
    }

    // Getters and Setters
    public List<Team> getTeams() {
        return teams;
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
     * Starts the tournament if there are enough teams.
     */
    public void startTournament() {
        if (teams.size() < 2) {
            System.out.println("A tournament requires at least 2 teams.");
            return;
        }
        isOngoing = true;
        System.out.println("The belote tournament has started with " + teams.size() + " teams!");
    }

    /**
     * Plays a match between two teams and updates their scores.
     * @param team1 the first team.
     * @param team2 the second team.
     */
    public void playMatch(Team team1, Team team2) {
        if (!isOngoing) {
            System.out.println("The tournament hasn't started yet.");
            return;
        }

        BeloteGame game = new BeloteGame();
        Scanner scanner = new Scanner(System.in);
        game.addPlayerToTeam(scanner, team1.getPlayers().get(0));
        game.addPlayerToTeam(scanner, team1.getPlayers().get(1));
        game.addPlayerToTeam(scanner, team2.getPlayers().get(0));
        game.addPlayerToTeam(scanner, team2.getPlayers().get(1));
            

        game.startGame();
        game.playRound();

        int team1Score = team1.getScore();
        int team2Score = team2.getScore();

        // Determine winner and award points
        if (team1Score > team2Score) {
            team1.addScore(3);
            System.out.println("Team " + team1.getTeamName() + " wins the match.");
        } else if (team2Score > team1Score) {
            team2.addScore(3);
            System.out.println("Team " + team2.getTeamName() + " wins the match.");
        } else {
            team1.addScore(1);
            team2.addScore(1);
            System.out.println("The match ended in a draw.");
        }
    }

    /**
     * Displays the current scoreboard for the tournament.
     */
    public void displayScoreBoard() {
        System.out.println("Current Tournament Scoreboard:");
        for (Team team : teams) {
            System.out.println(team.getTeamName() + ": " + team.getScore() + " points");
        }
    }

    /**
     * Determines and returns the winner of the tournament.
     * @return the winning team.
     */
    public Team getWinner() {
        if (!isOngoing) {
            System.out.println("The tournament hasn't started yet.");
            return null;
        }

        Team winner = null;
        int maxScore = -1;

        for (Team team : teams) {
            if (team.getScore() > maxScore) {
                maxScore = team.getScore();
                winner = team;
            }
        }

        if (winner != null) {
            System.out.println("The tournament winner is: " + winner.getTeamName() + " with " + maxScore + " points!");
        }
        return winner;
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
        System.out.println("The belote tournament has ended.");
        Team winner = getWinner();
        if (winner != null) {
            System.out.println("Congratulations to team " + winner.getTeamName() + " for winning the tournament!");
        }
    }
}
