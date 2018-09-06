package domain.common.notifications.validation;

import domain.common.notifications.Notification;
import smartvalidation.entityValidationRule.EntityValidationRulesBuilder;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.EntityValidator;
import smartvalidation.validator.entityValidator.EntityValidatorFactoryImpl;
import smartvalidation.validator.entityValidator.ValidationContext;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * Created by SuslovAI on 04.10.2017.
 */
public class NotificationValidatorFactory {

    public static class OnCreate extends EntityValidatorFactoryImpl<Notification> {

        public OnCreate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        protected void fillValidationRulesBuilder(EntityValidationRulesBuilder rulesBuilder, Notification entity) {
            rulesBuilder.addRule("targetUserAccount", entity.getTargetUserAccount(), AllOf(not(isNull())));
            rulesBuilder.addRule("notificationContent", entity.getNotificationContent(), AllOf(not(isNull())));
        }

    }

    public static class OnUpdate extends EntityValidatorFactoryImpl<Notification> {

        public OnUpdate(ValidationContext validationContext) {
            super(validationContext);
        }

        @Override
        public EntityValidator getValidator(Notification entity) throws EntityValidationException {
            return new NotificationValidatorFactory.OnCreate(validationContext).getValidator(entity);
        }
    }
}
