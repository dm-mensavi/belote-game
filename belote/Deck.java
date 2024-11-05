package belote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.Suit;
import utils.Rank;

public class Deck {
    private List<Card> cards;

    public Deck() {
        resetDeck();
    }

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
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (rank.getValue() >= 7) { // Only cards from 7 to Ace are used in belote
                    cards.add(new Card(suit, rank));
                }
            }
        }
        System.out.println("Deck reset.");
    }

    public List<Card> dealHand(int numCards) {
        if (cards.size() < numCards) {
            System.out.println("Not enough cards left in the deck to deal a full hand. Reshuffling the deck.");
            resetDeck();
            shuffle();
        }
        List<Card> hand = new ArrayList<>(cards.subList(0, numCards));
        cards.subList(0, numCards).clear();
        return hand;
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            System.out.println("No more cards to deal.");
            return null;
        }
        return cards.remove(0);
    }
}
