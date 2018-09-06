package controller.struts.action.main.organizations;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Organization;
import domain.common.Finder;
import domain.security.SecuritySubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class EditOrganizationAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(EditOrganizationAction.class);

    private Long organizationId;
    private Organization organization = new Organization();

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("organization:edit:*:*");
        organization = organization.save();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("organization:edit:*:*");
        Finder<Organization, Long> finder = (Finder<Organization, Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
        organization = finder.findOne(organizationId);
    }

    public Map<String,String> findAllOrganizationTypes(){
        HashMap<String, String> result = new HashMap<>();
        for (Organization.Type type : Organization.Type.values()) {
            result.put(type.name(), getText(type.name()));
        }
        return result;
    }

    @Override
    protected void setNotRequiredFieldsToNullIfFieldsAreEmpty(){
        organization.setSeniorOfficialPatronymic(StringUtils.trimToNull(organization.getSeniorOfficialPatronymic()));
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
