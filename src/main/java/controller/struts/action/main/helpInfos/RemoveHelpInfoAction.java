package controller.struts.action.main.helpInfos;

import controller.struts.action.common.RemoveAction;
import domain.HelpInfo;
import domain.common.DomainObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveHelpInfoAction extends RemoveAction {

    protected static final Logger LOG = LogManager.getLogger(HelpInfosPageAction.class);

    @Override
    protected DomainObject getDomainObjectWithId(Long id) {
        return new HelpInfo(id);
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "helpinfo:remove:*:*";
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}

