package controller.struts.action.main.issueActions;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Issue;
import domain.IssueAction;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Артем on 25.03.2018.
 */
public class EditIssueActionCAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(EditIssueActionCAction.class);

    protected IssueAction issueAction=new IssueAction();
    protected Long issueActionId;
    protected Issue issue;
    protected Long issueId;
    protected Long appealId;


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issueaction:edit:*:"+issueActionId);
        issueAction=issueAction.save();
        LOG.debug("Save issue action for issue(issueId="+issueId+") "+issueAction.toString());
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("issueaction:edit:*:"+issueActionId);
        SimpleFinder<IssueAction,Long> issueActionsFinder = (SimpleFinder<IssueAction,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueActionsFinder");
        issueAction=issueActionsFinder.findOne(issueActionId);
        SimpleFinder<Issue,Long> issuesFinder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        issue=issuesFinder.findOne(issueId);
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
