package bs.bj.service;

import bs.bj.dao.GameDAO;
import bs.bj.dao.PlayerDAO;
import bs.bj.entity.ECard;
import bs.bj.entity.EGame;
import bs.bj.entity.EPlayer;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by boubdyk on 31.10.2015.
 */

@Named
@Transactional
public class GameService {

    @Inject
    private GameDAO gameDAO;

    @Inject
    private PlayerDAO playerDAO;

    public GameService() {}

    //Returns deck for current round
    public List<Integer> getDeck(Integer gameID) {
        if (gameID == null) return null;
        if (gameDAO.read(gameID) == null) return null;
        return gameDAO.read(gameID).getDeck();
    }

    //Executes when new game round start.
    public Integer onGameStart(Integer playerID) {
        if (playerID == null) return null;
        EPlayer ePlayer = playerDAO.read(playerID);
        if (ePlayer == null) return null;
        EGame newGame = new EGame();
        newGame.setPlayerId(ePlayer.getId());
        Date date = new Date();
        newGame.setDateStart(date);
        return gameDAO.create(newGame);
    }


}
