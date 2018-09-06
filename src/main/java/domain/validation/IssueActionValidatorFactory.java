package domain.validation;

import domain.IssueAction;
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
public class IssueActionValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<IssueAction> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(IssueAction entity) throws EntityValidationException {
            return new SimpleEntityValidator(getIssueActionValidationRules(entity));
        }

        private List<EntityValidationRule> getIssueActionValidationRules(IssueAction entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(3);

                entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".text", entity.getText(),
                        AllOf(not(isNull()), not(blankString()),stringLength(lessThan(100000000)))));
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".issue.id", entity.getIssue(), not(isNull())));
                if(entity.getIssue()!=null) {
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".issue.id", entity.getIssue().getId(), not(isNull())));
                }
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<IssueAction> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(IssueAction entity) throws EntityValidationException {
            return new SimpleEntityValidator(getIssueActionValidationRules(entity));
        }

        private List<EntityValidationRule> getIssueActionValidationRules(IssueAction entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(3);

                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".text", entity.getText(),
                        AllOf(not(isNull()), not(blankString()),stringLength(lessThan(100000000)))));
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }
}
