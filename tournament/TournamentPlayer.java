package tournament;
import pubmanagement.Human;
import belote.Player;

/**
 * Class representing a player in the tournament.
 */
public class TournamentPlayer extends Player {
    private Human human;
    private String skillLevel;
    private int popularity;
    private int matchesWon;
    private int matchesLost;

    /**
     * Constructor for TournamentPlayer.
     * @param human The human (Client or Server) participating as a player.
     * @param skillLevel The skill level of the player.
     */
    public TournamentPlayer(Human human, String skillLevel) {
        super(human.getNickname());
        this.human = human;
        this.skillLevel = skillLevel;
        this.popularity = 0;
        this.matchesWon = 0;
        this.matchesLost = 0;
    }

    // Getters and Setters

    public Human getHuman() {
        return human;
    }

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
