package controller.struts.action.main;

import controller.struts.action.common.CustomResults;
import controller.struts.action.common.HandlingExceptionsBaseAction;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class IndexAction extends HandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(IndexAction.class);

    public IndexAction() {
        super();
    }

    @Override
    public String doExecute() throws Exception {
        SecuritySubjectUtils.checkAuthenticated();
        LOG.info("IndexAction is executing");
        return CustomResults.SUCCESS;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
