package controller.struts.action.main.appeals;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import dataAccess.common.Node;
import domain.*;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;

import static dataAccess.OrganizationsSpecifications.organizationsHasType;

/**
 * Created by Артем on 25.03.2018.
 */
public class EditAppealMainInfoAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(EditAppealMainInfoAction.class);

    protected Appeal appeal=new Appeal();
    protected long appealId;
    protected LocalDateTime createdDateTime;


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:edit:*:*");
        setAppealCitizenNotRequiredFieldsToNullIfFieldsAreEmptyStrings();
        appeal.save();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:edit:*:*");
        loadAppealWithIssues();
        createdDateTime=appeal.getCreatedDateTime();
    }

    protected void loadAppealWithIssues(){
        SimpleFinder<Appeal,Long> appealsFinder = (SimpleFinder<Appeal,Long>)BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        appeal=appealsFinder.findOne(appealId,Node.rootNode().addChild(new Node(Appeal_.issues.getName()).addChild(Issue_.organizations.getName())
                .addChild(Issue_.issueTypes.getName())
        ));
    }


    public Map<String, String> findAllSmo(){
        try {
            SimpleFinder<Organization, Long> finder = (SimpleFinder<Organization, Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
            List<Organization> smosList = finder.findAll(organizationsHasType(Organization.Type.InsuranceMedicalOrganization));
            HashMap<String, String> result = new LinkedHashMap<>();
            for (Organization smo : smosList) {
                result.put(smo.getId().toString(), smo.getName());
            }
            return result;
        } catch (Exception e){
            LOG.error("Error has occured on findAllSmoOrganizations",e);
            return  new HashMap<>();
        }
    }

    protected void setAppealCitizenNotRequiredFieldsToNullIfFieldsAreEmptyStrings(){
        if(appeal==null || appeal.getCitizen()==null){
            return;
        }
        appeal.getCitizen().setPatronymic(StringUtils.trimToNull(appeal.getCitizen().getPatronymic()));
        if(StringUtils.isBlank(appeal.getCitizen().getOmsPolicyNumber())){
            appeal.getCitizen().setOmsPolicyNumber(null);
            appeal.getCitizen().setSmo(null);
        }
        appeal.getCitizen().setPlaceOfLiving(StringUtils.trimToNull(appeal.getCitizen().getPlaceOfLiving()));
    }

    public Appeal getAppeal() {
        return appeal;
    }

    public long getAppealId() {
        return appealId;
    }

    public void setAppealId(long appealId) {
        this.appealId = appealId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
