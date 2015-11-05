package bs.bj.service;

import bs.bj.dao.PlayerDAO;
import bs.bj.entity.EPlayer;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by boubdyk on 31.10.2015.
 */

@Named
@Transactional
public class PlayerService {

    @Inject
    private PlayerDAO playerDAO;

    public PlayerService() {}

    // Modifies players balance if player win or lose. Returns new balance.
    public Integer modifyBalance(Integer playerID, Integer sum) {
        if (playerID == null || sum == null) return null;
        EPlayer ePlayer = playerDAO.read(playerID);
        if (ePlayer == null) return null;
        Integer playerBalance = ePlayer.getBalance();
        playerBalance += sum;
        if (playerBalance <= 0) return null;
        ePlayer.setBalance(playerBalance);
        playerDAO.update(ePlayer);
        return ePlayer.getBalance();
    }

    //Register new player in DB. Returns players ID.
    public Integer addPlayer(Integer playerBalance) {
        if (playerBalance == null || playerBalance <= 0) return null;
        EPlayer newPlayer = new EPlayer();
        newPlayer.setBalance(playerBalance);
        return playerDAO.create(newPlayer);
    }

    //Get players balance.
    public Integer getBalance(Integer playerID) {
        if (playerID == null) return null;
        EPlayer ePlayer = playerDAO.read(playerID);
        return ePlayer == null ? null : playerDAO.read(playerID).getBalance();
    }
}
