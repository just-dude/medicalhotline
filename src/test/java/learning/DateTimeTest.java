package learning;

import com.opensymphony.xwork2.conversion.TypeConversionException;
import common.argumentAssert.Assert;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by SuslovAI on 15.08.2017.
 */
public class DateTimeTest {

    @Test
    public void testDateTimeFromString(){
        LocalDateTime nowLocalDateTime=LocalDateTime.now(Clock.systemUTC());
        ZonedDateTime nowZonedDateTime = nowLocalDateTime.atZone(Clock.systemUTC().getZone());
        ZonedDateTime asiaChitaZoneDateTime=nowZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Chita"));
        System.out.println("Asia/Chita now: "+asiaChitaZoneDateTime);
        System.out.println("Asia/Chita now: "+asiaChitaZoneDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        asiaChitaZoneDateTime = nowLocalDateTime.atZone(Clock.system(ZoneId.of("Asia/Chita")).getZone());
        System.out.println("Asia/Chita now: "+asiaChitaZoneDateTime);
        System.out.println("Asia/Chita now: "+asiaChitaZoneDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        System.out.println("UTC now: "+nowLocalDateTime);
       ;
    }
}
