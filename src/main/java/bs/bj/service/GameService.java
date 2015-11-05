package bs.bj.service;

import bs.bj.dao.*;
import bs.bj.entity.*;
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

    @Inject
    private HistoryService historyService;

    @Inject
    private ActionDAO actionDAO;

    @Inject HistoryDAO historyDAO;

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
        historyService.addHistory(playerId, null, actionDAO.getID(ActionEnum.MODIFY_BALANCE.toString()));
        return player.getBalance();
    }

    //Player make bet. Return true if bet is valid else return false. Returns new balance.
    public Integer makeBet(Integer gameId, Integer playerID, Integer bet) {
        if (playerID == null || bet == null || bet <= 0) return null;
        EPlayer ePlayer = playerDAO.read(playerID);
        if (ePlayer == null || ePlayer.getBalance() < bet) return null;
        ePlayer.setBalance(ePlayer.getBalance() - bet);
        playerDAO.update(ePlayer);
        EHistory eHistory = new EHistory();
        eHistory.setPlayerId(playerID);
        eHistory.setBet(bet);
        eHistory.setActionId(actionDAO.getID(ActionEnum.BET.toString()));
        eHistory.setGameId(gameId);
        historyDAO.create(eHistory);
        return ePlayer.getBalance();
    }

    //Executes when new game round start. Return gameId.
    public Integer onGameStart(Integer playerId) {
        if (playerId == null) return null;
        EPlayer ePlayer = playerDAO.read(playerId);
        if (ePlayer == null) return null;
        EGame newGame = new EGame();
        newGame.setPlayerId(ePlayer.getId());
        newGame.setDateStart(new Date());
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
    public Map<Integer, Integer> updateGame(Integer gameId) {
        EGame eGame = gameDAO.read(gameId);
        eGame.setPlayerScore(playersDeckScore(gameId));
        eGame.setDealerScore(dealersDeckScore(gameId));
        gameDAO.update(eGame);
        return new HashMap<Integer, Integer>(eGame.getPlayerScore(), eGame.getDealerScore());
    }


    //If player Hits than draw card for him. Return Map of card and total cards value.
    public Map<EPlayersDeck, Integer> drawCardForPlayer(Integer gameId, Integer playerId) {
        if (gameId == null || gameDAO.read(gameId) == null) return null;
        EPlayersDeck ePlayersDeck = playingDeckService.drawCardForPlayer(gameId);
        Map<EPlayersDeck, Integer> resultMap = new HashMap<EPlayersDeck, Integer>();
        resultMap.put(ePlayersDeck, playersDeckScore(gameId));
        historyService.addHistory(playerId, gameId, actionDAO.getID(ActionEnum.HIT.toString()));
        return resultMap;
    }


    //If player stands than dealer draws card for himself. Return Map of card and total cards value.
    public Map<EDealersDeck, Integer> drawCardForDealer(Integer gameId, Integer playerId) {
        if (!isValidID(gameId)) return null;
        EDealersDeck eDealersDeck = playingDeckService.drawCardForDealer(gameId);
        Map<EDealersDeck, Integer> resultMap = new HashMap<EDealersDeck, Integer>();
        resultMap.put(eDealersDeck, dealersDeckScore(gameId));
        historyService.addHistory(playerId, gameId, actionDAO.getID(ActionEnum.STAND.toString()));
        return resultMap;
    }


    //Used in two methods:
    public Integer playersDeckScore(Integer gameId) {
        Deck playersDeck = new Deck();
        Card card;
        for (EPlayersDeck playerCards: playersDeckDAO.getCards(gameId)) {
            card = new Card(Suit.valueOf(playerCards.getCardSuit()), Face.valueOf(playerCards.getCardFace()));
            playersDeck.addCard(card);
        }
        return playersDeck.cardsValue();
    }

    public Integer dealersDeckScore(Integer gameId) {
        Deck dealersDeck = new Deck();
        Card card;
        for (EDealersDeck dealersCards: dealersDeckDAO.getCards(gameId)) {
            card = new Card(Suit.valueOf(dealersCards.getCardSuit()), Face.valueOf(dealersCards.getCardFace()));
            dealersDeck.addCard(card);
        }
        return dealersDeck.cardsValue();
    }

    public String gameRoundResult(Integer gameId, Integer playerId, Integer bet, boolean isBlackjack) {
        String result;
        EGame eGame;
        if (dealersDeckScore(gameId) > playersDeckScore(gameId)) {
            result = ActionEnum.BUSTED.toString();
            historyService.addHistory(playerId, gameId, actionDAO.getID(result));
            eGame = gameDAO.read(gameId);
            eGame.setPrice(0);
            eGame.setWinner("DEALER");
            eGame.setDateFinish(new Date());
            gameDAO.update(eGame);
            return result;
        } else if (dealersDeckScore(gameId) < playersDeckScore(gameId)) {
            result = ActionEnum.WIN.toString();
            historyService.addHistory(playerId, gameId, actionDAO.getID(result));
            eGame = gameDAO.read(gameId);
            int price = isBlackjack ? bet + bet * 3 / 2 : bet * 2;
            eGame.setPrice(price);
            addBalance(playerId, price);
            eGame.setWinner("PLAYER");
            eGame.setDateFinish(new Date());
            gameDAO.update(eGame);
            return result;
            //Using == instead of equals() 'cause both parameters will be less then 128
            //and pool of Integers will compare them correctly.
        } else if (dealersDeckScore(gameId) == playersDeckScore(gameId)) {
            result = ActionEnum.PUSH.toString();
            historyService.addHistory(playerId, gameId, actionDAO.getID(result));
            eGame = gameDAO.read(gameId);
            eGame.setPrice(bet);
            addBalance(playerId, bet);
            eGame.setWinner("PUSH");
            eGame.setDateFinish(new Date());
            gameDAO.update(eGame);
            return result;
        }
        return null;
    }

    public boolean isRoundFinished(Integer gameId) {
        if (gameId == null || gameDAO.read(gameId) == null) return true;
        return gameDAO.getWinner(gameId) == null ? false : true;
    }

    public Integer getRoundsBet(Integer gameId, Integer playerId) {
        return historyDAO.getBet(gameId, playerId, actionDAO.getID("BET"));
    }

}
