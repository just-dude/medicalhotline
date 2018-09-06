package controller.struts.action.main.responsiblePersons;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Organization;
import domain.ResponsiblePerson;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by SuslovAI on 06.10.2017.
 */
public class AddResponsiblePersonAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AddResponsiblePersonAction.class);

    private ResponsiblePerson responsiblePerson = new ResponsiblePerson();

    private Long organizationId;
    private Organization organization;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("responsibleperson:add:*:*");
        loadOrganization();
        new Organization(organizationId).addResponsiblePerson(responsiblePerson);
        responsiblePerson = new ResponsiblePerson();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("responsibleperson:add:*:*");
        loadOrganization();
        responsiblePerson=new ResponsiblePerson();
        responsiblePerson.setPriority(0);
    }

    protected void loadOrganization(){
        SimpleFinder<Organization,Long> organizationsFinder = (SimpleFinder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
        organization=organizationsFinder.findOne(organizationId);
    }

    public ResponsiblePerson getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(ResponsiblePerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    @Override
    protected void setNotRequiredFieldsToNullIfFieldsAreEmpty(){
        responsiblePerson.setPatronymic(StringUtils.trimToNull(responsiblePerson.getPatronymic()));
        responsiblePerson.setEmail(StringUtils.trimToNull(responsiblePerson.getEmail()));
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
