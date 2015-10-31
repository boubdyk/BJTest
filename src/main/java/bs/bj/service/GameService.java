package bs.bj.service;

import bs.bj.dao.GameDAO;
import bs.bj.dao.PlayerDAO;
import bs.bj.entity.EGame;
import bs.bj.entity.EPlayer;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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



    //Executes when new game start.
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
