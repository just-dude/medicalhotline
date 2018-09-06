/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.struts.customComponent.typeConverter;

import java.util.*;
import org.apache.struts2.util.StrutsTypeConverter;
import com.opensymphony.xwork2.conversion.TypeConversionException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author SuslovAI
 */
public class TimeZoneConverter extends StrutsTypeConverter {
   
    
   @Override
   public Object convertFromString(Map context, String[] values, Class toClass) {
        try{
            return ZoneId.of(values[0]);
        } 
        catch(Exception e){
            String exceptioDescription="Invalid input: "+values[0];
            throw new TypeConversionException(exceptioDescription,e);
        }
   }
   
   @Override
   public String convertToString(Map context, Object o) {
        return ((ZoneId)o).toString();
   }
}