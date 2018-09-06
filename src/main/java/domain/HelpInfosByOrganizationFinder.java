package domain;

import dataAccess.HelpInfosByOrganizationJpaRepository;
import dataAccess.HelpInfosJpaRepository;
import dataAccess.common.exception.DataAccessException;
import domain.common.SimpleFinder;
import domain.common.exception.BusinessException;
import domain.common.exception.DataAccessFailedBuisnessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class HelpInfosByOrganizationFinder extends SimpleFinder<HelpInfoByOrganization,  HelpInfoByOrganization.HelpInfoByOrganizationId> {

    public HelpInfosByOrganizationFinder(HelpInfosByOrganizationJpaRepository dao) {
        super(dao);
    }

    public PageImpl<HelpInfoByOrganization> findAllForAllIssueTypesAndOrganizations(Pageable pageable,Long issueCategoryId,Long issueTypeId,Long organizationId) {
        try {
            return ((HelpInfosByOrganizationJpaRepository) repository).findAllForAllIssueTypesAndOrganizations(pageable,issueCategoryId,issueTypeId,organizationId);
        } catch (DataAccessException e)  {
            throw new DataAccessFailedBuisnessException("Data access exception is occured",e);
        } catch (Exception e) {
            throw new BusinessException("Exception has occured", e);
        }
    }
}
