package belote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.Suit;
import utils.Rank;

/**
 * Class representing the deck of cards for a belote game.
 */
public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        // Initialize the deck with 32 cards (belote uses a 32-card deck)
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (rank.getValue() >= 7) { // Only cards from 7 to Ace are used in belote
                    cards.add(new Card(suit, rank));
                }
            }
        }
    }

    // Methods
    public void shuffle() {
        Collections.shuffle(cards);
        System.out.println("Deck shuffled.");
    }

    public void cut() {
        int cutPoint = cards.size() / 2;
        List<Card> topHalf = new ArrayList<>(cards.subList(0, cutPoint));
        List<Card> bottomHalf = new ArrayList<>(cards.subList(cutPoint, cards.size()));
        cards.clear();
        cards.addAll(bottomHalf);
        cards.addAll(topHalf);
        System.out.println("Deck cut.");
    }

    public void resetDeck() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (rank.getValue() >= 7) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
        System.out.println("Deck reset.");
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            System.out.println("No more cards to deal.");
            return null;
        }
        return cards.remove(0);
    }

    public List<Card> dealHand(int numCards) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            hand.add(dealCard());
        }
        return hand;
    }
}
