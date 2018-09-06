package domain;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import domain.common.HavingLongIdDomainObject;
import domain.userAccounts.AccountInfo;
import domain.userAccounts.Profile;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity(name = "ResponsiblePersonUserAccount")
@DiscriminatorValue("ResponsiblePersonUserAccount")
public class ResponsiblePersonUserAccount extends UserAccount{

    @OneToOne(optional = false,targetEntity = Organization.class)
    @JoinColumn(name = "organizationId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private Organization organization;

    @OneToOne(optional = false,targetEntity = ResponsiblePerson.class)
    @JoinColumn(name = "responsiblePersonId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private ResponsiblePerson responsiblePerson;

    public ResponsiblePersonUserAccount() {
        super();
        userGroup=UserGroup.ResponsiblePerson;
    }

    public ResponsiblePersonUserAccount(Long id) {
        super(id);
        userGroup=UserGroup.ResponsiblePerson;
    }

    public ResponsiblePersonUserAccount(AccountInfo accountInfo, Profile profile) {
        super(accountInfo,profile);
        userGroup=UserGroup.ResponsiblePerson;
    }

    public ResponsiblePersonUserAccount(AccountInfo accountInfo, Profile profile, Organization organization,ResponsiblePerson responsiblePerson) {
        super(accountInfo,profile);
        this.organization=organization;
        this.responsiblePerson=responsiblePerson;
        userGroup=UserGroup.ResponsiblePerson;
    }

    public ResponsiblePersonUserAccount(AccountInfo accountInfo, Profile profile, Long organizationId,Long responsiblePersonId) {
        super(accountInfo,profile);
        this.organization=new Organization(organizationId);
        this.responsiblePerson=new ResponsiblePerson(responsiblePersonId);
        userGroup=UserGroup.ResponsiblePerson;
    }

    public ResponsiblePersonUserAccount(Long id, AccountInfo accountInfo, Profile profile, Organization organization,ResponsiblePerson responsiblePerson) {
        super(id,accountInfo,profile);
        this.organization=organization;
        this.responsiblePerson=responsiblePerson;
        userGroup=UserGroup.ResponsiblePerson;
    }

    public ResponsiblePersonUserAccount(Long id, AccountInfo accountInfo, Profile profile, Long organizationId,Long responsiblePersonId) {
        super(id,accountInfo,profile);
        this.organization=new Organization(organizationId);
        this.responsiblePerson=new ResponsiblePerson(responsiblePersonId);
        userGroup=UserGroup.ResponsiblePerson;
    }

    @Override
    public void setUserGroup(UserGroup userGroup) {}

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public ResponsiblePerson getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(ResponsiblePerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    @Override
    protected void fillSelfForUpdate(UserAccount self) {
        super.fillSelfForUpdate(self);
        ResponsiblePersonUserAccount rpUserAccount=(ResponsiblePersonUserAccount)self;
        rpUserAccount.setOrganization(getOrganization());
        rpUserAccount.setResponsiblePerson(getResponsiblePerson());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", accountInfo=" + accountInfo +
                ", profile=" + profile +
                ", group=" + userGroup +
                ", responsiblePerson=" + responsiblePerson +
                ", organization=" + organization +
                '}';
    }
}
