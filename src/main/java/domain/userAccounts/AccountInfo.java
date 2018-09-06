package domain.userAccounts;

import javax.persistence.Embeddable;

/**
 * Created by SuslovAI on 17.08.2017.
 */

@Embeddable
public class AccountInfo {

    private String login;

    private String hashedPassword;

    private String salt;

    public AccountInfo() {
    }

    public AccountInfo(String login, String hashedPassword, String salt) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "login='" + login + "\'}";
    }
}
