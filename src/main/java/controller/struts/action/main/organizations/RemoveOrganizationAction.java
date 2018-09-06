package controller.struts.action.main.organizations;

import controller.struts.action.common.RemoveAction;
import domain.Organization;
import domain.common.DomainObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveOrganizationAction extends RemoveAction {

    protected static final Logger LOG = LogManager.getLogger(OrganizationsManagementAction.class);

    @Override
    protected DomainObject getDomainObjectWithId(Long id) {
        return new Organization(id);
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "organization:remove:*:*";
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}

