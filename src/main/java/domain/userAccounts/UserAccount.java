package domain.userAccounts;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import domain.common.HavingLongIdDomainObject;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;

import javax.persistence.*;

/**
 * Created by SuslovAI on 17.08.2017.
 */
@Entity(name = "UserAccount")
@Table(name = "UserAccounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("UserAccount")
public class UserAccount extends HavingLongIdDomainObject<UserAccount> {


    @Embedded
    protected AccountInfo accountInfo;

    @Embedded
    protected Profile profile;

    @Enumerated(EnumType.STRING)
    protected UserGroup userGroup;

    public UserAccount() {
        this.setProfile(new Profile());
        this.setAccountInfo(new AccountInfo());
    }

    public UserAccount(Long id) {
        super(id);
        this.setProfile(new Profile());
        this.setAccountInfo(new AccountInfo());
    }

    public UserAccount(AccountInfo accountInfo, Profile profile) {
        this.accountInfo = accountInfo;
        this.profile = profile;
    }

    public UserAccount(AccountInfo accountInfo, Profile profile, UserGroup userGroup) {
        this.accountInfo = accountInfo;
        this.profile = profile;
        this.userGroup = userGroup;
    }

    public UserAccount(Long id, AccountInfo accountInfo, Profile profile) {
        super(id);
        this.accountInfo = accountInfo;
        this.profile = profile;
    }

    public UserAccount(Long id, AccountInfo accountInfo, Profile profile, UserGroup group) {
        super(id);
        this.accountInfo = accountInfo;
        this.profile = profile;
        this.userGroup = group;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + getId() +
                ", accountInfo=" + accountInfo +
                ", profile=" + profile +
                ", group=" + getUserGroup() +
                '}';
    }

    @Override
    protected void beforeInsert() {
        super.beforeInsert();
        ByteSource salt = new SecureRandomNumberGenerator().nextBytes();
        String hashedPasswordBase64 = new Sha256Hash(this.getAccountInfo().getHashedPassword(), salt, 256).toBase64();
        this.getAccountInfo().setSalt(Base64.encode(salt.getBytes()));
        this.getAccountInfo().setHashedPassword(hashedPasswordBase64);
    }

    @Override
    protected void fillSelfForUpdate(UserAccount self) {
        super.fillSelfForUpdate(self);
        self.setProfile(getProfile());
        self.setUserGroup(getUserGroup());
    }
}
