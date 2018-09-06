package domain;


import common.argumentAssert.Assert;
import dataAccess.common.Node;
import domain.common.HavingNameAndLongIdDomainObject;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "IssueCategories")
public class IssueCategory extends HavingNameAndLongIdDomainObject<IssueCategory> {

    public static final String ISSUE_TYPES = "issueTypes";

    @OneToMany(targetEntity = IssueType.class, mappedBy = "issueCategory")
    @OrderBy(value="name asc")
    private List<IssueType> issueTypes = new ArrayList<>();

    public IssueCategory() {
    }

    public IssueCategory(Long id) {
        super(id);
    }

    public IssueCategory(Long id, String name) {
        super(id, name);
    }

    public List<IssueType> getIssueTypes() {
        return issueTypes;
    }

    public void setIssueTypes(List<IssueType> issueTypes) {
        this.issueTypes = issueTypes;
    }

    @Transactional
    public IssueType addIssueType(IssueType issueType){
        IssueCategory self=findNotSoftDeletedSelf();
        issueType.setIssueCategory(self);
        return issueType.save();
    }

    @Override
    protected void beforeHardRemove() {
        super.beforeHardRemove();
        for (IssueType issueType: getIssueTypes()){
            issueType.hardRemove();
        }
    }

    @Override
    protected void beforeSoftRemove(){
        super.beforeSoftRemove();
        for (IssueType issueType: getIssueTypes()){
            issueType.softRemove();
        }
    }

    @Override
    protected void fillSelfForUpdate(IssueCategory self) {
        super.fillSelfForUpdate(self);
        self.setName(getName());
    }

    @Override
    protected void doInitializeProperties(Node<String> initTree) {
        if(initTree.containsData(ISSUE_TYPES)){
            getIssueTypes().size();
        }
    }


    @Override
    public String toString() {
        return "IssueCategory{" +
                "id='" + id + '\'' +
                ", name=" + name +
                "}";
    }
}
