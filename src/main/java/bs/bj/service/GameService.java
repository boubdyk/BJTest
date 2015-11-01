package bs.bj.service;

import bs.bj.dao.GameDAO;
import bs.bj.dao.PlayerDAO;
import bs.bj.entity.EDealersDeck;
import bs.bj.entity.EGame;
import bs.bj.entity.EPlayer;
import bs.bj.entity.EPlayersDeck;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Inject
    private PlayingDeckService playingDeckService;

    public GameService() {}

//    private boolean endRound(Integer playerId, Integer gameId) {
//
//    }


    //Method invokes when user is not registered in DB. Set user balance and return user id.
    public Integer registerPlayer(Integer balance) {
        EPlayer newPlayer = new EPlayer(balance);
        return playerDAO.create(newPlayer);
    }

    //Return new balance.
    public Integer addBalance(Integer playerId, Integer balance) {
        EPlayer player = playerDAO.read(playerId);
        Integer newBalance = player.getBalance() + balance;
        player.setBalance(newBalance);
        playerDAO.update(player);
        return player.getBalance();
    }

    //Executes when new game round start. Return gameId.
    public Integer onGameStart(Integer playerId) {
        if (playerId == null) return null;
        EPlayer ePlayer = playerDAO.read(playerId);
        if (ePlayer == null) return null;
        EGame newGame = new EGame();
        newGame.setPlayerId(ePlayer.getId());
        Date date = new Date();
        newGame.setDateStart(date);
        Integer gameId = gameDAO.create(newGame);
        playingDeckService.createDeck(gameId);
        return gameId;
    }

    //Draw first two cards for player and dealer.
    public Map<EPlayersDeck, EDealersDeck> drawCards(Integer gameId) {
        Map<EPlayersDeck, EDealersDeck> resultMap = new HashMap<EPlayersDeck, EDealersDeck>();
        resultMap.put(playingDeckService.drawCardForPlayer(gameId), playingDeckService.drawCardForDealer(gameId));
        resultMap.put(playingDeckService.drawCardForPlayer(gameId), playingDeckService.drawCardForDealer(gameId));
        return resultMap;
    }

}
