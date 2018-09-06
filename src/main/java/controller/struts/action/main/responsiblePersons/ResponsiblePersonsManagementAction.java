package controller.struts.action.main.responsiblePersons;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.PageableAction;
import domain.Organization;
import domain.ResponsiblePerson;
import domain.common.SimpleFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static dataAccess.ResponsiblePersonsSpecifications.responsiblePersonsBelongsToCategory;

public class ResponsiblePersonsManagementAction extends PageableAction<ResponsiblePerson> {

    protected static final Logger LOG = LogManager.getLogger(ResponsiblePersonsManagementAction.class);

    private Long organizationId;
    private Organization organization;

    @Override
    protected Page<ResponsiblePerson> loadPage() {
        SimpleFinder<Organization,Long> organizationsFinder = (SimpleFinder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
        organization=organizationsFinder.findOne(organizationId);

        SimpleFinder<ResponsiblePerson,Long> finder = (SimpleFinder<ResponsiblePerson,Long>) BeanFactoryProvider.getBeanFactory().getBean("responsiblePersonsFinder");
        return finder.findAll(responsiblePersonsBelongsToCategory(organizationId),new PageRequest(pageNumber - 1, entitiesPerPage,new Sort("name")));
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "responsibleperson:add,edit,remove,view:*:*";
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
