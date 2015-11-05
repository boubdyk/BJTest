package bs.bj.dao;

import bs.bj.entity.EHistory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Named
public class HistoryDAO implements GenericDAO<EHistory, Integer> {

    @PersistenceContext(unitName = "entityManager")
    private EntityManager entityManager;

    public HistoryDAO() {}

    @Override
    public Integer create(EHistory newInstance) {
        entityManager.persist(newInstance);
        return newInstance.getId();
    }

    @Override
    public EHistory read(Integer id) {
        return entityManager.find(EHistory.class, id);
    }

    @Override
    public EHistory update(EHistory transientObject) {
        entityManager.merge(transientObject);
        return transientObject;
    }

    @Override
    public boolean delete(Integer persistentObjectID) {
        entityManager.remove(persistentObjectID);
        return read(persistentObjectID) == null ? true : false;
    }

    public Integer getBet(Integer gameId, Integer playerId, Integer actionId) {
        String query = "SELECT h.bet FROM EHistory h WHERE h.gameId=" + gameId +
                " AND h.playerId=" + playerId +
                " AND h.actionId=" + actionId;
        TypedQuery<Integer> result = entityManager.createQuery(query, Integer.class);
        return result.getSingleResult();
    }
}
