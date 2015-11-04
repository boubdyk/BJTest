package bs.bj.web;

import bs.bj.entity.EDealersDeck;
import bs.bj.entity.EPlayersDeck;
import bs.bj.service.GameService;
import bs.bj.service.Helper;
import bs.bj.service.HistoryService;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by boubdyk on 03.11.2015.
 */

@Path("/blackjack")
public class GameRestService {

    private static ApplicationContext context;
    private static GameService gameService;
    private static HistoryService historyService;
    private static Helper helper;

    private final int MIN_BET = 5;

    static {
        context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        gameService = context.getBean(GameService.class);
        historyService = context.getBean(HistoryService.class);
        helper = context.getBean(Helper.class);
    }


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public final Response registerPlayer(final String input) {
        JSONObject inputObject = helper.parse(input);

        Long balance = (Long)inputObject.get("balance");
        if (balance <= 0) return Response.status(Constants.CODE_NOT_MODIFIED).build();

        Integer playerId = gameService.registerPlayer(balance.intValue());
        Integer gameId = gameService.onGameStart(playerId);

        if (gameId == null) {
            return Response.status(Constants.CODE_NOT_MODIFIED).build();
        } else {
            JSONObject returnObject = new JSONObject();
            returnObject.put("playerId", playerId);
            returnObject.put("gameId", gameId);
            return Response.status(Constants.CODE_CREATED).entity(returnObject).build();
        }
    }


    @POST
    @Path("/bet")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public final Response makeBet(final String input) {
        JSONObject inputObject = helper.parse(input);
        Integer gameId = ((Long)inputObject.get("gameId")).intValue();
        Integer playerId = ((Long)inputObject.get("playerId")).intValue();
        Integer bet = ((Long)inputObject.get("bet")).intValue();

        Integer newBalance = gameService.makeBet(gameId, playerId, bet);


        if (newBalance == null || newBalance < 0) {
            return Response.status(Constants.CODE_NOT_MODIFIED).build();
        } else {
            Map<EPlayersDeck, EDealersDeck> cardsMap = gameService.drawCards(gameId);

            Integer playersDeckscore = gameService.playersDeckScore(gameId);
            Integer dealersDeckScore = gameService.dealersDeckScore(gameId);

            //Checking if player have Blackjack!!!
            boolean isRoundFinished = false;

            if (playersDeckscore == 21 && dealersDeckScore == 21) {
                newBalance = gameService.addBalance(playerId, bet);
                gameService.
                isRoundFinished = true;
            } else if (playersDeckscore == 21 && dealersDeckScore != 21) {
                newBalance = gameService.addBalance(playerId, bet * 3 / 2);
            }


            JSONObject returnObject = new JSONObject();
            returnObject.put("newBalance", newBalance);
            returnObject.put("cards", helper.parseCardsToJSON(cardsMap));
            returnObject.put("playersDeckScore", playersDeckscore);
            returnObject.put("dealersDeckScore", dealersDeckScore);
            return Response.status(Constants.CODE_CREATED).entity(returnObject).build();
        }
    }
}
