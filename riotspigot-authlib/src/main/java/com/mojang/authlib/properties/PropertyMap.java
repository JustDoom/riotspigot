package com.mojang.authlib.properties;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class PropertyMap extends ForwardingMultimap<String, Property>
{
    private final Multimap<String, Property> properties;
    
    public PropertyMap() {
        super();
        this.properties = LinkedHashMultimap.create();
    }
    
    protected Multimap<String, Property> delegate() {
        return this.properties;
    }
    
    public static class Serializer implements JsonSerializer<PropertyMap>, JsonDeserializer<PropertyMap>
    {
        public PropertyMap deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final PropertyMap result = new PropertyMap();
            if (json instanceof JsonObject) {
                final JsonObject object = (JsonObject)json;
                for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    if (entry.getValue() instanceof JsonArray) {
                        for (final JsonElement element : (JsonArray)entry.getValue()) {
                            result.put(entry.getKey(), new Property(entry.getKey(), element.getAsString()));
                        }
                    }
                }
            }
            else if (json instanceof JsonArray) {
                for (final JsonElement element2 : (JsonArray)json) {
                    if (element2 instanceof JsonObject) {
                        final JsonObject object2 = (JsonObject)element2;
                        final String name = object2.getAsJsonPrimitive("name").getAsString();
                        final String value = object2.getAsJsonPrimitive("value").getAsString();
                        if (object2.has("signature")) {
                            result.put(name, new Property(name, value, object2.getAsJsonPrimitive("signature").getAsString()));
                        }
                        else {
                            result.put(name, new Property(name, value));
                        }
                    }
                }
            }
            return result;
        }
        
        public JsonElement serialize(final PropertyMap src, final Type typeOfSrc, final JsonSerializationContext context) {
            final JsonArray result = new JsonArray();
            for (final Property property : src.values()) {
                final JsonObject object = new JsonObject();
                object.addProperty("name", property.getName());
                object.addProperty("value", property.getValue());
                if (property.hasSignature()) {
                    object.addProperty("signature", property.getSignature());
                }
                result.add((JsonElement)object);
            }
            return (JsonElement)result;
        }
    }
}
