/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.common;

import common.beanFactory.BeanFactoryProvider;
import smartvalidation.validator.сonstraintValidator.base.ConstraintValidator;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.regex.Pattern;

import static smartvalidation.validator.сonstraintValidator.ConstraintValidators.*;

/**
 * @author SuslovAI
 */
public class CommonValidators {


    public static ConstraintValidator getSimpleNameConstraintValidator() {
        Pattern commonNamesRexpPattern = Pattern.compile("^[а-яёЁА-ЯA-Za-z\\-\\s]+$", Pattern.UNICODE_CHARACTER_CLASS);
        return AllOf(not(isNull()), not(blankString()), stringLength(lessThan(100)),
                matchesRegularExpression(commonNamesRexpPattern, "matchToAlphabeticCharacters"));
    }

    public static ConstraintValidator getMoreThanMinAndLessThanNowDateConstraintValidator() {
        Clock clock = (Clock) BeanFactoryProvider.getBeanFactory().getBean("clock");
        return AllOf(not(isNull()), greaterThan(LocalDate.of(1000, 1, 1)), lessThan(LocalDate.now(clock)));
    }

    public static ConstraintValidator getMoreThanMinAndLessThanMaxDateTimeConstraintValidator() {
        return AllOf(not(isNull()), greaterThanOrEqualTo(LocalDateTime.of(1000, 1, 1, 0, 0, 0)),
                lessThanOrEqualTo(LocalDateTime.of(9999, 12, 31, 23, 59, 59)));
    }

    public static ConstraintValidator getEmailConstraintValidator() {
        return AllOf(not(isNull()), not(blankString()), stringLength(lessThan(256)),
                matchesRegularExpression("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", "matchToEmailPattern"));
    }

    public static ConstraintValidator<ZonedDateTime> getMoreThanNowZonedDateTimeValidator(){
        Clock clock = (Clock)BeanFactoryProvider.getBeanFactory().getBean("clock");
        return greaterThan(ZonedDateTime.now(clock));
    }

}
