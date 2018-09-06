package controller.struts.action.admin.userAccounts;

import com.google.gson.*;
import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import dataAccess.common.Node;
import domain.*;
import domain.common.Finder;
import domain.common.SimpleFinder;
import domain.common.exception.ValidationFailedException;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smartvalidation.constraintViolation.ConstraintViolation;
import smartvalidation.constraintViolation.PropertyConstraintViolation;

import java.util.*;

import static dataAccess.OrganizationsSpecifications.organizationsHasAtLeastOneResponsiblePerson;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class AddUserAccountAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AddUserAccountAction.class);

    private UserAccount userAccount = new UserAccount();

    private Long organizationId;
    private Long responsiblePersonId;

    private List<Organization> organizationsWithReponsiblePersonsList =new ArrayList<>();


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("userAccount:add:*:*");
        loadOrganizationsWithReponsiblePerson();
        setUserProfileFieldsToNullIfTheseAreEmpty();
        if(!userAccount.getUserGroup().equals(UserGroup.ResponsiblePerson)){
            userAccount = userAccount.save();
        } else{
            try {
                ResponsiblePersonUserAccount rpUserAccount=new ResponsiblePersonUserAccount(userAccount.getId(),userAccount.getAccountInfo(),
                        userAccount.getProfile(),organizationId,responsiblePersonId);
                userAccount= rpUserAccount.save();
            } catch (ValidationFailedException e){
                for(ConstraintViolation cv:e.getConstraintsViolations()){
                    if(cv instanceof PropertyConstraintViolation) {
                        if (((PropertyConstraintViolation) cv).getPropertyPath().equals("userAccount.responsiblePerson.id")){
                            ((PropertyConstraintViolation) cv).setPropertyPath("responsiblePersonId");
                        }
                        if (((PropertyConstraintViolation) cv).getPropertyPath().equals("userAccount.organization.id")){
                            ((PropertyConstraintViolation) cv).setPropertyPath("organizationId");
                        }
                    }
                }
                throw e;
            }
        }
        userAccount = new UserAccount();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("userAccount:add:*:*");
        loadOrganizationsWithReponsiblePerson();
        userAccount=new UserAccount();
        userAccount.setUserGroup(UserGroup.Operator);
        responsiblePersonId=-1L;
        organizationId=-1L;
    }

    protected void setUserProfileFieldsToNullIfTheseAreEmpty(){
        if(userAccount.getProfile().getSurname().equals("")){
            userAccount.getProfile().setSurname(null);
        }
        if(userAccount.getProfile().getPatronymic().equals("")){
            userAccount.getProfile().setPatronymic(null);
        }
        if(userAccount.getProfile().getEmail().equals("")){
            userAccount.getProfile().setEmail(null);
        }
    }

    private void loadOrganizationsWithReponsiblePerson() {
        Finder<Organization,Long> organizationsFinder = (Finder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsWithoutSoftDeletedFinder");
        organizationsWithReponsiblePersonsList = organizationsFinder.findAll(organizationsHasAtLeastOneResponsiblePerson(),(Node<String>) null,Node.rootNode().addChild(Organization_.responsiblePeople.getName()));
    }

    public String getOrganizationsWithResponsiblePersonsListAsJson(){
        try {

            JsonArray rootJsonArray = new JsonArray();
            for(Organization organization:organizationsWithReponsiblePersonsList){
                JsonObject organizationJsonObject=new JsonObject();
                JsonArray responsiblePeopleJsonArray = new JsonArray();
                if(organization.getResponsiblePeople()!=null){
                    for(ResponsiblePerson rp:organization.getResponsiblePeople()){
                        JsonObject responsiblePersonJsonObject=new JsonObject();
                        responsiblePersonJsonObject.add("id",new JsonPrimitive(rp.getId()));
                        responsiblePersonJsonObject.add("name",new JsonPrimitive(StringEscapeUtils.escapeHtml4(rp.getFullName())));
                        responsiblePeopleJsonArray.add(responsiblePersonJsonObject);
                    }
                }
                organizationJsonObject.add("id",new JsonPrimitive(organization.getId()));
                organizationJsonObject.add("name",new JsonPrimitive(StringEscapeUtils.escapeHtml4(organization.getName())));
                organizationJsonObject.add("responsiblePeople",responsiblePeopleJsonArray);
                rootJsonArray.add(organizationJsonObject);
            }
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return gson.toJson(rootJsonArray);
        }catch (Exception e){
            LOG.error(e);
            return null;
        }
    }

    public Map<String,String> findAllOrganizations(){
        HashMap<String, String> result = new LinkedHashMap<>();
        result.put("",getText("All"));
        for (Organization organization : organizationsWithReponsiblePersonsList) {
            result.put(organization.getId().toString(),organization.getName());
        }
        return result;
    }

    public Map<String,String> findAllResponsiblePersons() {
        HashMap<String, String> result = new LinkedHashMap<>();
        for (Organization organization : organizationsWithReponsiblePersonsList){
            for (ResponsiblePerson responsiblePerson : organization.getResponsiblePeople()) {
                result.put(responsiblePerson.getId().toString(), responsiblePerson.getFullName());
            }
        }
        return result;
    }

    public Map<String, String> findAllUserGroups() {
        HashMap<String, String> result = new HashMap<>();
        for (UserGroup priority : UserGroup.values()) {
            result.put(priority.name(), getText(priority.name()));
        }
        return result;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(Long responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
