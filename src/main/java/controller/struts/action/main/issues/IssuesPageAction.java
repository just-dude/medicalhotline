package controller.struts.action.main.issues;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import dataAccess.common.Node;
import domain.*;
import domain.common.SimpleFinder;
import domain.common.exception.AuthorizationFailedException;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.time.LocalDateTime;
import java.util.*;

import static common.DateTimeUtils.fromUserPrefZoneToSystemZone;
import static dataAccess.IssuesSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Артем on 25.03.2018.
 */
public class IssuesPageAction extends FormProviderHandlingExceptionsBaseAction {

    private enum ViewMode{All,ByOrganization}

    protected static final Logger LOG = LogManager.getLogger(IssuesPageAction.class);

    protected Page<Issue> issuesPage;
    protected List<Issue.State> issuesStates;
    protected boolean onlyMyIssuesFlag;
    protected List<Long> issueTypesIds;
    protected List<Long> issueCategoriesIds;
    protected List<Long> organizationsIds;
    protected LocalDateTime startPeriodDateTime;
    protected LocalDateTime endPeriodDateTime;

    protected Specifications findSpecification=null;

    private Integer entitiesPerPage = 15;
    private Integer pageNumber = 1;

    private ViewMode viewMode;
    private Long organizationId;

    public IssuesPageAction() {
        super(false);
    }

    @Override
    protected String doExecute() throws Exception {
        checkPermissionAndSetViewMode();
        SimpleFinder<Issue,Long> finder = (SimpleFinder<Issue,Long>)BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        buildFindSpecification();
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if(viewMode.equals(ViewMode.All)) {
            if (findSpecification != null) {
                issuesPage = finder.findAll(findSpecification, new PageRequest(pageNumber - 1, entitiesPerPage, sort),
                        Node.rootNode().addChildrenByData(Issue_.organizations.getName(), Issue_.issueTypes.getName()));
            } else {
                issuesPage = finder.findAll(new PageRequest(pageNumber - 1, entitiesPerPage, sort),
                        Node.rootNode().addChildrenByData(Issue_.organizations.getName(), Issue_.issueTypes.getName()));
            }
        } else if(viewMode.equals(ViewMode.ByOrganization)){
            issuesPage = finder.findAll(issueBelongsToOrganization(organizationId),
                    new PageRequest(pageNumber - 1, entitiesPerPage, sort),
                    Node.rootNode().addChildrenByData(Issue_.organizations.getName(), Issue_.issueTypes.getName()));
        }
        return CustomResults.SUCCESS;
    }

    protected void buildFindSpecification(){
        UserAccount currentUser = SecuritySubjectUtils.getCurrentUserAccount();
        if(issuesStates!=null && issuesStates.size()>0) {
            mergeToFindSpecification(issueHasStates(issuesStates));
        }
        if(issueTypesIds!=null && issueTypesIds.size()>0){
            mergeToFindSpecification(issueHasTypesWithIds(issueTypesIds));
        }
        if(issueCategoriesIds!=null && issueCategoriesIds.size()>0){
            mergeToFindSpecification(issueHasCategoriesIds(issueCategoriesIds));
        }
        if(organizationsIds!=null && organizationsIds.size()>0){
            mergeToFindSpecification(issueHasOrgnizationsWithIds(organizationsIds));
        }
        if (onlyMyIssuesFlag) {
            mergeToFindSpecification(issueHasCreatorWithId(currentUser.getId()));
        }
        if(startPeriodDateTime!=null && endPeriodDateTime!=null && (startPeriodDateTime.equals(endPeriodDateTime) || startPeriodDateTime.isBefore(endPeriodDateTime))){
            mergeToFindSpecification(
                    issueCreatedInPeriodOf(fromUserPrefZoneToSystemZone(startPeriodDateTime),fromUserPrefZoneToSystemZone(endPeriodDateTime))
            );
        }
    }

    @Override
    protected void doInput() throws Exception {
        checkPermissionAndSetViewMode();
        UserAccount currentUser=SecuritySubjectUtils.getCurrentUserAccount();
        setOnlyMyIssuesFlag((currentUser.getUserGroup().equals(UserGroup.Operator)));
        doExecute();
    }

    protected void checkPermissionAndSetViewMode(){
        if(SecuritySubjectUtils.isPermitted("issue:viewMany:*:*")){
            viewMode=ViewMode.All;
        } else if(SecuritySubjectUtils.isPermitted("issue:viewMany:byorganization:?")){
            UserAccount ua = SecuritySubjectUtils.getCurrentUserAccount();
            if(ua instanceof ResponsiblePersonUserAccount){
                viewMode=ViewMode.ByOrganization;
                organizationId=((ResponsiblePersonUserAccount)ua).getOrganization().getId();
            }
        } else{
            throw new AuthorizationFailedException("issue:viewMany*:*;issue:viewMany:byorganization:?");
        }
    }

