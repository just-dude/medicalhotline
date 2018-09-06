package controller.struts.action.main.appeals;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Appeal;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Артем on 25.03.2018.
 */
public class GoAppeaToCanceledStateAction extends FormProviderHandlingExceptionsBaseAction {


    protected static final Logger LOG = LogManager.getLogger(GoAppeaToCanceledStateAction.class);

    protected long appealId;
    protected AppealManagementAction.DefaultCauseOfTransitionToCanceledState defaultCauseOfTransitionToCanceledState;
    protected String myselfCauseOfTransition;


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:gotostate:*:*");
        SimpleFinder<Appeal,Long> appealsFinder = (SimpleFinder<Appeal,Long>)BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        Appeal appeal=new Appeal(appealId);
        if(defaultCauseOfTransitionToCanceledState.equals(AppealManagementAction.DefaultCauseOfTransitionToCanceledState.MyselfCause)){
            appeal.goToCanceledStateAndSave(myselfCauseOfTransition);
        } else{
            appeal.goToCanceledStateAndSave(getText(defaultCauseOfTransitionToCanceledState.name()));
        }
        return CustomResults.SUCCESS;
    }

    public long getAppealId() {
        return appealId;
    }

    public void setAppealId(long appealId) {
        this.appealId = appealId;
    }

    public AppealManagementAction.DefaultCauseOfTransitionToCanceledState getDefaultCauseOfTransitionToCanceledState() {
        return defaultCauseOfTransitionToCanceledState;
    }

    public void setDefaultCauseOfTransitionToCanceledState(AppealManagementAction.DefaultCauseOfTransitionToCanceledState defaultCauseOfTransitionToCanceledState) {
        this.defaultCauseOfTransitionToCanceledState = defaultCauseOfTransitionToCanceledState;
    }

    public String getMyselfCauseOfTransition() {
        return myselfCauseOfTransition;
    }

    public void setMyselfCauseOfTransition(String myselfCauseOfTransition) {
        this.myselfCauseOfTransition = myselfCauseOfTransition;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
