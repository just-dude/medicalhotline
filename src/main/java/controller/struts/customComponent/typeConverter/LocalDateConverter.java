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
import java.time.format.DateTimeFormatter;

/**
 *
 * @author SuslovAI
 */
public class LocalDateConverter extends StrutsTypeConverter {
   
   private final static String INPUT_PATTERN = "dd.MM.yyyy";
   
   private final static String OUTPUT_PATTERN= "dd.MM.yyyy"; 
    
   @Override
   public Object convertFromString(Map context, String[] values, Class toClass) {
        DateTimeFormatter fmt;
        LocalDate localDate;        
        try{
            localDate=LocalDate.parse(values[0],DateTimeFormatter.ofPattern(INPUT_PATTERN));
        } 
        catch(Exception e){
            String exceptioDescription="Invalid input: "+values[0]+" is not match to format: "+INPUT_PATTERN;
            throw new TypeConversionException(exceptioDescription,e);
        }
        return localDate;
   }
   
   @Override
   public String convertToString(Map context, Object o) {
        String localDateStr = ((LocalDate)o).format(DateTimeFormatter.ofPattern(OUTPUT_PATTERN));
        return localDateStr;
   }
}