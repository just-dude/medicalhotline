package controller.struts.action.main.issueCategories;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.IssueCategory;
import domain.common.Finder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class EditIssueCategoryAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(EditIssueCategoryAction.class);

    private Long issueCategoryId;
    private IssueCategory issueCategory = new IssueCategory();

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuecategory:edit:*:*");
        issueCategory = issueCategory.save();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("issuecategory:edit:*:*");
        Finder<IssueCategory, Long> issueCategoriesFinder = (Finder<IssueCategory, Long>) BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesFinder");
        issueCategory = issueCategoriesFinder.findOne(issueCategoryId);
    }

    public IssueCategory getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(IssueCategory issueCategory) {
        this.issueCategory = issueCategory;
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
