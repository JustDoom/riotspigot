package de.dytanic.spigot;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.properties.PropertyMap;
import de.dytanic.concurrent.TaskScheduler;
import de.dytanic.document.Document;
import de.dytanic.document.gson.DocumentTypeAdapter;
import net.minecraft.server.OpListEntry;
import net.minecraft.server.WhiteListEntry;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tareko on 19.01.2018.
 */
public class DytanicSpigot {

    public static final Path DATA_FILE = Paths.get("lists.json");

    private static final Type
            OP_LIST_ENTRY = new TypeToken<List<OpListEntry>>(){}.getType(),
            WHITELIST_ENTRY = new TypeToken<List<WhiteListEntry>>(){}.getType()
    ;

    private static final DytanicSpigot instance = new DytanicSpigot();

    private final TaskScheduler taskScheduler = new TaskScheduler(
            Runtime.getRuntime().availableProcessors(),
            null,
            null,
            false
    );

    private final Document document;

    static {
        Document.GSON = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(TypeAdapters.newTypeHierarchyFactory(PropertyMap.class, new TypeAdapter<PropertyMap>() {

                    private final PropertyMap.Serializer serializer = new PropertyMap.Serializer();

                    @Override
                    public void write(JsonWriter jsonWriter, PropertyMap propertyMap) throws IOException
                    {
                        TypeAdapters.JSON_ELEMENT.write(jsonWriter, serializer.serialize(propertyMap, null, null));
                    }

                    @Override
                    public PropertyMap read(JsonReader jsonReader) throws IOException
                    {
                        return serializer.deserialize(TypeAdapters.JSON_ELEMENT.read(jsonReader), null, null);
                    }
                }))
                .registerTypeAdapterFactory(TypeAdapters.newTypeHierarchyFactory(Document.class, new DocumentTypeAdapter()))
                .create();
    }

    public DytanicSpigot()
    {
        if(!Files.exists(DATA_FILE))
        {
            new Document()
                    .append("ops", new ArrayList<OpListEntry>())
                    .append("whitelist", new ArrayList<WhiteListEntry>())
                    .save(DATA_FILE);
        }
        document = Document.newDocument(DATA_FILE);
    }

    public static DytanicSpigot getInstance()
    {
        return instance;
    }

    public TaskScheduler getTaskScheduler()
    {
        return taskScheduler;
    }

    public List<OpListEntry> loadOpList()
    {
        if(document.getArray("ops").size() == 0) return new LinkedList<>();
        return document.getObject("ops", OP_LIST_ENTRY);
    }

    public void setOpList(List<OpListEntry> entries)
    {
        this.document.append("ops", entries);
        save();
    }

    public List<WhiteListEntry> getWhiteList()
    {
        if(document.getArray("ops").size() == 0) return new LinkedList<>();
        return document.getObject("whitelist", WHITELIST_ENTRY);
    }

    public void setWhiteList(List<WhiteListEntry> entries)
    {
        this.document.append("whitelist", entries);
        save();
    }

    public void save()
    {
        document.save(DATA_FILE);
    }

}