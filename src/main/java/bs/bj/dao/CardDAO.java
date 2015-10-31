package bs.bj.dao;

import bs.bj.entity.ECard;
import org.springframework.stereotype.Repository;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Named
public class CardDAO implements GenericDAO<ECard, Integer>{

    @PersistenceContext(unitName = "entityManager")
    private EntityManager entityManager;

    public CardDAO(){}

    @Override
    public Integer create(ECard newInstance) {
        entityManager.persist(newInstance);
        return newInstance.getId();
    }

    @Override
    public ECard read(Integer id) {
        return entityManager.find(ECard.class, id);
    }

    @Override
    public ECard update(ECard transientObjectID) {
        return null;
    }

    @Override
    public boolean delete(Integer persistentObject) {
        return false;
    }
}
