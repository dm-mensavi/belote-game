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

    public void addPlayer(Player player) {
        if (players.size() < 2) {
            players.add(player);
            System.out.println(player.getName() + " joined team " + teamName);
        } else {
            System.out.println("Team " + teamName + " already has 2 players.");
        }
    }

    public void addScore(int points) {
        score += points;
        System.out.println("Team " + teamName + " earned " + points + " points. Total score: " + score);
    }

    public void resetScore() {
        score = 0;
        System.out.println("Team " + teamName + "'s score has been reset.");
    }

    public String getTeamInfo() {
        StringBuilder info = new StringBuilder("Team " + teamName + " with players: ");
        for (Player player : players) {
            info.append(player.getName()).append(" ");
        }
        return info.toString();
    }
}
