package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class OpListEntry extends JsonListEntry<GameProfile> {

    public int anInt; //Dytanic edit
    public boolean b; //Dytanic edit

    public OpListEntry(GameProfile gameprofile, int i, boolean flag) {
        super(gameprofile);
        this.anInt = i;
        this.b = flag;
    }

    public OpListEntry(JsonObject jsonobject) {
        super(b(jsonobject), jsonobject);
        this.anInt = jsonobject.has("level") ? jsonobject.get("level").getAsInt() : 0;
        this.b = jsonobject.has("bypassesPlayerLimit") && jsonobject.get("bypassesPlayerLimit").getAsBoolean();
    }

    public int a() {
        return this.anInt;
    }

    public boolean b() {
        return this.b;
    }

    protected void a(JsonObject jsonobject) {
        if (this.getKey() != null) {
            jsonobject.addProperty("uuid", ((GameProfile) this.getKey()).getId() == null ? "" : ((GameProfile) this.getKey()).getId().toString());
            jsonobject.addProperty("name", ((GameProfile) this.getKey()).getName());
            super.a(jsonobject);
            jsonobject.addProperty("level", Integer.valueOf(this.anInt));
            jsonobject.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.b));
        }
    }

    private static GameProfile b(JsonObject jsonobject) {
        if (jsonobject.has("uuid") && jsonobject.has("name")) {
            String s = jsonobject.get("uuid").getAsString();

            UUID uuid;

            try {
                uuid = UUID.fromString(s);
            } catch (Throwable throwable) {
                return null;
            }

            return new GameProfile(uuid, jsonobject.get("name").getAsString());
        } else {
            return null;
        }
    }
}
