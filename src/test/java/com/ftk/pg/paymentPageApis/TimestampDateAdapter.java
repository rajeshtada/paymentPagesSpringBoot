package com.ftk.pg.paymentPageApis;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Date;

public class TimestampDateAdapter implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
        throws JsonParseException {
        try {
            // Handle both string and number formats
            if (json.isJsonPrimitive()) {
                JsonPrimitive primitive = json.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    return new Date(primitive.getAsLong());
                } else if (primitive.isString()) {
                    return new Date(Long.parseLong(primitive.getAsString()));
                }
            }
            throw new JsonParseException("Unsupported date format: " + json);
        } catch (NumberFormatException e) {
            throw new JsonParseException("Invalid date format: " + json, e);
        }
    }
}