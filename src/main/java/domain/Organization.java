package domain;

import common.argumentAssert.Assert;
import dataAccess.common.Node;
import domain.common.HavingNameAndLongIdDomainObject;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Organizations")
public class Organization  extends HavingNameAndLongIdDomainObject<Organization> {

    public static enum Type{
        MedicalOrganization,InsuranceMedicalOrganization, Another
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    @Lob
    @Column
    private String address;

    @Lob
    @Column
    private String contactInfo;

    private String seniorOfficialName;
    private String seniorOfficialSurname;
    private String seniorOfficialPatronymic;

    @OneToMany(targetEntity = ResponsiblePerson.class,mappedBy = "organization")
    @OrderBy(value="priority desc,surname asc")
    private List<ResponsiblePerson> responsiblePeople = new ArrayList<>();


    public Organization() {
    }

    public Organization(Long id) {
        super(id);
    }

    public Organization(Long id, String name, Type type, String address, String contactInfo, String seniorOfficialName, String seniorOfficialSurname, String seniorOfficialPatronymic) {
        super(id, name);
        this.type = type;
        this.address = address;
        this.contactInfo = contactInfo;
        this.seniorOfficialName = seniorOfficialName;
        this.seniorOfficialSurname = seniorOfficialSurname;
        this.seniorOfficialPatronymic = seniorOfficialPatronymic;
    }

    public Organization(String name, Type type, String address, String contactInfo, String seniorOfficialName, String seniorOfficialSurname, String seniorOfficialPatronymic) {
        super(null, name);
        this.type = type;
        this.address = address;
        this.contactInfo = contactInfo;
        this.seniorOfficialName = seniorOfficialName;
        this.seniorOfficialSurname = seniorOfficialSurname;
        this.seniorOfficialPatronymic = seniorOfficialPatronymic;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getSeniorOfficialName() {
        return seniorOfficialName;
    }

    public void setSeniorOfficialName(String seniorOfficialName) {
        this.seniorOfficialName = seniorOfficialName;
    }

    public String getSeniorOfficialSurname() {
        return seniorOfficialSurname;
    }

    public void setSeniorOfficialSurname(String seniorOfficialSurname) {
        this.seniorOfficialSurname = seniorOfficialSurname;
    }

    public String getSeniorOfficialPatronymic() {
        return seniorOfficialPatronymic;
    }

    public void setSeniorOfficialPatronymic(String seniorOfficialPatronymic) {
        this.seniorOfficialPatronymic = seniorOfficialPatronymic;
    }

    public List<ResponsiblePerson> getResponsiblePeople() {
        return responsiblePeople;
    }

    @Transactional
    public ResponsiblePerson addResponsiblePerson(ResponsiblePerson responsiblePerson) {
        Organization self=findNotSoftDeletedSelf();
        responsiblePerson.setOrganization(self);
        return responsiblePerson.save();
    }

    @Override
    protected void fillSelfForUpdate(Organization self) {
        super.fillSelfForUpdate(self);
        self.setName(getName());
        self.setSeniorOfficialSurname(getSeniorOfficialSurname());
        self.setSeniorOfficialName(getSeniorOfficialName());
        self.setSeniorOfficialPatronymic(getSeniorOfficialPatronymic());
        self.setAddress(getAddress());
        self.setContactInfo(getContactInfo());
        self.setType(getType());
    }

    @Override
    protected void beforeHardRemove() {
        super.beforeHardRemove();
        for (ResponsiblePerson responsiblePerson: getResponsiblePeople()){
            responsiblePerson.hardRemove();
        }
    }

    @Override
    protected void beforeSoftRemove(){
        super.beforeSoftRemove();
        for (ResponsiblePerson responsiblePerson: getResponsiblePeople()){
            responsiblePerson.softRemove();
        }
    }

    @Override
    protected void doInitializeProperties(Node<String> initTree) {
        if(initTree.containsData(Organization_.responsiblePeople.getName())){
            getResponsiblePeople().size();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return getType() == that.getType() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getContactInfo(), that.getContactInfo()) &&
                Objects.equals(getSeniorOfficialName(), that.getSeniorOfficialName()) &&
                Objects.equals(getSeniorOfficialSurname(), that.getSeniorOfficialSurname()) &&
                Objects.equals(getSeniorOfficialPatronymic(), that.getSeniorOfficialPatronymic()) &&
                Objects.equals(getResponsiblePeople(), that.getResponsiblePeople());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(),getName(),getType(), getAddress(), getContactInfo(), getSeniorOfficialName(), getSeniorOfficialSurname(), getSeniorOfficialPatronymic(), getResponsiblePeople());
    }

    @Override
    public String toString() {
        return "Organization{" +
                "type=" + type +
                ", address='" + address + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", seniorOfficialName='" + seniorOfficialName + '\'' +
                ", seniorOfficialSurname='" + seniorOfficialSurname + '\'' +
                ", seniorOfficialPatronymic='" + seniorOfficialPatronymic + '\'' +
                '}';
    }
}
