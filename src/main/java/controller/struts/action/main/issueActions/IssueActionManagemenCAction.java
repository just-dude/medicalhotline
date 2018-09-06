package controller.struts.action.main.issueActions;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import domain.Issue;
import domain.IssueAction;
import domain.common.SimpleFinder;
import domain.common.stateChangesAccounting.EntityFieldPrevState;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Артем on 25.03.2018.
 */
public class IssueActionManagemenCAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(IssueActionManagemenCAction.class);

    protected IssueAction issueAction=new IssueAction();
    protected Long issueActionId;
    protected Issue issue;
    protected Long issueId;
    protected Long appealId;


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issueaction:view:body:"+issueActionId);
        SimpleFinder<IssueAction,Long> issueActionsFinder = (SimpleFinder<IssueAction,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueActionsFinder");
        issueAction=issueActionsFinder.findOne(issueActionId);
        SimpleFinder<Issue,Long> issuesFinder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        issue=issuesFinder.findOne(issueId);
        return CustomResults.SUCCESS;
    }

    public boolean hasAtLeastOneFieldPrevState(){
        return issueAction.hasAtLeastOneFieldPrevState();
    }

    public Map<EntityFieldPrevState,String> findFieldPrevStatesWithNextFieldValue(){
        return issueAction.findFieldPrevStatesWithNextFieldValue();
    }

    public IssueAction getIssueAction() {
        return issueAction;
    }

    public void setIssueAction(IssueAction issueAction) {
        this.issueAction = issueAction;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Long getIssueActionId() {
        return issueActionId;
    }

    public void setIssueActionId(Long issueActionId) {
        this.issueActionId = issueActionId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getAppealId() {
        return appealId;
    }

    public void setAppealId(Long appealId) {
        this.appealId = appealId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
