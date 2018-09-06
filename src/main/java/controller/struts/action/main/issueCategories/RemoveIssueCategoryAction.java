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
public class RemoveIssueCategoryAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(RemoveIssueCategoryAction.class);

    private Long issueCategoryId;
    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuecategory:remove:*:*");
        new IssueCategory(issueCategoryId).remove();
        return CustomResults.SUCCESS;
    }

    public Long getIssueCategoryId() {
        return issueCategoryId;
    }

    public void setIssueCategoryId(Long issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
