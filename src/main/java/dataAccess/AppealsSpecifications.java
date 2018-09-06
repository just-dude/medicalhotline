package dataAccess;

import common.argumentAssert.Assert;
import domain.Appeal;
import domain.Appeal_;
import domain.Citizen_;
import domain.Issue_;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

public class AppealsSpecifications {

    public static Specification<Appeal> appealHasStates(List<Appeal.State> states) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return root.get(Appeal_.state).in(states.toArray());
            }
        };
    }

    public static Specification<Appeal> appealHasId(Long appealId) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Appeal_.id), appealId);
            }
        };
    }

    public static Specification<Appeal> appealHasContactPhoneNumber(String contactPhoneNumber) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get(Appeal_.contactPhoneNumber), contactPhoneNumber+"%");
            }
        };
    }

    public static Specification<Appeal> appealHasCitizenSurname(String citizenSurname) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get(Appeal_.citizen).get(Citizen_.surname), prepareSearchString(citizenSurname));
            }
        };
    }

    public static Specification<Appeal> appealHasCitizenName(String citizenName) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get(Appeal_.citizen).get(Citizen_.name), prepareSearchString(citizenName));
            }
        };
    }

    public static Specification<Appeal> appealHasCitizenPatronymic(String citizenPatronymic) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.like(root.get(Appeal_.citizen).get(Citizen_.patronymic), prepareSearchString(citizenPatronymic));
            }
        };
    }

    private static String prepareSearchString(String searchString){
        if(searchString.contains("*")){
            return searchString.replace('*','%');
        } else{
            return "%"+searchString+"%";
        }
    }



    public static Specification<Appeal> appealNotEqual(long appealId) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.not(cb.equal(root, appealId));
            }
        };
    }

    public static Specification<Appeal> appealHasCreatorWithId(long creatorId) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Appeal_.creator.getName()), creatorId);
            }
        };
    }

    public static Specification<Appeal> appealCreatedInPeriodOf(LocalDateTime startPeriodDateTime, LocalDateTime endPeriodDateTime) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.between(root.get(Appeal_.createdDateTime),startPeriodDateTime,endPeriodDateTime);
            }
        };
    }

    public static Specification<Appeal> appealHasIssuesWithCategories(List<Long> issueCategoriesIds) {
        return new Specification<Appeal> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Assert.notNull(issueCategoriesIds,"issueCategoriesIds");
                Assert.notEmpty(issueCategoriesIds,"issueCategoriesIds");
                query.distinct(true);
                return root.join(Appeal_.issues).get(Issue_.issueCategory).in(issueCategoriesIds);
            }
        };
    }
}
