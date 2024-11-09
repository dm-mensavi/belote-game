package tournament;
import belote.Player;

/**
 * Class representing a player in the tournament.
 */
public class TournamentPlayer extends Player {
    private String skillLevel;
    private int popularity;
    private int matchesWon;
    private int matchesLost;

    /**
     * Constructor for TournamentPlayer without Human object.
     * @param name The name of the player.
     * @param skillLevel The skill level of the player.
     */
    public TournamentPlayer(String name, String skillLevel) {
        super(name);
        this.skillLevel = skillLevel.toLowerCase();
        this.popularity = 0;
        this.matchesWon = 0;
        this.matchesLost = 0;
    }

    // Existing getters and setters...

    public String getSkillLevel() {
        return skillLevel;
    }

    public int getPopularity() {
        return popularity;
    }

    public void increasePopularity() {
        popularity++;
    }

    public void recordWin() {
        matchesWon++;
    }

    public void recordLoss() {
        matchesLost++;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public int getMatchesLost() {
        return matchesLost;
    }

    /**
     * Displays the player's statistics.
     */
    public void displayStats() {
        System.out.println("Player: " + getName());
        System.out.println("Skill Level: " + skillLevel);
        System.out.println("Popularity: " + popularity);
        System.out.println("Matches Won: " + matchesWon);
        System.out.println("Matches Lost: " + matchesLost);
    }

}
