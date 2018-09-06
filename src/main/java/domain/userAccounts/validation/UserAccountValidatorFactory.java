package domain.userAccounts.validation;

import domain.common.CommonValidators;
import domain.userAccounts.UserAccount;
import smartvalidation.entityValidationRule.EntityValidationRule;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.EntityValidator;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.SimpleEntityValidator;
import smartvalidation.validator.entityValidator.ValidationContext;
import smartvalidation.validator.сonstraintValidator.base.ConstraintValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * Created by SuslovAI on 04.10.2017.
 */
public class UserAccountValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<UserAccount> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(UserAccount entity) throws EntityValidationException {
            return new SimpleEntityValidator(getUserAccountChangedDataValidationRules(entity));
        }

        private List<EntityValidationRule> getUserAccountChangedDataValidationRules(UserAccount entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(12);

                Pattern loginRexpPattern = Pattern.compile("^[A-Za-z0-9\\-_]+$", Pattern.UNICODE_CHARACTER_CLASS);
                ConstraintValidator loginValidator = AllOf(not(isNull()), not(blankString()), stringLength(lessThan(100)),
                        matchesRegularExpression(loginRexpPattern, "matchToLoginPattern"));
                ConstraintValidator passwordValidator = AllOf(not(isNull()), not(blankString()), stringLength(greaterThan(4)), stringLength(lessThan(256)));
                Pattern commonNamesRexpPattern = Pattern.compile("^[а-яёЁА-ЯA-Za-z\\-\\s]+$", Pattern.UNICODE_CHARACTER_CLASS);
                ConstraintValidator commonNamesConstraintValidators = CommonValidators.getSimpleNameConstraintValidator();

                entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".accountInfo.login", entity.getAccountInfo().getLogin(), loginValidator));
                entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".accountInfo.hashedPassword", entity.getAccountInfo().getHashedPassword(), passwordValidator));
                entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.name", entity.getProfile().getName(), commonNamesConstraintValidators));
                if(entity.getProfile().getSurname()!=null) {
                    entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.surname", entity.getProfile().getSurname(), commonNamesConstraintValidators));
                }
                if(entity.getProfile().getPatronymic()!=null) {
                    entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.patronymic", entity.getProfile().getPatronymic(), commonNamesConstraintValidators));
                }
                if(entity.getProfile().getEmail()!=null) {
                    entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.email", entity.getProfile().getEmail(), CommonValidators.getEmailConstraintValidator()));
                }
                entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".group", entity.getUserGroup(), not(isNull())));

                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<UserAccount> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(UserAccount entity) throws EntityValidationException {
            return new SimpleEntityValidator(getUserAccountChangedDataValidationRules(entity));
        }

        private List<EntityValidationRule> getUserAccountChangedDataValidationRules(UserAccount entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(12);

                Pattern loginRexpPattern = Pattern.compile("^[A-Za-z0-9\\-_]+$", Pattern.UNICODE_CHARACTER_CLASS);
                ConstraintValidator loginValidator = AllOf(not(isNull()), not(blankString()), stringLength(lessThan(100)),
                        matchesRegularExpression(loginRexpPattern, "matchToLoginPattern"));
                ConstraintValidator passwordValidator = AllOf(not(isNull()), not(blankString()), stringLength(greaterThan(9)), stringLength(lessThan(256)));
                Pattern commonNamesRexpPattern = Pattern.compile("^[а-яёЁА-ЯA-Za-z\\-\\s]+$", Pattern.UNICODE_CHARACTER_CLASS);
                ConstraintValidator commonNamesConstraintValidators = CommonValidators.getSimpleNameConstraintValidator();

                entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".id", entity.getId(), not(isNull())));
                entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.name", entity.getProfile().getName(), commonNamesConstraintValidators));
                if(entity.getProfile().getSurname()!=null) {
                    entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.surname", entity.getProfile().getSurname(), commonNamesConstraintValidators));
                }
                if(entity.getProfile().getPatronymic()!=null) {
                    entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.patronymic", entity.getProfile().getPatronymic(), commonNamesConstraintValidators));
                }
                if(entity.getProfile().getEmail()!=null) {
                    entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".profile.email", entity.getProfile().getEmail(), CommonValidators.getEmailConstraintValidator()));
                }
                entityValidationRules.add(new EntityValidationRule(validationContext.getPropertiesPrefix() + ".group", entity.getUserGroup(), not(isNull())));

                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }
}
