package bs.bj.dao;

import bs.bj.entity.EDealersDeck;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by boubdyk on 01.11.2015.
 */

@Named
public class DealersDeckDAO implements GenericDAO<EDealersDeck, Integer> {

    @PersistenceContext(unitName = "entityManager")
    private EntityManager entityManager;

    public DealersDeckDAO() {}

    @Override
    public Integer create(EDealersDeck newInstance) {
        entityManager.persist(newInstance);
        return newInstance.getId();
    }

    @Override
    public EDealersDeck read(Integer id) {
        return entityManager.find(EDealersDeck.class, id);
    }

    @Override
    public EDealersDeck update(EDealersDeck transientObject) {
        entityManager.merge(transientObject);
        return transientObject;
    }

    @Override
    public boolean delete(Integer persistentObjectID) {
        entityManager.remove(read(persistentObjectID));
        return read(persistentObjectID) == null ? true : false;
    }
}
