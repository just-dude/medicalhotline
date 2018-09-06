package domain.validation;

import domain.Issue;
import smartvalidation.entityValidationRule.EntityValidationRule;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.EntityValidator;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.SimpleEntityValidator;
import smartvalidation.validator.entityValidator.ValidationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * Created by SuslovAI on 04.10.2017.
 */
public class IssueValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<Issue> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(Issue entity) throws EntityValidationException {
            return new SimpleEntityValidator(getIssueValidationRules(entity));
        }

        private List<EntityValidationRule> getIssueValidationRules(Issue entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(3);

                entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".text", entity.getText(),
                        AllOf(not(isNull()), not(blankString()),stringLength(lessThan(100000000)))));
                if(entity.getIssueCategory()!=null) {
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".issueCategory.id", entity.getIssueCategory().getId(), not(isNull())));
                } else{
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".issueCategory.id", null, not(isNull())));
                }
                if(entity.getAppeal()!=null) {
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".appeal.id", entity.getAppeal().getId(), not(isNull())));
                } else{
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".appeal.id", null, not(isNull())));
                }
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".issueTypes", entity.getIssueTypes(), AllOf(not(isNull()), collectionSize(greaterThan(0)))));

                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<Issue> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(Issue entity) throws EntityValidationException {
            return new SimpleEntityValidator(getIssueValidationRules(entity));
        }

        private List<EntityValidationRule> getIssueValidationRules(Issue entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(3);

                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".text", entity.getText(),
                        AllOf(not(isNull()), not(blankString()),stringLength(lessThan(100000000)))));
                if(entity.getIssueCategory()!=null) {
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".issueCategory.id", entity.getIssueCategory().getId(), not(isNull())));
                } else{
                    entityValidationRules.add(new EntityValidationRule(
                            validationContext.getPropertiesPrefix() + ".issueCategory.id", null, not(isNull())));
                }
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".issueTypes", entity.getIssueTypes(), AllOf(not(isNull()), collectionSize(greaterThan(0)))));

                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }
}
