package controller.struts.action.main.appeals;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;

import dataAccess.UserAccountsSpecifications;
import dataAccess.common.Node;
import domain.Appeal;
import domain.Appeal_;
import domain.IssueCategory;
import domain.common.SimpleFinder;
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
import static dataAccess.AppealsSpecifications.*;
import static dataAccess.UserAccountsSpecifications.userAccountBelongToOneOfGroups;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Артем on 25.03.2018.
 */
public class AppealsPageAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AppealsPageAction.class);

    protected Page<Appeal> appealsPage;
    protected List<Appeal.State> appealsStates;
    protected List<Long> issueCategoriesIds=null;
    protected Long appealCreatorId=null;
    protected boolean onlyMyAppealsFlag;
        protected LocalDateTime startPeriodDateTime;
    protected LocalDateTime endPeriodDateTime;

    protected Long appealId;
    protected String phoneNumber;
    protected String citizenSurname;
    protected String citizenName;
    protected String citizenPatronymic;


    private Integer entitiesPerPage = 15;
    private Integer pageNumber = 1;

    protected Specifications findSpecification=null;

    public AppealsPageAction() {
        super(false);
    }

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:add,view:*:*");
        SimpleFinder<Appeal,Long> finder = (SimpleFinder<Appeal,Long>)BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        buildFindSpecification();
        if(findSpecification!=null) {
            appealsPage = finder.findAll(findSpecification, new PageRequest(pageNumber - 1, entitiesPerPage,sort),
                    Node.rootNode().addChild(Appeal_.issues.getName()));
        } else{
            appealsPage = finder.findAll(new PageRequest(pageNumber - 1, entitiesPerPage,sort),Node.rootNode().addChild(Appeal_.issues.getName()));
        }
        return CustomResults.SUCCESS;
    }

    protected void buildFindSpecification(){
        UserAccount currentUser = SecuritySubjectUtils.getCurrentUserAccount();
        if(appealsStates!=null && appealsStates.size()!=0) {
            mergeToFindSpecification(appealHasStates(appealsStates));
        }
        if (onlyMyAppealsFlag) {
            mergeToFindSpecification(appealHasCreatorWithId(currentUser.getId()));
        }
        if(issueCategoriesIds!=null && issueCategoriesIds.size()!=0){
            mergeToFindSpecification(appealHasIssuesWithCategories(issueCategoriesIds));
        }
        if(appealCreatorId!=null){
            mergeToFindSpecification(appealHasCreatorWithId(appealCreatorId));
        }
        if(startPeriodDateTime!=null && endPeriodDateTime!=null  && (startPeriodDateTime.equals(endPeriodDateTime) || startPeriodDateTime.isBefore(endPeriodDateTime))){
            mergeToFindSpecification(appealCreatedInPeriodOf(fromUserPrefZoneToSystemZone(startPeriodDateTime),
                    fromUserPrefZoneToSystemZone(endPeriodDateTime)));
        }
        if(appealId!=null){
            mergeToFindSpecification(appealHasId(appealId));
        }
        if(citizenSurname!=null && !citizenSurname.equals("")){
            mergeToFindSpecification(appealHasCitizenSurname(citizenSurname));
        }
        if(citizenName!=null && !citizenName.equals("")){
            mergeToFindSpecification(appealHasCitizenName(citizenName));
        }
        if(citizenPatronymic!=null && !citizenPatronymic.equals("")){
            mergeToFindSpecification(appealHasCitizenPatronymic(citizenPatronymic));
        }
        if(phoneNumber!=null && !phoneNumber.equals("")){
            int firstUnderscoreIndex=phoneNumber.indexOf('_');
            if(firstUnderscoreIndex!=-1) {
                phoneNumber = phoneNumber.substring(0, firstUnderscoreIndex);
            }
            mergeToFindSpecification(appealHasContactPhoneNumber(phoneNumber));
        }
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:add,view:*:*");
        UserAccount currentUser=SecuritySubjectUtils.getCurrentUserAccount();
        setOnlyMyAppealsFlag((currentUser.getUserGroup().equals(UserGroup.Operator)));
        doExecute();
    }

    protected void mergeToFindSpecification(Specification additionalSpecification){
        if(findSpecification!=null){
            findSpecification=findSpecification.and(additionalSpecification);
        } else{
            findSpecification=where(additionalSpecification);
        }
    }

    public Map<String, String> findAllAppealStates(){
        HashMap<String, String> result = new LinkedHashMap();
        for (Appeal.State state :  Appeal.State.values()) {
            result.put(state.name(), getText(state.name()));
        }
        return result;
    }

    public Map<String, String> findAllIssueCategories(){
        SimpleFinder<IssueCategory,Long> finder = (SimpleFinder<IssueCategory,Long>)BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesWithoutSoftDeletedFinder");
        List<IssueCategory> list =finder.findAll(new Sort("name"));
        HashMap<String, String> result = new LinkedHashMap();
        for (IssueCategory issueCategory :  list) {
            result.put(issueCategory.getId().toString(), issueCategory.getName());
        }
        return result;
    }

    public Map<String, String> findAllAppealCreators(){
        try {

            SimpleFinder<UserAccount, Long> finder = (SimpleFinder<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsWithoutSoftDeletedFinder");
            List<UserAccount> list = finder.findAll(
                    userAccountBelongToOneOfGroups(new UserGroup[]{UserGroup.Operator, UserGroup.ShiftSupervisor,UserGroup.Admin}),
                    new PageRequest(0, Integer.MAX_VALUE, new Sort("profile.surname"))).getContent();
            HashMap<String, String> result = new LinkedHashMap();
            for (UserAccount userAccount : list) {
                result.put(userAccount.getId().toString(), userAccount.getProfile().getFullName());
            }
            return result;
        } catch (Exception e){
            LOG.error(e);
            return Collections.EMPTY_MAP;
        }
    }

    public Page<Appeal> getAppealsPage() {
        return appealsPage;
    }

    public void setAppealsPage(Page<Appeal> appealsPage) {
        this.appealsPage = appealsPage;
    }

    public List<Appeal.State> getAppealsStates() {
        return appealsStates;
    }

    public void setAppealsStates(List<Appeal.State> appealsStates) {
        this.appealsStates = appealsStates;
    }

    public boolean isOnlyMyAppealsFlag() {
        return onlyMyAppealsFlag;
    }

    public void setOnlyMyAppealsFlag(boolean onlyMyAppealsFlag) {
        this.onlyMyAppealsFlag = onlyMyAppealsFlag;
    }

    public boolean getOnlyMyAppealsFlag() {
        return this.onlyMyAppealsFlag;
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

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if(pageNumber==null || pageNumber<1){
            pageNumber=1;
        }
        this.pageNumber = pageNumber;
    }

    public void setIssueCategoriesIds(String[] issueCategoriesIds) {
        if(issueCategoriesIds!=null || issueCategoriesIds.length==0) {
            this.issueCategoriesIds = new ArrayList<Long>(issueCategoriesIds.length);
            for (int i=0;i<issueCategoriesIds.length;i++) {
                this.issueCategoriesIds.add(Long.parseLong(issueCategoriesIds[i]));
            }
        }
    }

    public List<Long> getIssueCategoriesIds() {
        return issueCategoriesIds;
    }

    public Long getAppealId() {
        return appealId;
    }

    public void setAppealId(Long appealId) {
        this.appealId = appealId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCitizenSurname() {
        return citizenSurname;
    }

    public void setCitizenSurname(String citizenSurname) {
        this.citizenSurname = citizenSurname;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public String getCitizenPatronymic() {
        return citizenPatronymic;
    }

    public void setCitizenPatronymic(String citizenPatronymic) {
        this.citizenPatronymic = citizenPatronymic;
    }

    public Integer getEntitiesPerPage() {
        return entitiesPerPage;
    }

    public void setEntitiesPerPage(Integer entitiesPerPage) {
        this.entitiesPerPage = entitiesPerPage;
    }

    public Integer getTotalPagesCount() {
        return (this.appealsPage!=null)?this.appealsPage.getTotalPages():0;
    }

    public Long getAppealCreatorId() {
        return appealCreatorId;
    }

    public void setAppealCreatorId(Long appealCreatorId) {
        this.appealCreatorId = appealCreatorId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
