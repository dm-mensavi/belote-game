package tournament;
import pubmanagement.Bartender;
import pubmanagement.Patronne;

import java.util.ArrayList;
import java.util.List;

/**
 * Class managing the tournament.
 */
public class Tournament {
    private String name;
    private double registrationFee;
    private Bartender bartender;
    private Patronne patronne;
    private List<Team> teams;
    private List<Match> matches;
    private boolean isStarted;
    private double totalFeesCollected;

    /**
     * Constructor for Tournament.
     * @param name The name of the tournament.
     * @param registrationFee The registration fee per team.
     * @param bartender The bartender responsible for the score sheet.
     * @param patronne The patronne of the bar.
     */
    public Tournament(String name, double registrationFee, Bartender bartender, Patronne patronne) {
        this.name = name;
        this.registrationFee = registrationFee;
        this.bartender = bartender;
        this.patronne = patronne;
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.isStarted = false;
        this.totalFeesCollected = 0;
    }

    // Getters

    public List<Team> getTeams() {
        return teams;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Registers a team for the tournament.
     * @param team The team to register.
     * @throws Exception If the tournament has already started.
     */
    public void registerTeam(Team team) throws Exception {
        if (isStarted) {
            throw new Exception("Cannot register team. Tournament has already started.");
        }
        teams.add(team);
        totalFeesCollected += registrationFee;
    }

    /**
     * Starts the tournament.
     * Generates the schedule of matches.
     * @throws Exception If there are less than two teams.
     */
    public void startTournament() throws Exception {
        if (teams.size() < 2) {
            throw new Exception("Cannot start tournament. At least two teams are required.");
        }
        isStarted = true;
        generateMatches();
    }

    /**
     * Generates all possible matches between teams.
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
     * Displays the current score sheet.
     */
    public void displayScoreSheet() {
        System.out.println("\nCurrent Score Sheet:");
        System.out.printf("%-10s", "Team");
        for (Team team : teams) {
            System.out.printf("%-10s", team.getTeamName());
        }
        System.out.printf("%-10s%-10s\n", "Total", "Rank");

        for (Team teamA : teams) {
            System.out.printf("%-10s", teamA.getTeamName());
            for (Team teamB : teams) {
                if (teamA == teamB) {
                    System.out.printf("%-10s", "-");
                } else {
                    Match match = findMatch(teamA, teamB);
                    if (match != null && match.isPlayed()) {
                        if (match.getTeamA() == teamA) {
                            System.out.printf("%-10d", match.getScoreA());
                        } else {
                            System.out.printf("%-10d", match.getScoreB());
                        }
                    } else {
                        System.out.printf("%-10s", "");
                    }
                }
            }
            System.out.printf("%-10d%-10d\n", teamA.getTotalPoints(), teamA.getRank());
        }
    }

    /**
     * Finds a match between two teams.
     * @param teamA The first team.
     * @param teamB The second team.
     * @return The Match object if found, null otherwise.
     */
    private Match findMatch(Team teamA, Team teamB) {
        for (Match match : matches) {
            if ((match.getTeamA() == teamA && match.getTeamB() == teamB) ||
                (match.getTeamA() == teamB && match.getTeamB() == teamA)) {
                return match;
            }
        }
        return null;
    }

    /**
     * Updates the ranks of teams based on their total points.
     */
    public void updateTeamRanks() {
        teams.sort((t1, t2) -> t2.getTotalPoints() - t1.getTotalPoints());
        int rank = 1;
        for (Team team : teams) {
            team.setRank(rank++);
        }
    }

    /**
     * Distributes the prize money at the end of the tournament.
     */
    public void distributePrizes() {
        if (!isStarted) {
            System.out.println("Tournament has not yet started or is already finished.");
            return;
        }
        isStarted = false;

        updateTeamRanks();
        Team winner = teams.get(0);
        double prize = totalFeesCollected * 0.5;
        double patronneShare = totalFeesCollected - prize;

        System.out.println("Distributing prizes:");
        System.out.println("Team " + winner.getTeamName() + " wins " + prize + " euros.");
        System.out.println("Patronne " + patronne.getNickname() + " receives " + patronneShare + " euros.");

        // Update winner's players' wallets
        for (TournamentPlayer player : winner.getPlayers()) {
            double share = prize / 2; // Assuming two players
            player.getHuman().setWallet(player.getHuman().getWallet() + share);
        }

        // Update patronne's wallet
        patronne.setWallet(patronne.getWallet() + patronneShare);
    }
}
