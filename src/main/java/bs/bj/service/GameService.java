package bs.bj.service;

import bs.bj.dao.DealersDeckDAO;
import bs.bj.dao.GameDAO;
import bs.bj.dao.PlayerDAO;
import bs.bj.dao.PlayersDeckDAO;
import bs.bj.entity.EDealersDeck;
import bs.bj.entity.EGame;
import bs.bj.entity.EPlayer;
import bs.bj.entity.EPlayersDeck;
import bs.bj.logic.Card;
import bs.bj.logic.Deck;
import bs.bj.logic.Face;
import bs.bj.logic.Suit;
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

    @Inject
    private PlayersDeckDAO playersDeckDAO;


    @Inject
    private DealersDeckDAO dealersDeckDAO;

    public GameService() {}


    boolean isValidID(Integer gameId) {
        return (gameId == null || gameDAO.read(gameId) == null) ? false : true;
    }

    //Method invokes when user is not registered in DB. Set user balance and return user id.
    public Integer registerPlayer(Integer balance) {
        if (balance == null || balance.intValue() <= 0) return null;
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

    //Draw first two cards for player and dealer. Set cards values.
    public Map<EPlayersDeck, EDealersDeck> drawCards(Integer gameId) {
        Map<EPlayersDeck, EDealersDeck> resultMap = new HashMap<EPlayersDeck, EDealersDeck>();
        resultMap.put(playingDeckService.drawCardForPlayer(gameId), playingDeckService.drawCardForDealer(gameId));
        resultMap.put(playingDeckService.drawCardForPlayer(gameId), playingDeckService.drawCardForDealer(gameId));
        updateGame(gameId);
        return resultMap;
    }

    //This method is used to update 2 fields in table Game: playersScore and dealersScore.
    private void updateGame(Integer gameId) {
        EGame eGame = gameDAO.read(gameId);
        eGame.setPlayerScore(playersDeckScore(gameId));
        eGame.setDealerScore(dealersDeckScore(gameId));
        gameDAO.update(eGame);
    }


    //If player Hits than draw card for him. Return Map of card and total cards value.
    public Map<EPlayersDeck, Integer> drawCardForPlayer(Integer gameId) {
        if (gameId == null || gameDAO.read(gameId) == null) return null;
        EPlayersDeck ePlayersDeck = playingDeckService.drawCardForPlayer(gameId);
        Map<EPlayersDeck, Integer> resultMap = new HashMap<EPlayersDeck, Integer>();
        resultMap.put(ePlayersDeck, playersDeckScore(gameId));
        return resultMap;
    }


    //If player stands than dealer draws card for himself. Return Map of card and total cards value.
    public Map<EDealersDeck, Integer> drawCardForDealer(Integer gameId) {
        if (!isValidID(gameId)) return null;
        EDealersDeck eDealersDeck = playingDeckService.drawCardForDealer(gameId);
        Map<EDealersDeck, Integer> resultMap = new HashMap<EDealersDeck, Integer>();
        resultMap.put(eDealersDeck, dealersDeckScore(gameId));
        return resultMap;
    }


    //Used in two methods:
    private Integer playersDeckScore(Integer gameId) {
        Deck playersDeck = new Deck();
        Card card;
        for (EPlayersDeck playerCards: playersDeckDAO.getCards(gameId)) {
            card = new Card(Suit.valueOf(playerCards.getCardSuit()), Face.valueOf(playerCards.getCardFace()));
            playersDeck.addCard(card);
        }
        return playersDeck.cardsValue();
    }

    private Integer dealersDeckScore(Integer gameId) {
        Deck dealersDeck = new Deck();
        Card card;
        for (EDealersDeck dealersCards: dealersDeckDAO.getCards(gameId)) {
            card = new Card(Suit.valueOf(dealersCards.getCardSuit()), Face.valueOf(dealersCards.getCardFace()));
            dealersDeck.addCard(card);
        }
        return dealersDeck.cardsValue();
    }

}
