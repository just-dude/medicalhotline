package controller.struts.action.main.issueCategories;

import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.IssueCategory;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class AddIssueCategoryAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AddIssueCategoryAction.class);

    private IssueCategory issueCategory = new IssueCategory();

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuecategory:add:*:*");
        issueCategory.save();
        issueCategory = new IssueCategory();
        return CustomResults.SUCCESS;
    }

    public IssueCategory getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(IssueCategory issueCategory) {
        this.issueCategory = issueCategory;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
