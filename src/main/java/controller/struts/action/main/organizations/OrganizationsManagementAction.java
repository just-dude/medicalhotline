package controller.struts.action.main.organizations;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.PageableAction;
import domain.Issue;
import domain.Organization;
import domain.common.SimpleFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashSet;

public class OrganizationsManagementAction extends PageableAction<Organization> {

    protected static final Logger LOG = LogManager.getLogger(OrganizationsManagementAction.class);

    @Override
    protected Page<Organization> loadPage() {
        SimpleFinder<Organization,Long> finder = (SimpleFinder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
        return finder.findAll(new PageRequest(pageNumber - 1, entitiesPerPage,new Sort("name")));
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "organization:add,edit,remove,view:*:*";
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
