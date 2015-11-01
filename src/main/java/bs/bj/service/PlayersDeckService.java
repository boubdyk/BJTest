package bs.bj.service;

import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by boubdyk on 01.11.2015.
 */

@Named
@Transactional
public class PlayersDeckService {

    @PersistenceContext(unitName = "entityManager")
    private EntityManager entityManager;

    public PlayersDeckService() {}


}
