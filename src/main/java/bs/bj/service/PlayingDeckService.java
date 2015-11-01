package bs.bj.service;

import bs.bj.dao.DealersDeckDAO;
import bs.bj.dao.GameDAO;
import bs.bj.dao.PlayersDeckDAO;
import bs.bj.dao.PlayingDeckDAO;
import bs.bj.entity.EDealersDeck;
import bs.bj.entity.EPlayersDeck;
import bs.bj.entity.EPlayingDeck;
import bs.bj.logic.Card;
import bs.bj.logic.Deck;
import bs.bj.logic.Face;
import bs.bj.logic.Suit;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by boubdyk on 01.11.2015.
 */

@Named
@Transactional
public class PlayingDeckService {

    @Inject
    private PlayingDeckDAO playingDeckDAO;

    @Inject
    private GameDAO gameDAO;

    @Inject
    private DealersDeckDAO dealersDeckDAO;

    @Inject
    private PlayersDeckDAO playersDeckDAO;

    private boolean isValidID(Integer gameId) {
        return (gameId == null || gameDAO.read(gameId) == null) ? false : true;
    }

    //Creates new deck for each game round.
    public Deck createDeck(Integer gameId) {
        if (!isValidID(gameId)) return null;
        Deck deck = new Deck();
        deck.createDeck();
        deck.shuffle();
        EPlayingDeck ePlayingDeck;
        for (int i = 0; i < deck.deckSize(); i++) {
            ePlayingDeck = new EPlayingDeck(deck.getCard(i).getSuit().toString(),
                    deck.getCard(i).getFace().toString(), gameId);
            playingDeckDAO.create(ePlayingDeck);
        }
        return deck;
    }

    //Getting playing deck of current game round.
    public Deck getPlayingDeck(Integer gameId) {
        if (!isValidID(gameId)) return null;
        List<EPlayingDeck> ePlayingDeck = playingDeckDAO.getDeck(gameId);
        Deck deck = new Deck();
        Card card;
        for (EPlayingDeck pd: ePlayingDeck) {
            card = new Card(Suit.valueOf(pd.getCardSuit()), Face.valueOf(pd.getCardFace()));
            deck.addCard(card);
        }
        return deck;
    }

    //This method is used in two methods: drawCardForDealer and drawCardForPlayer. Return card id.
    private Integer getCardId(Integer gameId) {
        if (!isValidID(gameId)) return null;
        Card card = getPlayingDeck(gameId).getCard(0);
        return playingDeckDAO.getCardID(gameId, card.getSuit().toString(), card.getFace().toString());
    }

    //Draw card for dealer. Get card from playing deck and put it into dealers deck.
    public EDealersDeck drawCardForDealer(Integer gameId) {
        EPlayingDeck deck = playingDeckDAO.read(getCardId(gameId));
        EDealersDeck eDealersDeck = new EDealersDeck(deck.getId(), deck.getCardSuit(), deck.getCardFace(), gameId);
        dealersDeckDAO.create(eDealersDeck);
        playingDeckDAO.delete(eDealersDeck.getCardId());
        return eDealersDeck;
    }


    public EPlayersDeck drawCardForPlayer(Integer gameId) {
        EPlayingDeck deck = playingDeckDAO.read(getCardId(gameId));
        EPlayersDeck ePlayersDeck = new EPlayersDeck(deck.getId(), deck.getCardSuit(), deck.getCardFace(), gameId);
        playersDeckDAO.create(ePlayersDeck);
        playingDeckDAO.delete(ePlayersDeck.getCardId());
        return ePlayersDeck;
    }
}
