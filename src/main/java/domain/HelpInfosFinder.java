package domain;

import common.argumentAssert.Assert;
import dataAccess.HelpInfosJpaRepository;
import dataAccess.common.exception.DataAccessException;
import domain.common.SimpleFinder;
import domain.common.exception.BusinessException;
import domain.common.exception.DataAccessFailedBuisnessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class HelpInfosFinder extends SimpleFinder<HelpInfo, Long> {

    public HelpInfosFinder(HelpInfosJpaRepository dao) {
        super(dao);
    }

    public PageImpl<HelpInfo> findAllForAllIssueTypes(Pageable pageable,Long issueCategoryId,Long issueTypeId) {
        try {
            return ((HelpInfosJpaRepository) repository).findAllForAllIssueTypes(pageable,issueCategoryId,issueTypeId);
        } catch (DataAccessException e) {
            throw new DataAccessFailedBuisnessException("Data access exception is occured",e);
        } catch (Exception e) {
            throw new BusinessException("Exception has occured", e);
        }
    }
}
