package bs.bj.service;


/**
 * Created by boubdyk on 30.10.2015.
 */

import bs.bj.dao.CardDAO;
import bs.bj.entity.ECard;
import bs.bj.logic.Card;
import bs.bj.logic.Face;
import bs.bj.logic.Suit;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Transactional
public class CardService {

    @Inject
    private CardDAO cardDAO;

    public Integer addCard(String cardName) {
        if (StringUtils.isEmpty(cardName)) return null;
        ECard newInstance = new ECard();
        newInstance.setName(cardName);
        return cardDAO.create(newInstance);
    }

    public boolean deleteCard(Integer id) {
        if (cardDAO.read(id) == null) return false;
        return cardDAO.delete(id);
    }

}
