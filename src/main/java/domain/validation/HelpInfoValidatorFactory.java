package domain.validation;

import domain.HelpInfo;
import smartvalidation.entityValidationRule.EntityValidationRule;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.EntityValidator;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.SimpleEntityValidator;
import smartvalidation.validator.entityValidator.ValidationContext;

import java.util.ArrayList;
import java.util.List;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * Created by SuslovAI on 04.10.2017.
 */
public class HelpInfoValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<HelpInfo> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(HelpInfo entity) throws EntityValidationException {
            return new SimpleEntityValidator(getHelpInfoValidationRules(entity));
        }

        private List<EntityValidationRule> getHelpInfoValidationRules(HelpInfo entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(2);

                entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".text", entity.getText(),
                        AllOf(not(isNull()), not(blankString()),stringLength(lessThan(100000000)))));
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".id", entity.getId(),
                        AllOf(not(isNull()))));
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<HelpInfo> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(HelpInfo entity) throws EntityValidationException {
            return new HelpInfoValidatorFactory.OnCreate(validationContext).getValidator(entity);
        }
    }
}
