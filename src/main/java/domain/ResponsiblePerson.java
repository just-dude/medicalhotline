package domain;

import com.google.gson.annotations.Expose;
import domain.common.HavingNameAndLongIdDomainObject;
import domain.userAccounts.UserAccount;

import javax.persistence.*;

@Entity
@Table(name = "ResponsiblePersons")
public class ResponsiblePerson  extends HavingNameAndLongIdDomainObject<ResponsiblePerson> {

    private String surname;

    private String patronymic;

    private String contactInfo;

    private String email;

    private String position;

    private int priority;

    @Expose(serialize = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizationId", referencedColumnName = "id" ,nullable = false)
    private Organization organization;

    public ResponsiblePerson() {
    }

    public ResponsiblePerson(Long id) {
        super(id);
    }

    public ResponsiblePerson(String name, String surname, String patronymic, String contactInfo, String email,String position,
                             int priority, Organization organization) {
        super(null, name);
        this.surname = surname;
        this.patronymic = patronymic;
        this.contactInfo = contactInfo;
        this.email = email;
        this.position=position;
        this.priority = priority;
        this.organization=organization;
    }

    public ResponsiblePerson(String name, String surname, String patronymic, String contactInfo, String email,String position, Organization organization) {
        super(null, name);
        this.surname = surname;
        this.patronymic = patronymic;
        this.contactInfo = contactInfo;
        this.email = email;
        this.position=position;
        this.priority = 0;
        this.organization=organization;
    }

    public ResponsiblePerson(Long id, String name, String surname, String patronymic, String contactInfo, String email, int priority, Organization organization) {
        super(id, name);
        this.surname = surname;
        this.patronymic = patronymic;
        this.contactInfo = contactInfo;
        this.email = email;
        this.priority = priority;
        this.organization=organization;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFullName() {
        return ((getSurname()!=null)?getSurname() + " ":"") +
                getName()+
                ((getPatronymic()!=null)?" "+getPatronymic():"");
    }


    @Override
    protected void fillSelfForUpdate(ResponsiblePerson self) {
        super.fillSelfForUpdate(self);
        self.setName(getName());
        self.setSurname(getSurname());
        self.setPatronymic(getPatronymic());
        self.setContactInfo(getContactInfo());
        self.setEmail(getEmail());
        self.setPosition(getPosition());
        self.setPriority(getPriority());
    }


    @Override
    public String toString() {
        return "ResponsiblePerson{" +
                "surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", priority=" + priority +
                ", organization=" + organization +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
