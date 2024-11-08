package tournament;

import belote.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a team in the belote tournament.
 */
public class Team {
    private String teamName;
    private List<Player> players;
    private int score;

    /**
     * Constructor for the Team class.
     * @param teamName the name of the team.
     */
    public Team(String teamName) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
        this.score = 0;
    }

    // Getters and Setters
    public String getTeamName() {
        return teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getScore() {
        return score;
    }

    /**
     * Adds a player to the team if there are fewer than 2 players.
     * @param player the player to add.
     */
    public void addPlayer(Player player) {
        if (players.size() < 2) {
            players.add(player);
            System.out.println(player.getName() + " joined team " + teamName);
        } else {
            System.out.println("Team " + teamName + " already has 2 players.");
        }
    }

    /**
     * Adds points to the team's score.
     * @param points the points to add.
     */
    public void addScore(int points) {
        score += points;
        System.out.println("Team " + teamName + " earned " + points + " points. Total score: " + score);
    }

    /**
     * Resets the team's score.
     */
    public void resetScore() {
        score = 0;
        System.out.println("Team " + teamName + "'s score has been reset.");
    }

    /**
     * Gets information about the team.
     * @return team info as a string.
     */
    public String getTeamInfo() {
        StringBuilder info = new StringBuilder("Team " + teamName + " with players: ");
        for (Player player : players) {
            info.append(player.getName()).append(" ");
        }
        return info.toString();
    }
}
