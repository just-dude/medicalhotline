package controller.struts.action.main.responsiblePersons;

import controller.struts.action.common.RemoveAction;
import domain.ResponsiblePerson;
import domain.common.DomainObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveResponsiblePersonAction extends RemoveAction {

    protected static final Logger LOG = LogManager.getLogger(RemoveResponsiblePersonAction.class);

    @Override
    protected DomainObject getDomainObjectWithId(Long id) {
        return new ResponsiblePerson(id);
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "responsibleperson:remove:*:*";
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}

