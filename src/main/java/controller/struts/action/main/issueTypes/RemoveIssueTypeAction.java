package controller.struts.action.main.issueTypes;

import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.IssueType;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class RemoveIssueTypeAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(RemoveIssueTypeAction.class);

    private Long issueTypeId;
    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuetype:remove:*:*");
        new IssueType(issueTypeId).remove();
        return CustomResults.SUCCESS;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
