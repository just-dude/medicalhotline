package domain.validation;

import domain.ResponsiblePerson;
import domain.common.CommonValidators;
import smartvalidation.entityValidationRule.EntityValidationRulesBuilder;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.EntityValidator;
import smartvalidation.validator.entityValidator.EntityValidatorFactoryImpl;
import smartvalidation.validator.entityValidator.ValidationContext;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * Created by SuslovAI on 04.10.2017.
 */
public class ResponsiblePersonValidatorFactory {

    public static class OnCreate extends EntityValidatorFactoryImpl<ResponsiblePerson> {

        public OnCreate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        protected void fillValidationRulesBuilder(EntityValidationRulesBuilder rulesBuilder, ResponsiblePerson entity) {
            rulesBuilder.addRule("name", entity.getName(),
                    AllOf(not(isNull()), not(blankString()),stringLength(lessThan(257)),CommonValidators.getSimpleNameConstraintValidator()));
            rulesBuilder.addRule("surname", entity.getSurname(),
                    AllOf(not(isNull()), not(blankString()),stringLength(lessThan(257)),CommonValidators.getSimpleNameConstraintValidator()));
            if(entity.getPatronymic()!=null) {
                rulesBuilder.addRule("patronymic", entity.getPatronymic(),
                        AllOf(not(isNull()), not(blankString()), stringLength(lessThan(257)), CommonValidators.getSimpleNameConstraintValidator()));
            }
            rulesBuilder.addRule("contactInfo", entity.getContactInfo(),
                    AllOf(not(isNull()), not(blankString()), stringLength(lessThan(257))));
            if(entity.getPosition()!=null) {
                rulesBuilder.addRule("position", entity.getPosition(),
                        AllOf(not(isNull()), not(blankString()), stringLength(lessThan(257))));
            }
            if(entity.getEmail()!=null) {
                rulesBuilder.addRule("email", entity.getEmail(),
                        AllOf(not(isNull()), not(blankString()), stringLength(lessThan(257)), CommonValidators.getEmailConstraintValidator()));
            }
        }

    }

    public static class OnUpdate extends EntityValidatorFactoryImpl<ResponsiblePerson> {

        public OnUpdate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        public EntityValidator getValidator(ResponsiblePerson entity) throws EntityValidationException {
            return new ResponsiblePersonValidatorFactory.OnCreate(validationContext).getValidator(entity);
        }
    }
}
