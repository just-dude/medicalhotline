package domain.common.notifications.validation;

import domain.common.notifications.NotificationContent;
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
public class NotificationContentValidatorFactory {

    public static class OnCreate implements EntityValidatorFactory<NotificationContent> {

        private ValidationContext validationContext;

        public OnCreate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(NotificationContent entity) throws EntityValidationException {
            return new SimpleEntityValidator(getNotificationValidationRules(entity));
        }

        private List<EntityValidationRule> getNotificationValidationRules(NotificationContent entity) throws EntityValidationException {
            try {
                List<EntityValidationRule> entityValidationRules = new ArrayList(2);
                entityValidationRules.add(new EntityValidationRule(
                        validationContext.getPropertiesPrefix() + ".text", entity.getText(),
                        AllOf(not(isNull()))));
                return entityValidationRules;
            } catch (Exception e) {
                throw new EntityValidationException("Entity validator initialization exception have occured", e);
            }
        }
    }

    public static class OnUpdate implements EntityValidatorFactory<NotificationContent> {

        private ValidationContext validationContext;

        public OnUpdate(ValidationContext validationContext) {
            this.validationContext = validationContext;
        }

        @Override
        public EntityValidator getValidator(NotificationContent entity) throws EntityValidationException {
            return new NotificationContentValidatorFactory.OnCreate(validationContext).getValidator(entity);
        }
    }
}
