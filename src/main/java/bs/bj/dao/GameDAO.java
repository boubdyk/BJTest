package bs.bj.dao;

import bs.bj.entity.EGame;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Named
public class GameDAO implements GenericDAO<EGame, Integer> {

    @PersistenceContext(unitName = "entityManager")
    private EntityManager entityManager;

    public GameDAO(){}

    @Override
    public Integer create(EGame newInstance) {
        entityManager.persist(newInstance);
        return newInstance.getId();
    }

    @Override
    public EGame read(Integer id) {
        return entityManager.find(EGame.class, id);
    }

    @Override
    public EGame update(EGame transientObject) {
        entityManager.merge(transientObject);
        return transientObject;
    }

    @Override
    public boolean delete(Integer persistentObjectID) {
        entityManager.remove(persistentObjectID);
        return read(persistentObjectID) == null ? true : false;
    }
}