    protected void mergeToFindSpecification(Specification additionalSpecification){
        if(findSpecification!=null){
            findSpecification=findSpecification.and(additionalSpecification);
        } else{
            findSpecification=where(additionalSpecification);
        }
    }

    public Map<String, String> findAllIssueStates(){
        HashMap<String, String> result = new LinkedHashMap();
        for (Issue.State state :  Issue.State.values()) {
            result.put(state.name(), getText(state.name()));
        }
        return result;
    }

    public Map<String, String> findAllIssueTypes(){
        SimpleFinder<IssueType,Long> finder = (SimpleFinder<IssueType,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueTypesWithoutSoftDeletedFinder");
        List<IssueType> issueTypesList=finder.findAll();
        HashMap<String, String> result = new LinkedHashMap<>();
        for (IssueType issueType : issueTypesList) {
            result.put(issueType.getId().toString(), issueType.getName());
        }
        return result;
    }

    public Map<String, String> findAllIssueCategories(){
        SimpleFinder<IssueCategory,Long> finder = (SimpleFinder<IssueCategory,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesWithoutSoftDeletedFinder");
        List<IssueCategory> list=finder.findAll();
        HashMap<String, String> result = new LinkedHashMap<>();
        for (IssueCategory issueCategory : list) {
            result.put(issueCategory.getId().toString(), issueCategory.getName());
        }
        return result;
    }

    public Map<String, String> findAllOrganizations(){
        SimpleFinder<Organization,Long> finder = (SimpleFinder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsWithoutSoftDeletedFinder");
        List<Organization> list=finder.findAll();
        HashMap<String, String> result = new LinkedHashMap<>();
        for (Organization organization : list) {
            result.put(organization.getId().toString(), organization.getName());
        }
        return result;
    }

    public Page<Issue> getIssuesPage() {
        return issuesPage;
    }

    public void setIssuesPage(Page<Issue> issuesPage) {
        this.issuesPage = issuesPage;
    }

    public List<Issue.State> getIssuesStates() {
        return issuesStates;
    }

    public void setIssuesStates(List<Issue.State> issuesStates) {
        this.issuesStates = issuesStates;
    }

    public boolean isOnlyMyIssuesFlag() {
        return onlyMyIssuesFlag;
    }

    public void setOnlyMyIssuesFlag(boolean onlyMyIssuesFlag) {
        this.onlyMyIssuesFlag = onlyMyIssuesFlag;
    }

    public LocalDateTime getStartPeriodDateTime() {
        return startPeriodDateTime;
    }

    public void setStartPeriodDateTime(LocalDateTime startPeriodDateTime) {
        this.startPeriodDateTime = startPeriodDateTime;
    }

    public LocalDateTime getEndPeriodDateTime() {
        return endPeriodDateTime;
    }

    public void setEndPeriodDateTime(LocalDateTime endPeriodDateTime) {
        this.endPeriodDateTime = endPeriodDateTime;
    }

    public void setIssueTypes(String[] issueTypes) {
        issueTypesIds=null;
        if(issueTypes!=null || issueTypes.length==0) {
            issueTypesIds = new ArrayList<Long>(issueTypes.length);
            for (int i=0;i<issueTypes.length;i++) {
                issueTypesIds.add(Long.parseLong(issueTypes[i]));
            }
        }
    }

    public List<Long> getIssueTypes() {
       return issueTypesIds;
    }

    public void setIssueCategoriesIds(String[] issueCategoriesIds) {
        this.issueCategoriesIds=null;
        if(issueCategoriesIds!=null || issueCategoriesIds.length==0) {
            this.issueCategoriesIds = new ArrayList<Long>(issueCategoriesIds.length);
            for (int i=0;i<issueCategoriesIds.length;i++) {
                this.issueCategoriesIds.add(Long.parseLong(issueCategoriesIds[i]));
            }
        }
    }

    public void setOrganizationsIds(String[] organizationsIds) {
        this.organizationsIds=null;
        if(organizationsIds!=null || organizationsIds.length==0) {
            this.organizationsIds = new ArrayList<Long>(organizationsIds.length);
            for (int i=0;i<organizationsIds.length;i++) {
                this.organizationsIds.add(Long.parseLong(organizationsIds[i]));
            }
        }
    }

    public List<Long> getIssueCategoriesIds() {
       return issueCategoriesIds;
    }

    public List<Long> getOrganizationsIds() {
       return organizationsIds;
    }

    public boolean getOnlyMyIssuesFlag() {
        return this.onlyMyIssuesFlag;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if(pageNumber==null || pageNumber<1){
            pageNumber=1;
        }
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPagesCount() {
        return (this.issuesPage!=null)?this.issuesPage.getTotalPages():0;
    }

    public Integer getEntitiesPerPage() {
        return entitiesPerPage;
    }

    public void setEntitiesPerPage(Integer entitiesPerPage) {
        this.entitiesPerPage = entitiesPerPage;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
