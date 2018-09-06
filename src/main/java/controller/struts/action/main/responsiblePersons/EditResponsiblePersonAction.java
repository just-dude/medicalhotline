package controller.struts.action.main.responsiblePersons;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Organization;
import domain.ResponsiblePerson;
import domain.common.Finder;
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
public class EditResponsiblePersonAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(EditResponsiblePersonAction.class);

    private Long organizationId;
    private Organization organization;

    private Long responsiblePersonId;
    private ResponsiblePerson responsiblePerson = new ResponsiblePerson();

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("responsibleperson:edit:*:*");
        loadOrganization();
        responsiblePerson = responsiblePerson.save();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("responsibleperson:edit:*:*");
        loadOrganization();
        Finder<ResponsiblePerson, Long> finder = (Finder<ResponsiblePerson, Long>) BeanFactoryProvider.getBeanFactory().getBean("responsiblePersonsFinder");
        responsiblePerson = finder.findOne(responsiblePersonId);
    }

    protected void loadOrganization(){
        SimpleFinder<Organization,Long> organizationsFinder = (SimpleFinder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
        organization=organizationsFinder.findOne(organizationId);
    }

    @Override
    protected void setNotRequiredFieldsToNullIfFieldsAreEmpty(){
        responsiblePerson.setPatronymic(StringUtils.trimToNull(responsiblePerson.getPatronymic()));
        responsiblePerson.setEmail(StringUtils.trimToNull(responsiblePerson.getEmail()));
    }

    public ResponsiblePerson getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(ResponsiblePerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Long getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(Long responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
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
