package bs.bj.web;

import bs.bj.service.GameService;
import bs.bj.service.Helper;
import bs.bj.service.HistoryService;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by boubdyk on 03.11.2015.
 */

@Path("/blackjack")
public class GameRestService {

    private static ApplicationContext context;
    private static GameService gameService;
    private static HistoryService historyService;
    private static Helper helper;

    static {
        context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        gameService = context.getBean(GameService.class);
        historyService = context.getBean(HistoryService.class);
        helper = context.getBean(Helper.class);
    }


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public final Response registerPlayer(final String input) {
        JSONObject inputObject = helper.parse(input);

        Integer balance = (Integer)inputObject.get("balance");
        if (balance <= 0) return Response.status(Constants.CODE_NOT_MODIFIED).build();

        Integer playerId = gameService.registerPlayer(balance);
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
}
