package domain;


import common.beanFactory.BeanFactoryProvider;
import domain.common.Validatable;
import domain.common.exception.BusinessException;
import smartvalidation.constraintViolation.ConstraintViolation;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.EntityValidator;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class Citizen implements Serializable{

    private String name;
    private String surname;
    private String patronymic;
    private String omsPolicyNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "smoId", referencedColumnName = "id")
    private Organization smo;
    private String placeOfLiving;

    public Citizen() {
    }

    public Citizen(String name, String surname, String patronymic, String omsPolicyNumber, Organization smo, String placeOfLiving) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.omsPolicyNumber = omsPolicyNumber;
        this.smo = smo;
        this.placeOfLiving = placeOfLiving;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOmsPolicyNumber() {
        return omsPolicyNumber;
    }

    public void setOmsPolicyNumber(String omsPolicyNumber) {
        this.omsPolicyNumber = omsPolicyNumber;
    }

    public String getPlaceOfLiving() {
        return placeOfLiving;
    }

    public void setPlaceOfLiving(String placeOfLiving) {
        this.placeOfLiving = placeOfLiving;
    }

    public Organization getSmo() {
        return smo;
    }

    public void setSmo(Organization smo) {
        this.smo = smo;
    }

    public boolean isFilled(){
        return (getName()!=null && getSurname()!=null);
    }

    @Override
    public String toString() {
        return "Citizen{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", smo='" + smo + '\'' +
                ", omsPolicyNumber='" + omsPolicyNumber + '\'' +
                ", placeOfLiving='" + placeOfLiving + '\'' +
                '}';
    }

}
