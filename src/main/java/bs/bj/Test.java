package bs.bj;

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
    private static ActionService actionService;
    private static PlayerService playerService;
    private static HistoryService historyService;
    private static GameService gameService;

    static {
        context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        actionService = context.getBean(ActionService.class);
        playerService = context.getBean(PlayerService.class);
        historyService = context.getBean(HistoryService.class);
        gameService = context.getBean(GameService.class);
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
        System.out.println("Players balance before bet = " + playerService.getBalance(13000));
        System.out.println(historyService.makeBet(13000, 5));
        System.out.println("Players balance after bet = " + playerService.getBalance(13000));
    }

    public void addPlayer() {
        gameService.registerPlayer(500);
    }

    public void addBalance() {
        System.out.println(gameService.addBalance(13000, 200));
    }



    public static void main(String[] args) {
        Test test = new Test();
//        test.addPlayer(); id = 13000
//        test.addBalance();
//        test.makeBet();
        gameService.drawCards(21000);
    }
}
