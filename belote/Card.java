package belote;

import utils.Suit;
import utils.Rank;

public class Card {
    private final Suit suit;
    private final Rank rank;
    private boolean isTrump; // Indicates if the card is a trump card

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isTrump = false;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setTrump(boolean isTrump) {
        this.isTrump = isTrump;
    }

    public boolean isTrump() {
        return isTrump;
    }

    public int getCardPoints() {
        return rank.getPoints();
    }

    @Override
    public String toString() {
        return rank + " of " + suit + (isTrump ? " (Trump)" : "");
    }
}
