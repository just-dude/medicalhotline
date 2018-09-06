package controller.struts.action.main.issues;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Issue;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;

public class GoIssueToImpossibleToSolveState extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(GoIssueToImpossibleToSolveState.class);

    protected long issueId;
    protected String causeOfTransition;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issue:gotostate:*:*");
        Issue issue=new Issue(issueId);
        issue.goToImpossibleToSolveStateAndSave(causeOfTransition);
        return CustomResults.SUCCESS;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public String getCauseOfTransition() {
        return causeOfTransition;
    }

    public void setCauseOfTransition(String causeOfTransition) {
        this.causeOfTransition = causeOfTransition;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
