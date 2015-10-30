package bs.bj.service;


/**
 * Created by boubdyk on 30.10.2015.
 */

import bs.bj.dao.CardDAO;
import bs.bj.entity.ECard;
import bs.bj.logic.Card;
import bs.bj.logic.Face;
import bs.bj.logic.Suit;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Transactional
public class CardService {

    @Inject
    private CardDAO cardDAO;

    public Integer addCard() {
        ECard newInstance = new ECard();
        Card card = new Card(Suit.CLUB, Face.ACE);
        newInstance.setName(card.toString());
        System.out.println(cardDAO);
        return cardDAO.create(newInstance);
    }

}
