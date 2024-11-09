package belote;

import utils.Suit;
import utils.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards;
    private Random random = new Random();

    public Deck() {
        resetDeck();
    }

    public void shuffle() {
        Collections.shuffle(cards);
        System.out.println("Deck shuffled.");
    }

    public void cut() {
        int cutPoint = random.nextInt(cards.size());
        List<Card> topHalf = new ArrayList<>(cards.subList(0, cutPoint));
        List<Card> bottomHalf = new ArrayList<>(cards.subList(cutPoint, cards.size()));
        cards.clear();
        cards.addAll(bottomHalf);
        cards.addAll(topHalf);
        System.out.println("Deck cut at position " + cutPoint + ".");
    }

    public void resetDeck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        System.out.println("Deck reset.");
    }

    public List<Card> dealCards(int numCards) {
        if (cards.size() < numCards) {
            System.out.println("Not enough cards left in the deck to deal a full hand. Reshuffling the deck.");
            resetDeck();
            shuffle();
        }
        List<Card> hand = new ArrayList<>(cards.subList(0, numCards));
        cards.subList(0, numCards).clear();
        return hand;
    }

    public Card flipCard() {
        if (cards.isEmpty()) {
            System.out.println("No more cards to flip.");
            return null;
        }
        Card card = cards.remove(0);
        System.out.println("Card flipped: " + card);
        return card;
    }

    public void setTrumpSuit(Suit trumpSuit) {
        for (Card card : cards) {
            if (card.getSuit() == trumpSuit) {
                card.setTrump(true);
            } else {
                card.setTrump(false);
            }
        }
    }
}
