package controller.struts.action.main.appeals;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Appeal;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoAppealToTheOnTheGoState extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(GoAppealToTheOnTheGoState.class);

    protected long appealId;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:gotostate:*:*");
        SimpleFinder<Appeal,Long> appealsFinder = (SimpleFinder<Appeal,Long>) BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        Appeal appeal=new Appeal(appealId);
        appeal.goToOnTheGoStateAndSave();
        return CustomResults.SUCCESS;
    }

    public long getAppealId() {
        return appealId;
    }

    public void setAppealId(long appealId) {
        this.appealId = appealId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
