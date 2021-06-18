package de.dytanic.document.gson;

import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.dytanic.document.Document;

import java.io.IOException;

/**
 * Created by Tareko on 23.12.2017.
 */
public class DocumentTypeAdapter extends TypeAdapter<Document> {

    @Override
    public void write(JsonWriter jsonWriter, Document document) throws IOException
    {
        TypeAdapters.JSON_ELEMENT.write(jsonWriter, document.toJsonObject());
    }

    @Override
    public Document read(JsonReader jsonReader) throws IOException
    {
        JsonElement jsonElement = TypeAdapters.JSON_ELEMENT.read(jsonReader);
        if (jsonElement.isJsonObject())
            return new Document(jsonElement);
        else
            return new Document();
    }
}