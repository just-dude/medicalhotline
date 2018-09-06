package controller.struts.action.main.issueTypes;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.IssueType;
import domain.IssueCategory;
import domain.common.Finder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class AddIssueTypeAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AddIssueTypeAction.class);

    private IssueType issueType = new IssueType();
    private IssueCategory issueCategory;
    private Long issueCategoryId;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuetype:add:*:*");
        loadIssueCategory();
        issueCategory.addIssueType(issueType);
        issueType = new IssueType();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("issuetype:add:*:*");
        loadIssueCategory();
    }

    protected void loadIssueCategory(){
        Finder<IssueCategory,Long> issueTypesFinder = (Finder<IssueCategory,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesFinder");
        issueCategory =issueTypesFinder.findOne(issueCategoryId);
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public IssueCategory getIssueCategory() {
        return issueCategory;
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
