import java.util.ArrayList;
import java.util.List;

public class Player {

    private final List<Card> hand;  // Списък с картите в ръката на играча
    private final String name;  // Име на играча

    // Конструктор за създаване на нов играч
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    // Метод за добавяне на карта към ръката на играча
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    // Метод за получаване на името на играча
    public String getName() {
        return name;
    }

    // Метод за получаване на текущата ръка на играча
    public List<Card> getHand() {
        return hand;
    }

    // Метод за изчисляване на текущите точки на играча
    public int calculateScore() {
        int score = 0;
        int aceCount = 0;

        for (Card card : hand) {
            switch (card.getRank()) {
                case TWO -> score += 2;
                case THREE -> score += 3;
                case FOUR -> score += 4;
                case FIVE -> score += 5;
                case SIX -> score += 6;
                case SEVEN -> score += 7;
                case EIGHT -> score += 8;
                case NINE -> score += 9;
                case TEN, JACK, QUEEN, KING -> score += 10;
                case ACE -> {
                    score += 11;
                    aceCount++;
                }
            }
        }

        // Ако имаме асове и резултатът надвишава 21, броим асовете за 1 точка
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    // Метод за проверка дали играчът е преминал 21 точки
    public boolean isBusted() {
        return calculateScore() > 21;
    }

    // Метод за изчистване на ръката на играча
    public void clearHand() {
        hand.clear();
    }


    // Метод за представяне на ръката на играча в текстов формат
    @Override
    public String toString() {
        return name + "'s hand: " + hand + " (Score: " + calculateScore() + ")";
    }

    public int calculateVisibleScorе() {
        int score = 0;
        // Започваме от втория елемент (индекс 1), за да не включим скритата карта
        for (int i = 1; i < hand.size(); i++) {
            Card card = hand.get(i);
            score += switch (card.getRank()) {
                case TWO -> 2;
                case THREE -> 3;
                case FOUR -> 4;
                case FIVE -> 5;
                case SIX -> 6;
                case SEVEN -> 7;
                case EIGHT -> 8;
                case NINE -> 9;
                case TEN, JACK, QUEEN, KING -> 10;
                case ACE -> 11;
            };
        }
        return score;
    }
}
