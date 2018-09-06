package controller.struts.action.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import controller.struts.action.common.ResultFactory;

/**
 * Created by SuslovAI on 02.12.2016.
 */
public class ActionUtils {
    
    public static void logException(Logger logger,Exception e){
        logException(logger,e,null,null);
    }
    
    public static void logException(Logger logger,Exception e,Object... parameters){
        logException(logger,e,null,parameters);
    }
    
    public static void logException(Logger logger,Exception e,String message,Object... parameters){
        StringBuilder loggedMessage = new StringBuilder();
        if(message!=null && message.equals("")){
            loggedMessage.append(message+". ");
        }
        if(parameters!=null &&  parameters.length!=0){
            loggedMessage.append("Parameters: "+Arrays.toString(parameters));
        }        
        logger.error(loggedMessage,e);
    }
    
    public static String getDecoratedByViewSettingsResult(String result){
        HttpServletRequest request = ServletActionContext.getRequest();
        return ResultFactory.getDecoratedByViewSettingsResultByRequest(result, request);
    }
    
    public static Map<String,String> getAllZoneNamesAndZoneNamesWithOffsetsMap(){
        Set<String> allZones = ZoneId.getAvailableZoneIds();
        Map<String,String> allZoneNamesAndZoneNamesWithOffsetsMap = new LinkedHashMap(allZones.size());
        LocalDateTime dt = LocalDateTime.now();

        List<String> zoneList = new ArrayList<String>(allZones);
        Collections.sort(zoneList);
        String zoneNameWithOffset;
        for (String s : zoneList) {
            ZoneId zone = ZoneId.of(s);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset offset = zdt.getOffset();
            if(offset.getTotalSeconds()!=0){
                zoneNameWithOffset=String.format("%35s %10s", zone, offset);
            } else{
                zoneNameWithOffset=String.format("%35s", zone);
            }
            allZoneNamesAndZoneNamesWithOffsetsMap.put(zone.getId(),zoneNameWithOffset);
        }
        return allZoneNamesAndZoneNamesWithOffsetsMap;
    }
}
