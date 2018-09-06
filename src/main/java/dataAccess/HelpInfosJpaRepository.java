package dataAccess;

import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.HelpInfo;
import domain.IssueType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuslovAI on 23.10.2017.
 */
public class HelpInfosJpaRepository extends SoftDeleteModedJpaRepository<HelpInfo, Long> {

    private static final String findAllForAllIssueTypesSQL="SELECT  it,hi FROM IssueType AS it " +
            "LEFT JOIN HelpInfo AS hi ON it.id=hi.id " +
            "INNER JOIN FETCH it.issueCategory AS ic " +
            "WHERE (hi.isSoftDeleted=0 OR hi.isSoftDeleted IS NULL) AND (it.isSoftDeleted=0 OR it.isSoftDeleted IS NULL) AND (ic.isSoftDeleted=0 OR ic.isSoftDeleted IS NULL) %s"+
            "ORDER BY ic.name,it.name";

    private static final String countOfAllForAllIssueTypesSQL="SELECT  count(it) FROM IssueType AS it " +
            "LEFT JOIN HelpInfo AS hi ON it.id=hi.id " +
            "INNER JOIN it.issueCategory AS ic " +
            "WHERE (hi.isSoftDeleted=0 OR hi.isSoftDeleted IS NULL) AND (it.isSoftDeleted=0 OR it.isSoftDeleted IS NULL) AND (ic.isSoftDeleted=0 OR ic.isSoftDeleted IS NULL) %s"+
            "ORDER BY ic.name,it.name";

    public HelpInfosJpaRepository(EntityManager em) {
        super(em, HelpInfo.class);
    }

    public HelpInfosJpaRepository(EntityManager em, FindMode findMode) {
        super(em, HelpInfo.class, findMode);
    }

    public PageImpl<HelpInfo> findAllForAllIssueTypes(Pageable pageable, Long issueCategoryId, Long issueTypeId) {
        final int ISSUE_TYPE = 0;
        final int HELP_INFO=1;
        String partOfWhereClause= buildPartOfWhereClause(new String[]{"ic.id","it.id"},new Object[]{issueCategoryId,issueTypeId});
        CriteriaBuilder cb = em.getCriteriaBuilder();
        TypedQuery<Object[]> typedQuery = em.createQuery(String.format(findAllForAllIssueTypesSQL,partOfWhereClause), Object[].class);
        typedQuery.setFirstResult(pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Object[]> results = typedQuery.getResultList();
        List<HelpInfo> helpInfoList=new ArrayList<>();
        for (Object[] result : results) {
            HelpInfo helpInfo=((HelpInfo)result[HELP_INFO]);
            IssueType issueType=((IssueType)result[ISSUE_TYPE]);
            if(helpInfo!=null){
                helpInfo.setIssueType(issueType);
            } else{
                helpInfo=new HelpInfo(issueType);
            }

            helpInfoList.add(helpInfo);
        }
        return new PageImpl<HelpInfo>(helpInfoList, pageable, countOfAllForAllIssueTypes(partOfWhereClause));
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


    public long countOfAllForAllIssueTypes(String partOfWhereClause) {
        TypedQuery<Long> typedQuery = em.createQuery(String.format(countOfAllForAllIssueTypesSQL,partOfWhereClause),Long.class);
        return typedQuery.getSingleResult();
    }


}
