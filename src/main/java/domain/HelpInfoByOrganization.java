package domain;

import domain.common.DomainObject;
import domain.common.exception.EntityWithSuchIdDoesNotExistsBusinessException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HelpInfosByOrganization")
public class HelpInfoByOrganization extends DomainObject<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId>{

    @EmbeddedId
    private HelpInfoByOrganizationId id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issueTypeId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private IssueType issueType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizationId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Organization organization;

    @Lob
    @Column
    private String text;

    public HelpInfoByOrganization() {
        setId(new HelpInfoByOrganization.HelpInfoByOrganizationId());
    }

    public HelpInfoByOrganization(IssueType issueType, Organization organization, String text) {
        this.setId(new HelpInfoByOrganizationId(issueType,organization));
        this.issueType = issueType;
        this.organization = organization;
        this.text = text;
    }

    public HelpInfoByOrganization(IssueType issueType, Organization organization) {
        this.setId(new HelpInfoByOrganizationId(issueType,organization));
        this.issueType = issueType;
        this.organization = organization;
    }

    public HelpInfoByOrganization(HelpInfoByOrganizationId helpInfoByOrganizationId) {
        this.setId(helpInfoByOrganizationId);
    }

    @Override
    public HelpInfoByOrganizationId getId() {
        return id;
    }

    public void setId(HelpInfoByOrganizationId id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
        if(issueType !=null) {
            getId().setIssueTypeId(issueType.getId());
        }
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
        if(organization!=null) {
            getId().setOrganizationId(organization.getId());
        }
    }

    @Override
    protected boolean isNew() {
        return !getWithoutSoftDeletedFinder().exists(getId());
    }

    @Override
    protected void fillSelfForUpdate(HelpInfoByOrganization self) {
        super.fillSelfForUpdate(self);
        self.setText(getText());
    }


    @Embeddable
    public static class HelpInfoByOrganizationId implements Serializable {

        private long issueTypeId;
        private long organizationId;

        public HelpInfoByOrganizationId() {
        }

        public HelpInfoByOrganizationId(long issueTypeId, long organizationId) {
            this.issueTypeId = issueTypeId;
            this.organizationId = organizationId;
        }

        public HelpInfoByOrganizationId(IssueType issueType, Organization organization) {
            this.issueTypeId = issueType.getId();
            this.organizationId = organization.getId();
        }

        public long getIssueTypeId() {
            return issueTypeId;
        }

        public void setIssueTypeId(long issueTypeId) {
            this.issueTypeId = issueTypeId;
        }

        public long getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(long organizationId) {
            this.organizationId = organizationId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            HelpInfoByOrganizationId that = (HelpInfoByOrganizationId) o;

            return new EqualsBuilder()
                    .append(issueTypeId, that.issueTypeId)
                    .append(organizationId, that.organizationId)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(issueTypeId)
                    .append(organizationId)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return "HelpInfoByOrganizationId{" +
                    "issueTypeId=" + issueTypeId +
                    ", organizationId=" + organizationId +
                    '}';
        }
    }
}
