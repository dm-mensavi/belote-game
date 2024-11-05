package belote;

import utils.Suit;
import utils.Rank;

/**
 * Class representing a card in the belote deck.
 */
public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String getCardInfo() {
        return rank + " of " + suit;
    }

    @Override
    public String toString() {
        return getCardInfo();
    }
}
