package controller.struts.action.main.organizations;

import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Organization;
import domain.security.SecuritySubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class AddOrganizationAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AddOrganizationAction.class);

    private Organization organization = new Organization();

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("organization:add:*:*");
        organization.save();
        organization = new Organization();
        return CustomResults.SUCCESS;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
