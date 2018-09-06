package domain.validation;

import domain.ResponsiblePersonUserAccount;
import domain.common.CommonValidators;
import domain.userAccounts.UserAccount;
import smartvalidation.entityValidationRule.EntityValidationRule;
import smartvalidation.entityValidationRule.EntityValidationRulesBuilder;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.*;
import smartvalidation.validator.сonstraintValidator.base.ConstraintValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * Created by SuslovAI on 04.10.2017.
 */
public class ResponsiblePersonUserAccountValidatorFactory {

    public static class OnCreate extends EntityValidatorFactoryImpl<ResponsiblePersonUserAccount> {

        public OnCreate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        protected void fillValidationRulesBuilder(EntityValidationRulesBuilder rulesBuilder, ResponsiblePersonUserAccount entity) {
            Pattern loginRexpPattern = Pattern.compile("^[A-Za-z0-9\\-_]+$", Pattern.UNICODE_CHARACTER_CLASS);
            ConstraintValidator loginValidator = AllOf(not(isNull()), not(blankString()), stringLength(lessThan(100)),
                    matchesRegularExpression(loginRexpPattern, "matchToLoginPattern"));
            ConstraintValidator passwordValidator = AllOf(not(isNull()), not(blankString()), stringLength(greaterThan(4)), stringLength(lessThan(256)));
            Pattern commonNamesRexpPattern = Pattern.compile("^[а-яёЁА-ЯA-Za-z\\-\\s]+$", Pattern.UNICODE_CHARACTER_CLASS);
            ConstraintValidator commonNamesConstraintValidators = CommonValidators.getSimpleNameConstraintValidator();

            rulesBuilder.addRule("accountInfo.login", entity.getAccountInfo().getLogin(), loginValidator);
            rulesBuilder.addRule("accountInfo.hashedPassword", entity.getAccountInfo().getHashedPassword(), passwordValidator);
            rulesBuilder.addRule("profile.name", entity.getProfile().getName(), commonNamesConstraintValidators);
            if(entity.getProfile().getSurname()!=null) {
                rulesBuilder.addRule("profile.surname", entity.getProfile().getSurname(), commonNamesConstraintValidators);
            }
            if(entity.getProfile().getPatronymic()!=null) {
                rulesBuilder.addRule("profile.patronymic", entity.getProfile().getPatronymic(), commonNamesConstraintValidators);
            }
            if(entity.getProfile().getEmail()!=null) {
                rulesBuilder.addRule("profile.email", entity.getProfile().getEmail(), CommonValidators.getEmailConstraintValidator());
            }
            rulesBuilder.addRule("group", entity.getUserGroup(), not(isNull()));

            rulesBuilder.addRule("organization.id", entity.getOrganization().getId(), not(isNull()));
            rulesBuilder.addRule("responsiblePerson.id", entity.getResponsiblePerson().getId(), not(isNull()));
        }
    }

    public static class OnUpdate extends EntityValidatorFactoryImpl<ResponsiblePersonUserAccount> {

        public OnUpdate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        protected void fillValidationRulesBuilder(EntityValidationRulesBuilder rulesBuilder, ResponsiblePersonUserAccount entity) {
            Pattern loginRexpPattern = Pattern.compile("^[A-Za-z0-9\\-_]+$", Pattern.UNICODE_CHARACTER_CLASS);
            ConstraintValidator loginValidator = AllOf(not(isNull()), not(blankString()), stringLength(lessThan(100)),
                    matchesRegularExpression(loginRexpPattern, "matchToLoginPattern"));
            ConstraintValidator passwordValidator = AllOf(not(isNull()), not(blankString()), stringLength(greaterThan(9)), stringLength(lessThan(256)));
            Pattern commonNamesRexpPattern = Pattern.compile("^[а-яёЁА-ЯA-Za-z\\-\\s]+$", Pattern.UNICODE_CHARACTER_CLASS);
            ConstraintValidator commonNamesConstraintValidators = CommonValidators.getSimpleNameConstraintValidator();

            rulesBuilder.addRule("profile.name", entity.getProfile().getName(), commonNamesConstraintValidators);
            if(entity.getProfile().getSurname()!=null) {
                rulesBuilder.addRule("profile.surname", entity.getProfile().getSurname(), commonNamesConstraintValidators);
            }
            if(entity.getProfile().getPatronymic()!=null) {
                rulesBuilder.addRule("profile.patronymic", entity.getProfile().getPatronymic(), commonNamesConstraintValidators);
            }
            if(entity.getProfile().getEmail()!=null) {
                rulesBuilder.addRule("profile.email", entity.getProfile().getEmail(), CommonValidators.getEmailConstraintValidator());
            }
            rulesBuilder.addRule("group", entity.getUserGroup(), not(isNull()));

            rulesBuilder.addRule("organization.id", entity.getOrganization().getId(), not(isNull()));
            rulesBuilder.addRule("responsiblePerson.id", entity.getResponsiblePerson().getId(), not(isNull()));
        }
    }
}
