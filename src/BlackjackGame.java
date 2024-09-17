public class BlackjackGame {

    private final Deck deck;
    private final Player player;
    private final Player dealer;
    private Card hiddenCard; // Скритата карта на дилъра

    public BlackjackGame() {
        deck = new Deck();
        player = new Player("Player");
        dealer = new Player("Dealer");
    }

    public void startNewGame() {
        player.clearHand();
        dealer.clearHand();
        deck.shuffleDeck();
        player.addCardToHand(deck.dealCard());
        player.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());
        hiddenCard = deck.dealCard(); // Задаваме скритата карта
        dealer.addCardToHand(hiddenCard);
    }

    public void playerHits() {
        player.addCardToHand(deck.dealCard());
    }

    public void playerStands() {
        dealerTurn(); // Дилърът играе след като играчът реши да "stand"
    }

    public void dealerTurn() {
        while (dealer.calculateScore() < 17) {
            dealer.addCardToHand(deck.dealCard());
        }
    }

    public String determineWinner() {
        int playerScore = player.calculateScore();
        int dealerScore = dealer.calculateScore();

        if (player.isBusted()) {
            return "Dealer wins!";
        } else if (dealer.isBusted()) {
            return "Player wins!";
        } else if (playerScore > dealerScore) {
            return "Player wins!";
        } else if (dealerScore > playerScore) {
            return "Dealer wins!";
        } else {
            return "It's a tie!";
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Player getDealer() {
        return dealer;
    }

    public Deck getDeck() {
        return deck;
    }

    // Метод за разкриване на скритата карта на дилъра
    public void revealHiddenCard() {
        dealer.getHand().remove(hiddenCard);
        dealer.addCardToHand(hiddenCard);
        hiddenCard = null; // Изчистваме референцията, тъй като картата вече е разкрита
    }


    @Override
    public String toString() {
        return "Player: " + player + "\nDealer: " + dealer;
    }
}
