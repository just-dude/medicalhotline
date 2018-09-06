package controller.struts.action.main.appeals;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.Appeal;
import domain.Organization;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specifications;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static dataAccess.AppealsSpecifications.appealHasContactPhoneNumber;
import static dataAccess.AppealsSpecifications.appealNotEqual;
import static dataAccess.OrganizationsSpecifications.organizationsHasType;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Артем on 25.03.2018.
 */
public class AddAppealMainInfoAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(AddAppealMainInfoAction.class);

    protected Appeal appeal=new Appeal();
    protected Boolean isContactPhoneNumberSavedFlag =false;
    protected LocalDateTime createdDateTime;

    protected List<Appeal> similarAppeals=null;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("appeal:add:*:*");
        setAppealCitizenNotRequiredFieldsToNullIfFieldsAreEmptyStrings();
        boolean isContactPhoneNumberSavedFlagBefore = isContactPhoneNumberSavedFlag;
        Appeal savedAppeal = appeal.save();
        appeal.setId(savedAppeal.getId());
        isContactPhoneNumberSavedFlag = true;
        SimpleFinder<Appeal, Long> finder = (SimpleFinder<Appeal, Long>) BeanFactoryProvider.getBeanFactory().getBean("appealsWithoutSoftDeletedFinder");
        similarAppeals = finder.findAll(((Specifications) where(appealHasContactPhoneNumber(savedAppeal.getContactPhoneNumber()))).and(appealNotEqual(savedAppeal.getId())));
        if (!isContactPhoneNumberSavedFlagBefore){
            return "partially-success";
        } else{
            return CustomResults.SUCCESS;
        }
    }


    public Map<String, String> findAllSmo(){
        try {
            SimpleFinder<Organization, Long> finder = (SimpleFinder<Organization, Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsWithoutSoftDeletedFinder");
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

    public boolean getIsContactPhoneNumberSavedFlag(){
        return isContactPhoneNumberSavedFlag;
    }

    public void setIsContactPhoneNumberSavedFlag(Boolean isContactPhoneNumberSavedFlag) {
        this.isContactPhoneNumberSavedFlag = isContactPhoneNumberSavedFlag;
    }

    public Appeal getAppeal() {
        return appeal;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<Appeal> getSimilarAppeals() {
        return similarAppeals;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
