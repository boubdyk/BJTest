package bs.bj.dao;

import bs.bj.entity.EPlayersDeck;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by boubdyk on 01.11.2015.
 */

@Named
public class PlayersDeckDAO implements GenericDAO<EPlayersDeck, Integer> {

    @PersistenceContext(unitName = "entityManager")
    private EntityManager entityManager;

    public PlayersDeckDAO() {}

    @Override
    public Integer create(EPlayersDeck newInstance) {
        entityManager.persist(newInstance);
        return newInstance.getId();
    }

    @Override
    public EPlayersDeck read(Integer id) {
        return entityManager.find(EPlayersDeck.class, id);
    }

    @Override
    public EPlayersDeck update(EPlayersDeck transientObject) {
        entityManager.merge(transientObject);
        return transientObject;
    }

    @Override
    public boolean delete(Integer persistentObjectID) {
        entityManager.remove(read(persistentObjectID));
        return read(persistentObjectID) == null ? true : false;
    }
}
