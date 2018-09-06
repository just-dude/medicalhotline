package controller.struts.action.main.appeals;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import dataAccess.common.Node;
import domain.Appeal;
import domain.Appeal_;
import domain.Issue;
import domain.Issue_;
import domain.common.SimpleFinder;
import domain.common.stateChangesAccounting.EntityFieldPrevState;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Created by Артем on 25.03.2018.
 */
public class AppealManagementAction extends PrevActionMsgHandlingExceptionsAction {

    enum DefaultCauseOfTransitionToCanceledState {
        NotIdentifiedByPhoneNumber,NotIdentifiedByPersonalInformation,NoDataAvailableOnProblems, MyselfCause
    }

    protected static final Logger LOG = LogManager.getLogger(AppealManagementAction.class);

    protected Appeal appeal=new Appeal();
    protected long appealId;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:edit,gotostate,view:*:"+appealId);
        SecuritySubjectUtils.checkPermission("issue:add:*:*");
        addPrevActionMsgAndErrorToAction();
        loadAppealWithIssues();
        LOG.debug("Appeal management "+appeal.toString());
        return CustomResults.SUCCESS;
    }


    protected void loadAppealWithIssues(){
        SimpleFinder<Appeal,Long> appealsFinder = (SimpleFinder<Appeal,Long>)BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        appeal=appealsFinder.findOne(appealId,Node.rootNode().addChild(
                new Node(Appeal_.issues.getName()).addChildrenByData(Issue_.takenActions.getName(),Issue_.issueTypes.getName(),Issue_.organizations.getName()))
        );
    }

    public Map<String,String> findAllDefaultCausesOfTransition(){
        DefaultCauseOfTransitionToCanceledState[] defaultCausesOfTransitionsList = DefaultCauseOfTransitionToCanceledState.values();
        HashMap<String, String> result = new HashMap<>();
        for (DefaultCauseOfTransitionToCanceledState cause : defaultCausesOfTransitionsList) {
            result.put(cause.name(), getText(cause.name()));
        }
        return result;
    }

    public boolean isPermittedToViewChangesHistory(){
        return SecuritySubjectUtils.isPermitted("appeal:viewChangesHistory:*:"+appealId);
    }

    public boolean hasAtLeastOneFieldPrevState(){
        return appeal.hasAtLeastOneFieldPrevState();
    }

    public Map<EntityFieldPrevState,String> findFieldPrevStatesWithNextFieldValue(){
        if(SecuritySubjectUtils.isPermitted("appeal:viewChangesHistory:*:"+appealId)) {
            return appeal.findFieldPrevStatesWithNextFieldValue();
        } else{
            return Collections.EMPTY_MAP;
        }
    }

    public boolean getIsAllowedToGoToTheCanceledState(){
        return appeal.isAllowedToGoToTheCanceledState();
    }

    public boolean getIsAllowedToAddIssues(){
        return appeal.isAllowedToAddIssues();
    }

    public Appeal getAppeal() {
        return appeal;
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
