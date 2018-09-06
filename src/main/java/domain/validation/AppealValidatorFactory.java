package domain.validation;

import domain.Appeal;
import domain.common.CommonValidators;
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
public class AppealValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<Appeal> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(Appeal entity) throws EntityValidationException {
            return new SimpleEntityValidator(getAppealValidationRules(entity));
        }

        private List<EntityValidationRule> getAppealValidationRules(Appeal entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(1);

                Pattern phoneNumberRexpPattern = Pattern.compile("^\\+7\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$", Pattern.UNICODE_CHARACTER_CLASS);
                ConstraintValidator phoneNumberValidator = AllOf(not(isNull()), not(blankString()), matchesRegularExpression(phoneNumberRexpPattern, "matchToPhonePattern"));
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".contactPhoneNumber", entity.getContactPhoneNumber(), phoneNumberValidator));
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<Appeal> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(Appeal entity) throws EntityValidationException {
            return new SimpleEntityValidator(getAppealValidationRules(entity));
        }

        private List<EntityValidationRule> getAppealValidationRules(Appeal entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(8);

                Pattern phoneNumberRexpPattern = Pattern.compile("^\\+7\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$", Pattern.UNICODE_CHARACTER_CLASS);
                ConstraintValidator phoneNumberValidator = AllOf(not(isNull()), not(blankString()), matchesRegularExpression(phoneNumberRexpPattern, "matchToPhonePattern"));
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".contactPhoneNumber", entity.getContactPhoneNumber(), phoneNumberValidator));
                ConstraintValidator commonNamesConstraintValidators = CommonValidators.getSimpleNameConstraintValidator();

                Pattern omsPolicyNumberRexpPattern = Pattern.compile("^[\\d\\-\\s]+$", Pattern.UNICODE_CHARACTER_CLASS);
                ConstraintValidator omsPolicyNumberValidator = AllOf(not(blankString()), matchesRegularExpression(omsPolicyNumberRexpPattern, "containsOnlyDigitsAndWhitespacesAndDashes"),
                        stringLength(lessThan(257)));
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".citizen.name", entity.getCitizen().getName(), commonNamesConstraintValidators));
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".citizen.surname", entity.getCitizen().getSurname(), commonNamesConstraintValidators));
                if (entity.getCitizen().getPatronymic() != null) {
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".citizen.patronymic", entity.getCitizen().getPatronymic(), commonNamesConstraintValidators));
                }
                if (entity.getCitizen().getOmsPolicyNumber() != null) {
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".citizen.smo.id", entity.getCitizen().getSmo(), not(isNull())));
                    if (entity.getCitizen().getSmo() != null) {
                        entityValidationRules.add(new EntityValidationRule(
                                validationContext.getPropertiesPrefix() + ".citizen.smo.id", entity.getCitizen().getSmo().getId(), not(isNull())));
                    }
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".citizen.omsPolicyNumber", entity.getCitizen().getOmsPolicyNumber(), omsPolicyNumberValidator));
                }
                if (entity.getCitizen().getPlaceOfLiving() != null) {
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".placeOfLiving", entity.getCitizen().getPlaceOfLiving(), stringLength(lessThan(257))));
                }
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }
}
