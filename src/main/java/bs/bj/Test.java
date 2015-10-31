package bs.bj;

import bs.bj.entity.ECard;
import bs.bj.logic.Card;
import bs.bj.logic.Deck;
import bs.bj.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by boubdyk on 30.10.2015.
 */
public class Test {
    private static ApplicationContext context;
    private static CardService cardService;
    private static ActionService actionService;
    private static PlayerService playerService;
    private static HistoryService historyService;
    private static GameService gameService;

    static {
        context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        cardService = context.getBean(CardService.class);
        actionService = context.getBean(ActionService.class);
        playerService = context.getBean(PlayerService.class);
        historyService = context.getBean(HistoryService.class);
        gameService = context.getBean(GameService.class);
    }

    public void insertIntoCardTable() {
        Deck deck = new Deck();
        deck.createDeck();
        for (int i = 0; i < deck.deckSize(); i++) {
            cardService.addCard(deck.getCard(i).toString());
        }
    }

    public void insertIntoActionTable() {
        actionService.addAction("BET", "Make a bet");
        actionService.addAction("HIT", "Draw one more card");
        actionService.addAction("STAND", "Finish current hand");
        actionService.addAction("BUST", "You lose tha hand");
        actionService.addAction("WIN", "You win the hand");
        actionService.addAction("PUSH", "You have the same score as dealer");
    }

    public void addNewPlayer() {
        playerService.addPlayer(200);
    }

    public void modifyBalance() {
        System.out.println(playerService.modifyBalance(5000, 20));
        System.out.println(playerService.modifyBalance(5000, -50));
        System.out.println(playerService.modifyBalance(5000, -500));
        System.out.println(playerService.modifyBalance(500, -500));
    }

    public void makeBet() {
        System.out.println("Players balance before bet = " + playerService.getBalance(5000));
        System.out.println(historyService.makeBet(5000, 1));
        System.out.println("Players balance after bet = " + playerService.getBalance(5000));
    }

    public void getDeck() {
        for (Integer cardId: gameService.getDeck(9000)) {
            System.out.println(cardId);
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
//        test.insertIntoActionTable();
//        test.addNewPlayer();
//        test.modifyBalance();
        test.makeBet();
        test.getDeck();
    }
}
