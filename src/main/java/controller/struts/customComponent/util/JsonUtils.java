package controller.struts.customComponent.util;
 
import java.io.IOException;
 
import com.google.gson.*;
import java.time.LocalDate;

import com.google.gson.annotations.Expose;
import controller.struts.customComponent.serializer.LocalDateJsonSerializer;
import javassist.Modifier;

public final class JsonUtils {
 
    private JsonUtils() {
    }
 
    public static String toJson(Object value) throws IOException {
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                    return expose != null && !expose.serialize();
                }

                @Override
                public boolean shouldSkipClass(Class<?> aClass) {
                    return false;
                }
            })
            .registerTypeAdapter(LocalDate.class, new LocalDateJsonSerializer())
            .disableHtmlEscaping()
            .create();
        return gson.toJson(value);
    }
 
}
