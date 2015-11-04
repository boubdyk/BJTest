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

    public HistoryService() {}

    public Integer addHistory(Integer playerId, Integer gameId, Integer actionId) {
        EHistory eHistory = new EHistory(actionId, playerId, gameId);
        return historyDAO.create(eHistory);
    }
}
