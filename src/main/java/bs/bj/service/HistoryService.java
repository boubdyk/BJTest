package bs.bj.service;

import bs.bj.dao.HistoryDAO;
import bs.bj.dao.PlayerDAO;
import bs.bj.entity.EHistory;
import bs.bj.entity.EPlayer;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by boubdyk on 31.10.2015.
 */

@Named
@Transactional
public class HistoryService {

    @Inject
    private HistoryDAO historyDAO;

    @Inject
    private PlayerDAO playerDAO;

    @Inject
    private ActionService actionService;

    @Inject
    private GameService gameService;

    public HistoryService() {}

    //Player make bet. Return true if bet is valid else return false.
    public boolean makeBet(Integer playerID, Integer bet) {
        if (playerID == null || bet == null || bet.intValue() <= 0) return false;
        EPlayer ePlayer = playerDAO.read(playerID);
        if (ePlayer == null || ePlayer.getBalance() < bet.intValue()) return false;
        ePlayer.setBalance(ePlayer.getBalance() - bet.intValue());
        EHistory eHistory = new EHistory();
        eHistory.setPlayerId(playerID);
        eHistory.setBet(bet);
        eHistory.setActionId(actionService.getActionID("BET"));
        eHistory.setGameId(gameService.onGameStart(playerID));
        historyDAO.create(eHistory);
        return true;
    }


}
