package bs.bj;

import bs.bj.logic.Card;
import bs.bj.logic.Deck;
import bs.bj.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;
import java.net.URLClassLoader;

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


    public static void main(String[] args) {
        Test test = new Test();
        Integer playerId = gameService.registerPlayer(254);
        Integer gameId = gameService.onGameStart(playerId);
        System.out.println("player_id = " + playerId);
        System.out.println("game_id = " + gameId);

    }

//    {
//        "gameId": 1580,
//            "playerId": 1570
//    }
}
