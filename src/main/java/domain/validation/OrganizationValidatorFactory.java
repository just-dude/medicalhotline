package domain.validation;

import domain.Organization;
import domain.common.CommonValidators;
import smartvalidation.entityValidationRule.EntityValidationRule;
import smartvalidation.entityValidationRule.EntityValidationRulesBuilder;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.*;

import java.util.ArrayList;
import java.util.List;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * Created by SuslovAI on 04.10.2017.
 */
public class OrganizationValidatorFactory {

    public static class OnCreate extends EntityValidatorFactoryImpl<Organization> {

        public OnCreate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        protected void fillValidationRulesBuilder(EntityValidationRulesBuilder rulesBuilder, Organization entity) {
            rulesBuilder.addRule("name", entity.getName(),
                    AllOf(not(isNull()), not(blankString()),stringLength(lessThan(257))));
            rulesBuilder.addRule("type", entity.getType(), AllOf(not(isNull())));
            rulesBuilder.addRule("address", entity.getAddress(),
                    AllOf(not(isNull()), not(blankString()),stringLength(lessThan(100000000))));
            rulesBuilder.addRule("contactInfo", entity.getContactInfo(),
                    AllOf(not(isNull()), not(blankString()),stringLength(lessThan(100000000))));
            rulesBuilder.addRule("seniorOfficialName", entity.getSeniorOfficialName(),
                    AllOf(not(isNull()), not(blankString()),stringLength(lessThan(257)),CommonValidators.getSimpleNameConstraintValidator()));
            rulesBuilder.addRule("seniorOfficialSurname", entity.getSeniorOfficialSurname(),
                    AllOf(not(isNull()), not(blankString()),stringLength(lessThan(257)),CommonValidators.getSimpleNameConstraintValidator()));
            if(entity.getSeniorOfficialPatronymic()!=null) {
                rulesBuilder.addRule("seniorOfficialPatronymic", entity.getSeniorOfficialPatronymic(),
                        AllOf(not(isNull()), not(blankString()), stringLength(lessThan(257)), CommonValidators.getSimpleNameConstraintValidator()));
            }
        }

    }

    public static class OnUpdate extends EntityValidatorFactoryImpl<Organization> {

        public OnUpdate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        public EntityValidator getValidator(Organization entity) throws EntityValidationException {
            return new OrganizationValidatorFactory.OnCreate(validationContext).getValidator(entity);
        }
    }
}
