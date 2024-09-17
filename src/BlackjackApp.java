import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.InputStream;

public class BlackjackApp extends Application {

    private BlackjackGame game;
    private HBox playerCardsBox;
    private HBox dealerCardsBox;
    private Label statusLabel;
    private Label playerScoreLabel;
    private Label dealerScoreLabel;
    private Button hitButton;
    private Button standButton;
    private Button newGameButton;
    private Button quitButton; // Бутон за изход
    private boolean dealerCardRevealed;

    @Override
    public void start(Stage primaryStage) {
        game = new BlackjackGame();

        // Създаване на основната структура на потребителския интерфейс
        VBox root = new VBox(15); // Създаване на VBox с отстояние 10 пиксела
        root.setAlignment(Pos.CENTER); // Центриране на всички елементи във VBox
        HBox buttonsBox = new HBox(5); // Създаване на HBox за бутоните с отстояние 10 пиксела
        buttonsBox.setAlignment(Pos.CENTER); // Центриране на бутоните
        playerCardsBox = new HBox(10); // Създаване на HBox за картите на играча
        playerCardsBox.setAlignment(Pos.CENTER); // Центриране на картите на играча
        dealerCardsBox = new HBox(10); // Създаване на HBox за картите на дилъра
        dealerCardsBox.setAlignment(Pos.CENTER); // Центриране на картите на дилъра

        // Настройки на стил за надписите
        statusLabel = new Label("Welcome to Blackjack!");
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Удебелен шрифт, 18 пиксела

        playerScoreLabel = new Label("Player Score: 0");
        playerScoreLabel.setFont(Font.font("Impact", FontWeight.BOLD, 16)); // Удебелен шрифт, 16 пиксела

        dealerScoreLabel = new Label("Dealer Score: 0");
        dealerScoreLabel.setFont(Font.font("Impact", FontWeight.BOLD, 16)); // Удебелен шрифт, 16 пиксела

        Label playerLabel = new Label("Player's Hand");
        playerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Удебелен шрифт, 14 пиксела

        Label dealerLabel = new Label("Dealer's Hand");
        dealerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Удебелен шрифт, 14 пиксела

        // Създаване на бутоните и задаване на техните действия
        hitButton = new Button("    HIT    ");
        standButton = new Button("  STAND  ");
        newGameButton = new Button("NEW GAME");
        quitButton = new Button("   QUIT   "); // Създаване на бутон за изход

        hitButton.setOnAction(event -> {
            game.playerHits();
            updateGameView();
            checkPlayerStatus();
        });

        standButton.setOnAction(event -> {
            playerStands();
        });

        newGameButton.setOnAction(event -> {
            startNewGame();
        });

        quitButton.setOnAction(event -> {
            Platform.exit(); // Затваряне на приложението
        });

        // Добавяне на компонентите към потребителския интерфейс
        buttonsBox.getChildren().addAll(hitButton, standButton, newGameButton, quitButton);

        root.getChildren().addAll(statusLabel, dealerLabel, dealerScoreLabel, dealerCardsBox,
                playerLabel, playerScoreLabel, playerCardsBox, buttonsBox);

        // Настройка на фона с изображение
        setBackgroundImage(root, "/images/blackjack_table.png"); // Укажете пътя до вашето изображение

        // Настройки на сцената и прозореца
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("BlackJack Game (ver 1.3)");
        primaryStage.setScene(scene);
        primaryStage.show();

        startNewGame();
    }

