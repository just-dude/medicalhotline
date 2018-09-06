/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package controller.struts.customComponent.serializer;

import com.google.gson.*;
import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author SuslovAI
 */
public class LocalDateJsonSerializer implements JsonSerializer<LocalDate>{
    
   @Override
    public JsonElement serialize(LocalDate localDate, Type typeOfId, JsonSerializationContext context) {
        return new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
    
}
