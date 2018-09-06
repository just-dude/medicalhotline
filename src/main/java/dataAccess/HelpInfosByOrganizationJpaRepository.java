package dataAccess;

import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.HelpInfoByOrganization;
import domain.IssueType;
import domain.Organization;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuslovAI on 23.10.2017.
 */
public class HelpInfosByOrganizationJpaRepository extends SoftDeleteModedJpaRepository<HelpInfoByOrganization, HelpInfoByOrganization.HelpInfoByOrganizationId> {

    private static final String findAllForAllIssueTypesAndOrganizationsSQL="SELECT  it,o,hi FROM IssueType AS it, Organization AS o " +
            "LEFT JOIN HelpInfoByOrganization AS hi ON it.id=hi.issueType.id AND o.id=hi.organization.id " +
            "INNER JOIN FETCH it.issueCategory AS ic " +
            "WHERE (hi.isSoftDeleted=0 OR hi.isSoftDeleted IS NULL) AND (it.isSoftDeleted=0 OR it.isSoftDeleted IS NULL) " +
            "AND (ic.isSoftDeleted=0 OR ic.isSoftDeleted IS NULL) AND (o.isSoftDeleted=0 OR o.isSoftDeleted IS NULL) %s"+
            "ORDER BY ic.name,it.name";

    private static final String countOfAllForAllIssueTypesAndOrganizationsSQL="SELECT  count(it) FROM IssueType AS it, Organization AS o " +
            "LEFT JOIN HelpInfoByOrganization AS hi ON it.id=hi.issueType.id AND o.id=hi.organization.id " +
            "INNER JOIN it.issueCategory AS ic " +
            "WHERE (hi.isSoftDeleted=0 OR hi.isSoftDeleted IS NULL) AND (it.isSoftDeleted=0 OR it.isSoftDeleted IS NULL) " +
            "AND (ic.isSoftDeleted=0 OR ic.isSoftDeleted IS NULL) AND (o.isSoftDeleted=0 OR o.isSoftDeleted IS NULL) %s";

    public HelpInfosByOrganizationJpaRepository(EntityManager em) {
        super(em, HelpInfoByOrganization.class);
    }

    public HelpInfosByOrganizationJpaRepository(EntityManager em, FindMode findMode) {
        super(em, HelpInfoByOrganization.class, findMode);
    }

    public PageImpl<HelpInfoByOrganization> findAllForAllIssueTypesAndOrganizations(Pageable pageable,Long issueCategoryId,Long issueTypeId,Long organizationId) {
        final int ISSUE_TYPE = 0;
        final int ORGANIZATION = 1;
        final int HELP_INFO=2;
        String partOfWhereClause= buildPartOfWhereClause(new String[]{"ic.id","it.id","o.id"},
                new Object[]{issueCategoryId,issueTypeId,organizationId});
        CriteriaBuilder cb = em.getCriteriaBuilder();
        TypedQuery<Object[]> typedQuery = em.createQuery(String.format(findAllForAllIssueTypesAndOrganizationsSQL,partOfWhereClause), Object[].class);
        typedQuery.setFirstResult(pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Object[]> results = typedQuery.getResultList();
        List<HelpInfoByOrganization> helpInfoByOrganizationList=new ArrayList<>();
        for (Object[] result : results) {
            HelpInfoByOrganization helpInfoByOrganization=((HelpInfoByOrganization)result[HELP_INFO]);
            IssueType issueType=((IssueType)result[ISSUE_TYPE]);
            Organization organization=((Organization)result[ORGANIZATION]);
            if(helpInfoByOrganization!=null){
                helpInfoByOrganization.setIssueType(issueType);
                helpInfoByOrganization.setOrganization(organization);
            } else{
                helpInfoByOrganization=new HelpInfoByOrganization(issueType,organization);
            }

            helpInfoByOrganizationList.add(helpInfoByOrganization);
        }
        return new PageImpl<HelpInfoByOrganization>(helpInfoByOrganizationList, pageable, countOfAllForAllIssueTypesAndOrganizations(partOfWhereClause));
    }

    private String buildPartOfWhereClause(String[] paramNames, Object[] paramValues){
        String partOfWhereClause="";
        for(int i=0;i<paramValues.length;i++){
            if(paramValues[i]!=null){
                partOfWhereClause+="AND "+paramNames[i]+"="+paramValues[i]+" ";
            }
        }
        return partOfWhereClause;
    }


    public long countOfAllForAllIssueTypesAndOrganizations(String partOfWhereClause) {
        TypedQuery<Long> typedQuery = em.createQuery(String.format(countOfAllForAllIssueTypesAndOrganizationsSQL,partOfWhereClause),Long.class);
        return typedQuery.getSingleResult();
    }


}
