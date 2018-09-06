/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.struts.customComponent.typeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author SuslovAI
 */
public class LocalDateTimeConverter extends StrutsTypeConverter {
   
   private final static String INPUT_PATTERN = "dd.MM.yyyy HH:mm:ss";
   
   private final static String OUTPUT_PATTERN= "dd.MM.yyyy HH:mm:ss";
    
   @Override
   public Object convertFromString(Map context, String[] values, Class toClass) {
        DateTimeFormatter fmt;
        LocalDateTime localDateTime;
        try{
            localDateTime= LocalDateTime.parse(values[0],DateTimeFormatter.ofPattern(INPUT_PATTERN));
        } 
        catch(Exception e){
            String exceptioDescription="Invalid input: "+values[0]+" is not match to format: "+INPUT_PATTERN;
            throw new TypeConversionException(exceptioDescription,e);
        }
        return localDateTime;
   }
   
   @Override
   public String convertToString(Map context, Object o) {
        String localDateStr = ((LocalDateTime)o).format(DateTimeFormatter.ofPattern(OUTPUT_PATTERN));
        return localDateStr;
   }
}