package controller.struts.action.common;

import domain.IssueCategory;
import domain.common.DomainObject;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public abstract class RemoveAction extends FormProviderHandlingExceptionsBaseAction {


    private Long id;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission(getPermissionsStringForCheck());
        getDomainObjectWithId(id).remove();
        return CustomResults.SUCCESS;
    }

    protected abstract DomainObject getDomainObjectWithId(Long id);

    protected abstract String getPermissionsStringForCheck();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
