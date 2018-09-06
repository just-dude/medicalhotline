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
public class AddIssueActionCAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AddIssueActionCAction.class);

    protected IssueAction issueAction=new IssueAction();
    protected Issue issue;
    protected long issueId;
    protected long appealId;


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issueaction:add:text:"+issueId);
        Issue issue=new Issue(issueId);
        issueAction=issue.addAction(issueAction);
        LOG.debug("Save issue action for issue(issueId="+issueId+") "+issueAction.toString());
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("issueaction:add:text:"+issueId);
        SimpleFinder<Issue,Long> issuesFinder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        issue=issuesFinder.findOne(issueId);
    }

    public IssueAction getIssueAction() {
        return issueAction;
    }

    public void setIssueAction(IssueAction issueAction) {
        this.issueAction = issueAction;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public Issue getIssue() {
        return issue;
    }

    public long getAppealId() {
        return appealId;
    }

    public void setAppealId(long appealId) {
        this.appealId = appealId;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
