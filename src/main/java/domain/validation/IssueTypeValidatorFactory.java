package domain.validation;

import domain.IssueType;
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
public class IssueTypeValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<IssueType> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(IssueType entity) throws EntityValidationException {
            return new SimpleEntityValidator(getIssueTypeValidationRules(entity));
        }

        private List<EntityValidationRule> getIssueTypeValidationRules(IssueType entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(1);
                entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".name", entity.getName(),
                        AllOf(not(isNull()), not(blankString()),stringLength(lessThan(257)))));
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<IssueType> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(IssueType entity) throws EntityValidationException {
            return new IssueTypeValidatorFactory.OnCreate(validationContext).getValidator(entity);
        }
    }
}
