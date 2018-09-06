package domain.common.stateChangesAccounting.validation;

import domain.common.stateChangesAccounting.EntityFieldPrevState;
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
public class EntityFieldPrevStateValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<EntityFieldPrevState> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(EntityFieldPrevState entity) throws EntityValidationException {
            return new SimpleEntityValidator(getEntityFieldPrevStateValidationRules(entity));
        }

        private List<EntityValidationRule> getEntityFieldPrevStateValidationRules(EntityFieldPrevState entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(0);

                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".id", entity.getId(),
                        AllOf(not(isNull()))));
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<EntityFieldPrevState> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(EntityFieldPrevState entity) throws EntityValidationException {
            return new EntityFieldPrevStateValidatorFactory.OnCreate(validationContext).getValidator(entity);
        }
    }
}
