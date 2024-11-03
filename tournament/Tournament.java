package tournament;

import belote.BeloteGame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a belote tournament.
 */
public class Tournament {
    private List<Team> teams;
    private Map<Team, Integer> scoreBoard;
    private boolean isOngoing;

    public Tournament() {
        this.teams = new ArrayList<>();
        this.scoreBoard = new HashMap<>();
        this.isOngoing = false;
    }

    // Getters and Setters
    public List<Team> getTeams() {
        return teams;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void registerTeam(Team team) {
        if (isOngoing) {
            System.out.println("The tournament has already started. No more teams can join.");
        } else if (teams.contains(team)) {
            System.out.println("Team " + team.getTeamName() + " is already registered.");
        } else {
            teams.add(team);
            scoreBoard.put(team, 0);
            System.out.println("Team " + team.getTeamName() + " has been registered for the tournament.");
        }
    }

    public void startTournament() {
        if (teams.size() < 2) {
            System.out.println("A tournament requires at least 2 teams.");
            return;
        }
        isOngoing = true;
        System.out.println("The belote tournament has started with " + teams.size() + " teams!");
    }

    public void playMatch(Team team1, Team team2) {
        if (!isOngoing) {
            System.out.println("The tournament hasn't started yet.");
            return;
        }

        BeloteGame game = new BeloteGame();
        game.addPlayer(team1.getPlayers().get(0));
        game.addPlayer(team1.getPlayers().get(1));
        game.addPlayer(team2.getPlayers().get(0));
        game.addPlayer(team2.getPlayers().get(1));

        game.startGame();
        game.playRound();

        // After the game, we will determine a random winner for simplicity here.
        int team1Score = team1.getScore();
        int team2Score = team2.getScore();

        // Assume some simple logic for winner determination
        if (team1Score > team2Score) {
            team1.addScore(3); // Winning team gets 3 points
            System.out.println("Team " + team1.getTeamName() + " wins the match.");
        } else if (team2Score > team1Score) {
            team2.addScore(3); // Winning team gets 3 points
            System.out.println("Team " + team2.getTeamName() + " wins the match.");
        } else {
            team1.addScore(1); // Draw, both teams get 1 point
            team2.addScore(1);
            System.out.println("The match ended in a draw.");
        }

        updateScoreBoard(team1);
        updateScoreBoard(team2);
    }

    public void updateScoreBoard(Team team) {
        scoreBoard.put(team, team.getScore());
        System.out.println("Scoreboard updated for team " + team.getTeamName());
    }

    public void displayScoreBoard() {
        System.out.println("Current Tournament Scoreboard:");
        for (Map.Entry<Team, Integer> entry : scoreBoard.entrySet()) {
            System.out.println(entry.getKey().getTeamName() + ": " + entry.getValue() + " points");
        }
    }

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
