public class Main {
    public static void main(String[] args) {
        // Създаване на нова игра
        BlackjackGame game = new BlackjackGame();

        // Ръчно задаване на картите за тест
        Card playerCard1 = new Card(Card.Suit.SPADES, Card.Rank.ACE);   // Aсо спатия
        Card playerCard2 = new Card(Card.Suit.HEARTS, Card.Rank.KING);  // Крал купа

        Card dealerCard1 = new Card(Card.Suit.CLUBS, Card.Rank.ACE);    // Асо клуб
        Card dealerCard2 = new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN); // Дама каро

        // Изчистване на текущите ръце
        game.getPlayer().clearHand();
        game.getDealer().clearHand();

        // Добавяне на картите към ръцете
        game.getPlayer().addCardToHand(playerCard1);
        game.getPlayer().addCardToHand(playerCard2);

        game.getDealer().addCardToHand(dealerCard1);
        game.getDealer().addCardToHand(dealerCard2);

        // Проверка на резултата след задаване на картите
        if (game.getPlayer().calculateScore() == 21 && game.getPlayer().getHand().size() == 2
                && game.getDealer().calculateScore() == 21 && game.getDealer().getHand().size() == 2) {
            System.out.println("Both players have Blackjack! It's a tie!");
        } else if (game.getPlayer().calculateScore() == 21 && game.getPlayer().getHand().size() == 2) {
            System.out.println("Player has Blackjack! Player wins!");
        } else if (game.getDealer().calculateScore() == 21 && game.getDealer().getHand().size() == 2) {
            System.out.println("Dealer has Blackjack! Dealer wins!");
        } else {
            System.out.println("No Blackjack scenario.");
        }
    }
}
