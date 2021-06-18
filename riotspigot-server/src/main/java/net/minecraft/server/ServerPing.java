package net.minecraft.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import de.dytanic.$;

import java.lang.reflect.Type;
import java.util.UUID;

public class ServerPing {

    private IChatBaseComponent a;
    private ServerPingPlayerSample b;
    private ServerData c;
    private String d;

    public ServerPing() {}

    public IChatBaseComponent a() {
        return this.a;
    }

    public void setMOTD(IChatBaseComponent ichatbasecomponent) {
        this.a = ichatbasecomponent;
    }

    public ServerPingPlayerSample b() {
        return this.b;
    }

    public void setPlayerSample(ServerPingPlayerSample serverping_serverpingplayersample) {
        this.b = serverping_serverpingplayersample;
    }

    public ServerData c() {
        return this.c;
    }

    public void setServerInfo(ServerData serverping_serverdata) {
        this.c = serverping_serverdata;
    }

    public void setFavicon(String s) {
        this.d = s;
    }

    public String d() {
        return this.d;
    }

    public static class Serializer implements JsonDeserializer<ServerPing>, JsonSerializer<ServerPing> {

        public Serializer() {}

        public ServerPing a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            JsonObject jsonobject = ChatDeserializer.l(jsonelement, "status");
            ServerPing serverping = new ServerPing();

            if (jsonobject.has("description")) {
                serverping.setMOTD((IChatBaseComponent) jsondeserializationcontext.deserialize(jsonobject.get("description"), IChatBaseComponent.class));
            }

            if (jsonobject.has("players")) {
                serverping.setPlayerSample((ServerPingPlayerSample) jsondeserializationcontext.deserialize(jsonobject.get("players"), ServerPingPlayerSample.class));
            }

            if (jsonobject.has("version")) {
                serverping.setServerInfo((ServerData) jsondeserializationcontext.deserialize(jsonobject.get("version"), ServerData.class));
            }

            if (jsonobject.has("favicon")) {
                serverping.setFavicon(ChatDeserializer.h(jsonobject, "favicon"));
            }

            return serverping;
        }

        public JsonElement a(ServerPing serverping, Type type, JsonSerializationContext jsonserializationcontext) {
            JsonObject jsonobject = new JsonObject();

            if (serverping.a() != null) {
                jsonobject.add("description", jsonserializationcontext.serialize(serverping.a()));
            }

            if (serverping.b() != null) {
                jsonobject.add("players", jsonserializationcontext.serialize(serverping.b()));
            }

            if (serverping.c() != null) {
                jsonobject.add("version", jsonserializationcontext.serialize(serverping.c()));
            }

            if (serverping.d() != null) {
                jsonobject.addProperty("favicon", serverping.d());
            }

            return jsonobject;
        }

        public JsonElement serialize(ServerPing object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((ServerPing) object, type, jsonserializationcontext);
        }

        public ServerPing deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }

    public static class ServerData {

        private final String a;
        private final int b;

        public ServerData(String s, int i) {
            this.a = s;
            this.b = i;
        }

        public String a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        public static class ServerData$Serializer implements JsonDeserializer<ServerData>, JsonSerializer<ServerData> {

            public ServerData$Serializer() {}

            public ServerData a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
                JsonObject jsonobject = ChatDeserializer.l(jsonelement, "version");

                return new ServerData(ChatDeserializer.h(jsonobject, "name"), ChatDeserializer.m(jsonobject, "protocol"));
            }

            public JsonElement a(ServerData serverping_serverdata, Type type, JsonSerializationContext jsonserializationcontext) {
                JsonObject jsonobject = new JsonObject();

                jsonobject.addProperty("name", serverping_serverdata.a());
                jsonobject.addProperty("protocol", Integer.valueOf(serverping_serverdata.b()));
                return jsonobject;
            }

            public JsonElement serialize(ServerData object, Type type, JsonSerializationContext jsonserializationcontext) {
                return this.a((ServerData) object, type, jsonserializationcontext);
            }

            public ServerData deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
                return this.a(jsonelement, type, jsondeserializationcontext);
            }
        }
    }

    public static class ServerPingPlayerSample {

        private final int a;
        private final int b;
        private GameProfile[] c;

        public ServerPingPlayerSample(int i, int j) {
            this.a = i;
            this.b = j;
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        public GameProfile[] c() {
            return this.c;
        }

        public void a(GameProfile[] agameprofile) {
            this.c = agameprofile;
        }

        public static class ServerPingPlayerSample$Serializer implements JsonDeserializer<ServerPingPlayerSample>, JsonSerializer<ServerPingPlayerSample> {

            public ServerPingPlayerSample$Serializer() {}

            public ServerPingPlayerSample a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
                JsonObject jsonobject = ChatDeserializer.l(jsonelement, "players");
                ServerPingPlayerSample serverping_serverpingplayersample = new ServerPingPlayerSample(ChatDeserializer.m(jsonobject, "max"), ChatDeserializer.m(jsonobject, "online"));

                if (ChatDeserializer.d(jsonobject, "sample")) {
                    JsonArray jsonarray = ChatDeserializer.t(jsonobject, "sample");

                    if (jsonarray.size() > 0) {
                        GameProfile[] agameprofile = new GameProfile[jsonarray.size()];

                        for (int i = 0; i < agameprofile.length; ++i) {
                            JsonObject jsonobject1 = ChatDeserializer.l(jsonarray.get(i), "player[" + i + "]");
                            String s = ChatDeserializer.h(jsonobject1, "id");

                            agameprofile[i] = new GameProfile(UUID.fromString(s), ChatDeserializer.h(jsonobject1, "name"));
                        }

                        serverping_serverpingplayersample.a(agameprofile);
                    }
                }

                return serverping_serverpingplayersample;
            }

            public JsonElement a(ServerPingPlayerSample serverping_serverpingplayersample, Type type, JsonSerializationContext jsonserializationcontext) {
                JsonObject jsonobject = new JsonObject();

                jsonobject.addProperty("max", Integer.valueOf(serverping_serverpingplayersample.a()));
                jsonobject.addProperty("online", Integer.valueOf(serverping_serverpingplayersample.b()));
                if (serverping_serverpingplayersample.c() != null && serverping_serverpingplayersample.c().length > 0) {
                    JsonArray jsonarray = new JsonArray();

                    for (int i = 0; i < serverping_serverpingplayersample.c().length; ++i) {
                        JsonObject jsonobject1 = new JsonObject();
                        UUID uuid = serverping_serverpingplayersample.c()[i].getId();

                        jsonobject1.addProperty("id", uuid == null ? $.EMPTY_STRING : uuid.toString());
                        jsonobject1.addProperty("name", serverping_serverpingplayersample.c()[i].getName());
                        jsonarray.add(jsonobject1);
                    }

                    jsonobject.add("sample", jsonarray);
                }

                return jsonobject;
            }

            public JsonElement serialize(ServerPingPlayerSample object, Type type, JsonSerializationContext jsonserializationcontext) {
                return this.a((ServerPingPlayerSample) object, type, jsonserializationcontext);
            }

            public ServerPingPlayerSample deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
                return this.a(jsonelement, type, jsondeserializationcontext);
            }
        }
    }
}
