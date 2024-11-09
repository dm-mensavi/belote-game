package tournament;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a team in the tournament.
 */
public class Team {
    private String teamName;
    private List<TournamentPlayer> players;
    private int totalPoints;
    private int rank;

    /**
     * Constructor for Team.
     * @param teamName The name of the team.
     */
    public Team(String teamName) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
        this.totalPoints = 0;
        this.rank = 0;
    }

    // Getters and Setters

    public String getTeamName() {
        return teamName;
    }

    public List<TournamentPlayer> getPlayers() {
        return players;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Adds a player to the team.
     * @param player The player to add.
     * @throws Exception If the team already has two players.
     */
    public void addPlayer(TournamentPlayer player) throws Exception {
        if (players.size() < 2) {
            players.add(player);
        } else {
            throw new Exception("Team " + teamName + " already has two players.");
        }
    }

    /**
     * Adds points to the team's total points.
     * @param points The points to add.
     */
    public void addPoints(int points) {
        totalPoints += points;
    }

    /**
     * Displays the team's information.
     */
    public void displayTeamInfo() {
        System.out.println("Team: " + teamName);
        System.out.println("Players:");
        for (TournamentPlayer player : players) {
            System.out.println("- " + player.getName());
        }
        System.out.println("Total Points: " + totalPoints);
        System.out.println("Rank: " + rank);
    }
}
