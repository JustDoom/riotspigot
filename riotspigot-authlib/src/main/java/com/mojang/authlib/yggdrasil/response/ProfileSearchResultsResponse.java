package com.mojang.authlib.yggdrasil.response;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;

import java.lang.reflect.Type;

public class ProfileSearchResultsResponse extends Response
{
    private GameProfile[] profiles;
    
    public GameProfile[] getProfiles() {
        return this.profiles;
    }
    
    public static class Serializer implements JsonDeserializer<ProfileSearchResultsResponse>
    {
        public ProfileSearchResultsResponse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final ProfileSearchResultsResponse result = new ProfileSearchResultsResponse();
            if (json instanceof JsonObject) {
                final JsonObject object = (JsonObject)json;
                if (object.has("error")) {
                    result.setError(object.getAsJsonPrimitive("error").getAsString());
                }
                if (object.has("errorMessage")) {
                    result.setError(object.getAsJsonPrimitive("errorMessage").getAsString());
                }
                if (object.has("cause")) {
                    result.setError(object.getAsJsonPrimitive("cause").getAsString());
                }
            }
            else {
                result.profiles = (GameProfile[])context.deserialize(json, (Type)GameProfile[].class);
            }
            return result;
        }
    }
}
