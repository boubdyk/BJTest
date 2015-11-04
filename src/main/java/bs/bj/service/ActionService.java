package bs.bj.service;

import bs.bj.dao.ActionDAO;
import bs.bj.entity.EAction;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by boubdyk on 31.10.2015.
 */

@Named
@Transactional
public class ActionService {

    @Inject
    private ActionDAO actionDAO;

//    public Integer addAction(String name, String description) {
//        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(description)) return null;
//        EAction eAction = new EAction();
//        eAction.setName(name);
//        eAction.setDescription(description);
//        return actionDAO.create(eAction);
//    }

    //Returns action id by action name.
    public Integer getActionID(String actionName) {
        return StringUtils.isEmpty(actionName) ? null : actionDAO.getID(actionName);
    }
}
