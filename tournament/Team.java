package tournament;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a team in the belote tournament.
 */
public class Team {
    private String teamName;
    private List<TournamentPlayer> players;
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

    public String getTeamName() {
        return teamName;
    }

    public List<TournamentPlayer> getPlayers() {
        return players;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        this.score += points;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void addPlayer(TournamentPlayer player) {
        if (players.size() < 2) {
            players.add(player);
            System.out.println(player.getName() + " joined team " + teamName);
        } else {
            System.out.println("Team " + teamName + " already has 2 players.");
        }
    }
}
