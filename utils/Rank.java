package utils;

public enum Rank {
    SEVEN(0, "7"),
    EIGHT(0, "8"),
    NINE(0, "9"),
    JACK(2, "Jack"),
    QUEEN(3, "Queen"),
    KING(4, "King"),
    TEN(10, "10"),
    ACE(11, "Ace");

    private final int points;
    private final String displayName;

    Rank(int points, String displayName) {
        this.points = points;
        this.displayName = displayName;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
