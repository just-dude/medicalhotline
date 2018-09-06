package controller.struts.action.main.helpInfosByOrganization;

import controller.struts.action.common.RemoveAction;
import domain.HelpInfoByOrganization;
import domain.IssueType;
import domain.Organization;
import domain.common.DomainObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveHelpInfoByOrganizationAction extends RemoveAction {

    protected static final Logger LOG = LogManager.getLogger(RemoveHelpInfoByOrganizationAction.class);

    private Long issueTypeId;
    private Long organizationId;

    @Override
    protected DomainObject getDomainObjectWithId(Long id) {
        return new HelpInfoByOrganization(new IssueType(issueTypeId),new Organization(organizationId));
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "helpinfo-by-organization:remove:*:*";
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Long issueTypeId) {
        this.issueTypeId = issueTypeId;
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

