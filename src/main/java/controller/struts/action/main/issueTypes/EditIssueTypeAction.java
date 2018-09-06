package controller.struts.action.main.issueTypes;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.IssueCategory;
import domain.IssueType;
import domain.common.Finder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class EditIssueTypeAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(EditIssueTypeAction.class);

    private IssueCategory issueCategory;
    private Long issueCategoryId;

    private Long issueTypeId;
    private IssueType issueType = new IssueType();


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuetype:edit:*:*");
        loadIssueCategory();
        issueType = issueType.save();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("issuetype:edit:*:*");
        Finder<IssueType, Long> issueTypesFinder = (Finder<IssueType, Long>) BeanFactoryProvider.getBeanFactory().getBean("issueTypesFinder");
        issueType = issueTypesFinder.findOne(issueTypeId);
        loadIssueCategory();
    }

    private void loadIssueCategory(){
        Finder<IssueCategory, Long> issueCategoriesFinder = (Finder<IssueCategory, Long>) BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesFinder");
        issueCategory=issueCategoriesFinder.findOne(issueCategoryId);
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Long issueTypeId) {
        this.issueTypeId = issueTypeId;
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
