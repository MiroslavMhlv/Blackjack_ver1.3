public class Card {

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

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

    // Метод за получаване на късото име на ранга (2, 3, ..., J, Q, K, A)
    public String getRankShortName() {
        return switch (rank) {
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE -> "9";
            case TEN -> "10";
            case JACK -> "J";
            case QUEEN -> "Q";
            case KING -> "K";
            case ACE -> "A";
        };
    }

    // Метод за получаване на късото име на цвета (C, D, H, S)
    public String getSuitShortName() {
        return switch (suit) {
            case CLUBS -> "C";
            case DIAMONDS -> "D";
            case HEARTS -> "H";
            case SPADES -> "S";
        };
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
