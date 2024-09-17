import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            System.out.println("No more cards in the deck! Reshuffling...");
            resetDeck(); // Автоматично презареждане на тестето
        }
        return cards.remove(cards.size() - 1);
    }

    public void resetDeck() {
        initializeDeck();
        shuffleDeck();
    }

    public int getDeckSize() {
        return cards.size();
    }
}
