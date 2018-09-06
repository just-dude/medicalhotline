package domain;

import com.google.gson.annotations.Expose;
import domain.common.HavingNameAndLongIdDomainObject;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Артем on 18.03.2018.
 */
@Entity
@Table(name = "IssueTypes")
public class IssueType extends HavingNameAndLongIdDomainObject<IssueType> {

    @Expose(serialize = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "categoryId", referencedColumnName = "id" ,nullable = false)
    private IssueCategory issueCategory;

    public IssueType() {
    }

    public IssueType(Long id) {
        super(id);
    }

    public IssueType(Long id, String name, IssueCategory issueCategory) {
        super(id, name);
        this.issueCategory = issueCategory;
    }

    public IssueCategory getIssueCategory() {
        return issueCategory;
    }

    protected void setIssueCategory(IssueCategory issueCategory) {
        this.issueCategory = issueCategory;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueType)) return false;
        IssueType that = (IssueType) o;
        return  Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getIssueCategory(), that.getIssueCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getName(), getIssueCategory());
    }

    @Override
    public String toString() {
        return "IssueType{" +
                "id='" + id + '\'' +
                ", name=" + name +
                "}";
    }

    @Override
    protected void fillSelfForUpdate(IssueType self) {
        super.fillSelfForUpdate(self);
        self.setName(getName());
    }
}