    // Метод за настройка на фона с изображение
    private void setBackgroundImage(VBox root, String imagePath) {
        InputStream backgroundStream = getClass().getResourceAsStream(imagePath);
        if (backgroundStream != null) {
            Image backgroundImage = new Image(backgroundStream);
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true); // Задаване на размера да запълва
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            root.setBackground(new Background(background));
        } else {
            System.out.println("Error: Could not find background image at path: " + imagePath);
        }
    }

    // Метод за стартиране на нова игра
    private void startNewGame() {
        game.startNewGame();
        dealerCardRevealed = false; // Скритата карта е обратно скрита
        enableButtons();
        statusLabel.setText("Please make a decision.");
        updateGameView();

        checkForInitialBlackjack(); // Проверка за Blackjack в началото
    }

    // Метод за проверка на статуса на играча
    private void checkPlayerStatus() {
        if (game.getPlayer().calculateScore() == 21) {
            statusLabel.setText(game.getPlayer().getName() + " has 21!");
            playerStands();
        } else if (game.getPlayer().isBusted()) {
            statusLabel.setText("Player is busted. Dealer wins!");
            dealerCardRevealed = true; // Разкриване на картата на дилъра
            revealDealerCards(); // Разкриване на всички карти на дилъра
            disableButtons(); // Деактивиране на бутоните, защото играта приключва
        }
    }

    // Метод за проверка на Blackjack в началото на играта
    private void checkForInitialBlackjack() {
        boolean playerHasBlackjack = game.getPlayer().calculateScore() == 21 && game.getPlayer().getHand().size() == 2;
        boolean dealerHasBlackjack = game.getDealer().calculateScore() == 21 && game.getDealer().getHand().size() == 2;

        if (playerHasBlackjack && dealerHasBlackjack) {
            // И двамата имат Blackjack
            dealerCardRevealed = true;
            updateGameView();
            statusLabel.setText("Both players have Blackjack! It's a tie!");
            disableButtons();
        } else if (playerHasBlackjack) {
            // Само играчът има Blackjack
            dealerCardRevealed = true;
            updateGameView();
            statusLabel.setText("Player has Blackjack! Player wins!");
            disableButtons();
        } else if (dealerHasBlackjack) {
            // Само дилърът има Blackjack, но изчакваме играчът да направи своя ход
            dealerCardRevealed = false; // Все още не разкриваме картата на дилъра
        }
    }

    // Метод за действия след като играчът избере "Stand" или има 21 точки
    private void playerStands() {
        dealerCardRevealed = true; // Дилърът разкрива картата
        revealDealerCards();

        if (game.getDealer().calculateScore() == 21 && game.getDealer().getHand().size() == 2) {
            // Дилърът има Blackjack, проверяваме резултата
            if (game.getPlayer().calculateScore() == 21 && game.getPlayer().getHand().size() > 2) {
                // Играчът има 21 с повече от две карти
                statusLabel.setText("The Dealer has Blackjack. Better luck next time!");
            } else {
                statusLabel.setText("The Dealer has Blackjack. Better luck next time!");
            }
            disableButtons();
        } else {
            dealerPlays();
        }
    }

    // Метод за действия на дилъра
    private void dealerPlays() {
        while (game.getDealer().calculateScore() < 17) {
            game.getDealer().addCardToHand(game.getDeck().dealCard());
        }

        updateGameView();

        if (game.getDealer().isBusted()) {
            statusLabel.setText("Dealer is busted! Player wins!");
        } else {
            String result = game.determineWinner();
            statusLabel.setText(result);
        }

        disableButtons();
    }

    // Метод за разкриване на всички карти на дилъра
    private void revealDealerCards() {
        game.revealHiddenCard(); // Разкрива скритата карта на дилъра
        updateGameView();
    }

    // Метод за обновяване на визуализацията на играта
    private void updateGameView() {
        playerCardsBox.getChildren().clear();
        dealerCardsBox.getChildren().clear();

        playerCardsBox.setSpacing(-55);
        dealerCardsBox.setSpacing(-55);

        playerScoreLabel.setText("Player Score: " + game.getPlayer().calculateScore());

        int dealerDisplayedScore = dealerCardRevealed ? game.getDealer().calculateScore() : game.getDealer().calculateVisibleScorе();
        dealerScoreLabel.setText("Dealer Score: " + dealerDisplayedScore);

        if (dealerCardRevealed) {
            for (Card card : game.getDealer().getHand()) {
                ImageView cardImageView = createCardImageView(card);
                dealerCardsBox.getChildren().add(cardImageView);
            }
        } else {
            dealerCardsBox.getChildren().add(createBackOfCardImageView());
            for (int i = 1; i < game.getDealer().getHand().size(); i++) {
                ImageView cardImageView = createCardImageView(game.getDealer().getHand().get(i));
                dealerCardsBox.getChildren().add(cardImageView);
            }
        }

        for (Card card : game.getPlayer().getHand()) {
            ImageView cardImageView = createCardImageView(card);
            playerCardsBox.getChildren().add(cardImageView);
        }
    }

    // Метод за създаване на ImageView за карта
    private ImageView createCardImageView(Card card) {
        String rankShortName = card.getRankShortName();
        String cardImagePath = "/images/" + rankShortName + "-" + card.getSuitShortName() + ".png";
        InputStream imageStream = getClass().getResourceAsStream(cardImagePath);

        if (imageStream == null) {
            System.out.println("Error: Could not find image at path: " + cardImagePath);
            return new ImageView();
        }

        Image cardImage = new Image(imageStream);
        ImageView cardImageView = new ImageView(cardImage);

        // Задаване на размер на картите
        cardImageView.setFitWidth(80);
        cardImageView.setPreserveRatio(true);

        return cardImageView;
    }

    // Метод за създаване на ImageView за гърба на карта
    private ImageView createBackOfCardImageView() {
        String backImagePath = "/images/BACK.png";
        InputStream backImageStream = getClass().getResourceAsStream(backImagePath);

        if (backImageStream == null) {
            System.out.println("Error: Could not find back image at path: " + backImagePath);
            return new ImageView();
        }

        Image backImage = new Image(backImageStream);
        ImageView backImageView = new ImageView(backImage);

        // Задаване на размер на гърба на картите
        backImageView.setFitWidth(80);
        backImageView.setPreserveRatio(true);

        return backImageView;
    }

    // Метод за деактивиране на бутоните
    private void disableButtons() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }

    // Метод за активиране на бутоните
    private void enableButtons() {
        hitButton.setDisable(false);
        standButton.setDisable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
