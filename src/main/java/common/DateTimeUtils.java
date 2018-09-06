package common;

import common.beanFactory.BeanFactoryProvider;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Created by Артем on 01.04.2018.
 */
public class DateTimeUtils {

    public static String toString(LocalDateTime localDateTimeInUTC){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        Clock clock = (Clock) BeanFactoryProvider.getBeanFactory().getBean("userPreferencesClock");
        ZonedDateTime zonedDateTime = localDateTimeInUTC.atZone(Clock.systemUTC().getZone());
        zonedDateTime=zonedDateTime.withZoneSameInstant(clock.getZone());
        return zonedDateTime.format(formatter);
    }

    public static LocalDateTime fromUserPrefZoneToSystemZone(LocalDateTime localDateTimeInUserPrefZone){
        Clock clock = (Clock) BeanFactoryProvider.getBeanFactory().getBean("userPreferencesClock");
        ZonedDateTime zonedDateTime = localDateTimeInUserPrefZone.atZone(clock.getZone());
        zonedDateTime=zonedDateTime.withZoneSameInstant(Clock.systemUTC().getZone());
        return zonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime startOfTodayInUserPrefZone(){
        Clock clock = (Clock) BeanFactoryProvider.getBeanFactory().getBean("userPreferencesClock");
        LocalDateTime now = LocalDateTime.now(clock);
        return LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),0,0,0,0);
    }

    public static LocalDateTime endOfTodayInUserPrefZone(){
        Clock clock = (Clock) BeanFactoryProvider.getBeanFactory().getBean("userPreferencesClock");
        LocalDateTime now = LocalDateTime.now(clock);
        return LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),23,59,59,0);
    }

    public static LocalDateTime now(){
        Clock clock =(Clock)BeanFactoryProvider.getBeanFactory().getBean("clock");
        return LocalDateTime.now(clock);
    }
}
