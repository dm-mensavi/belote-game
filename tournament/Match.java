package tournament;

/**
 * Class representing a match between two teams.
 */
public class Match {
    private Team teamA;
    private Team teamB;
    private int scoreA; // Sets won by team A
    private int scoreB; // Sets won by team B
    private boolean isPlayed;

    /**
     * Constructor for Match.
     * @param teamA The first team.
     * @param teamB The second team.
     */
    public Match(Team teamA, Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.scoreA = 0;
        this.scoreB = 0;
        this.isPlayed = false;
    }

    // Existing getters...

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    /**
     * Plays the match.
     * Simulates the match and updates scores.
     */
    public void playMatch() {
        // Simulate the match logic here
        // For simplicity, we'll randomly decide the winner
        int setsToWin = 2;
        scoreA = 0;
        scoreB = 0;

        while (scoreA < setsToWin && scoreB < setsToWin) {
            // Simulate a set
            double random = Math.random();
            if (random < 0.5) {
                scoreA++;
            } else {
                scoreB++;
            }

            // At the end of each set, losing players buy drinks for winners
            if (scoreA + scoreB > 0) {
                if (scoreA > scoreB) {
                    buyDrinks(teamB, teamA);
                } else {
                    buyDrinks(teamA, teamB);
                }
            }
        }

        isPlayed = true;
        updateTeamPoints();
        increasePopularityOfWinners();
    }

    /**
     * Updates team points based on the match result.
     */
    private void updateTeamPoints() {
        if (scoreA > scoreB) {
            teamA.addPoints(3);
            if (scoreB == 1) {
                teamB.addPoints(1);
            }
        } else {
            teamB.addPoints(3);
            if (scoreA == 1) {
                teamA.addPoints(1);
            }
        }
    }

    /**
     * Increases the popularity of the winning team's players.
     */
    private void increasePopularityOfWinners() {
        if (scoreA > scoreB) {
            for (TournamentPlayer player : teamA.getPlayers()) {
                player.increasePopularity();
            }
        } else {
            for (TournamentPlayer player : teamB.getPlayers()) {
                player.increasePopularity();
            }
        }
    }

    /**
     * Simulates buying drinks by the losing team.
     * @param losers The losing team.
     * @param winners The winning team.
     */
    private void buyDrinks(Team losers, Team winners) {
        System.out.println("Team " + losers.getTeamName() + " buys drinks for team " + winners.getTeamName());
        // Since we don't have a Bar or drinks, we can just display this message.
    }

    /**
     * Displays the match details.
     */
    public void displayMatchDetails() {
        System.out.println("Match between " + teamA.getTeamName() + " and " + teamB.getTeamName());
        if (isPlayed) {
            System.out.println("Score: " + teamA.getTeamName() + " " + scoreA + " - " + scoreB + " " + teamB.getTeamName());
        } else {
            System.out.println("Match not yet played.");
        }
    }
}
