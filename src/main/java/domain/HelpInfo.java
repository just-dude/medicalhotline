package domain;

import domain.common.DomainObject;
import domain.common.exception.EntityWithSuchIdDoesNotExistsBusinessException;

import javax.persistence.*;

@Entity
@Table(name = "HelpInfos")
public class HelpInfo extends DomainObject<HelpInfo,Long>{

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private IssueType issueType;

    @Lob
    @Column
    private String text;

    public HelpInfo() {
    }

    public HelpInfo(Long id) {
        this.id = id;
    }

    public HelpInfo(IssueType issueType) {
        if(issueType !=null){
            id = issueType.getId();
        }
        this.issueType = issueType;
    }

    public HelpInfo(IssueType issueType, String text) {
        if(issueType !=null){
            id = issueType.getId();
        }
        this.issueType = issueType;
        this.text = text;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected boolean isNew() {
        return !getWithoutSoftDeletedFinder().exists(getId());
    }

    @Override
    protected void fillSelfForUpdate(HelpInfo self) {
        super.fillSelfForUpdate(self);
        self.setText(getText());
    }
}
