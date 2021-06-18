package de.dytanic.document;

import com.google.gson.*;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import de.dytanic.document.gson.DocumentTypeAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Tareko on 21.05.2017.
 */
public class Document implements IDocPropertyable, Serializable {

    public static Gson GSON = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(TypeAdapters.newTypeHierarchyFactory(Document.class, new DocumentTypeAdapter()))
            .create();

    protected static final JsonParser PARSER = new JsonParser();

    protected JsonObject dataCatcher;

    public Document()
    {
        this(new JsonObject());
    }

    public Document(JsonElement source)
    {
        if (source.isJsonObject())
            this.dataCatcher = source.getAsJsonObject();
        else throw new ClassCastException("JsonElement isn't JsonObject");
    }

    public Document(Document defaults)
    {
        this(defaults.dataCatcher);
    }

    public Document(String key, String value)
    {
        this(new JsonObject());
        this.append(key, value);
    }

    public Document(String key, Object value)
    {
        this(new JsonObject());
        this.append(key, value);
    }

    public Document(String key, Number value)
    {
        this(new JsonObject());
        this.append(key, value);
    }

    public Document(String key, Boolean value)
    {
        this(new JsonObject());
        this.append(key, value);
    }

    public Document(Map<String, Object> defaults)
    {
        this(new JsonObject());
        this.append(defaults);
    }

    /*= ------------------------------------------------------------------- =*/

    public Document append(String key, String value)
    {
        if (value == null) return this;
        this.dataCatcher.addProperty(key, value);
        return this;
    }

    public Document append(String key, Number value)
    {
        if (value == null) return this;
        this.dataCatcher.addProperty(key, value);
        return this;
    }

    public Document append(String key, Boolean value)
    {
        if (value == null) return this;
        this.dataCatcher.addProperty(key, value);
        return this;
    }

    public Document append(String key, JsonElement value)
    {
        if (value == null) return this;
        this.dataCatcher.add(key, value);
        return this;
    }

    public Document append(String key, List<String> value)
    {
        if (value == null) return this;
        JsonArray jsonElements = new JsonArray();

        for (short i = 0; i < value.size(); i++)
            jsonElements.add(new JsonPrimitive(value.get(i)));

        this.dataCatcher.add(key, jsonElements);
        return this;
    }

    public Document append(String key, Document value)
    {
        if (value == null) return this;
        this.dataCatcher.add(key, value.dataCatcher);
        return this;
    }

    public Document append(String key, Object value)
    {
        if (value == null) return this;
        this.dataCatcher.add(key, GSON.toJsonTree(value));
        return this;
    }

    public Document append(Document defaults)
    {
        return append(defaults.dataCatcher);
    }

    public Document append(JsonObject jsonObject)
    {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
            append(entry.getKey(), entry.getValue());
        return this;
    }

    public Document append(Map<String, Object> values)
    {
        for (Map.Entry<String, Object> valuess : values.entrySet())
            append(valuess.getKey(), valuess.getValue());
        return this;
    }

    public Document append(Properties properties)
    {
        Object entry;
        Enumeration enumeration = properties.keys();

        while (enumeration.hasMoreElements() && (entry = enumeration.nextElement()) != null)
            append(entry.toString(), properties.getProperty(entry.toString()));

        return this;
    }

    public Document append(File backend)
    {
        return append(backend.toPath());
    }

    public Document append(Path path)
    {
        try (InputStream inputStream = Files.newInputStream(path))
        {
            return append(inputStream);
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public Document append(InputStream inputStream)
    {
        try (InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8"))
        {
            return append(reader);
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public Document append(Reader reader)
    {
        return append(PARSER.parse(reader).getAsJsonObject());
    }

        /*= ------------------------------------------------------------------- =*/

    @Override
    public <E> Document setProperty(DocProperty<E> docProperty, E val)
    {
        docProperty.appender.accept(val, this);
        return this;
    }

    @Override
    public <E> E getProperty(DocProperty<E> docProperty)
    {
        return docProperty.resolver.apply(this);
    }

    @Override
    public <E> Document removeProperty(DocProperty<E> docProperty)
    {
        docProperty.remover.accept(this);
        return this;
    }

    /*= ------------------------------------------------------------------- =*/

    public JsonElement get(String key)
    {
        if (!contains(key)) return null;
        return dataCatcher.get(key);
    }

    public String getString(String key)
    {
        if (!contains(key)) return null;
        return dataCatcher.get(key).getAsString();
    }

    public String[] getStrings(String key)
    {
        if (!contains(key)) return null;
        return getObject(key, new TypeToken<String[]>() {
        }.getType());
    }

    public int getInt(String key)
    {
        if (!contains(key)) return 0;
        return dataCatcher.get(key).getAsInt();
    }

    public byte getByte(String key)
    {
        if (!contains(key)) return 0;
        return dataCatcher.get(key).getAsByte();
    }

    public byte[] getBytes(String key)
    {
        return getObject(key, new TypeToken<byte[]>() {
        }.getType());
    }

    public long getLong(String key)
    {
        if (!contains(key)) return 0L;
        return dataCatcher.get(key).getAsLong();
    }

    public double getDouble(String key)
    {
        if (!contains(key)) return 0D;
        return dataCatcher.get(key).getAsDouble();
    }

    public double[] getDoubles(String key)
    {
        if (!contains(key)) return null;
        return getObject(key, new TypeToken<double[]>() {
        }.getType());
    }

    public boolean getBoolean(String key)
    {
        if (!contains(key)) return false;
        return dataCatcher.get(key).getAsBoolean();
    }

    public float getFloat(String key)
    {
        if (!contains(key)) return 0F;
        return dataCatcher.get(key).getAsFloat();
    }

    public short getShort(String key)
    {
        if (!dataCatcher.has(key)) return 0;
        return dataCatcher.get(key).getAsShort();
    }

    public <T> T getObject(String key, Class<T> class_)
    {
        if (!dataCatcher.has(key)) return null;
        JsonElement element = dataCatcher.get(key);

        return GSON.fromJson(element, class_);
    }

    public <T> T getObject(String key, Type type)
    {
        if (!dataCatcher.has(key)) return null;
        return GSON.fromJson(dataCatcher.get(key), type);
    }

    public Document getDocument(String key)
    {
        if (!dataCatcher.has(key)) return null;
        Document document = new Document(dataCatcher.get(key).getAsJsonObject());
        return document;
    }

    public JsonArray getArray(String key)
    {
        if (!dataCatcher.has(key)) return null;
        return dataCatcher.get(key).getAsJsonArray();
    }

    public BigDecimal getBigDecimal(String key)
    {
        if (!dataCatcher.has(key)) return null;
        return dataCatcher.get(key).getAsBigDecimal();
    }

    public BigInteger getBigInteger(String key)
    {
        if (!dataCatcher.has(key)) return null;
        return dataCatcher.get(key).getAsBigInteger();
    }

        /*= ------------------------------------------------------------------- =*/

    public Document clear()
    {
        for (String key : keys())
            remove(key);
        return this;
    }

    public int size()
    {
        return this.dataCatcher.entrySet().size();
    }

    public Document remove(String key)
    {
        this.dataCatcher.remove(key);
        return this;
    }

    public Set<String> keys()
    {
        Set<String> c = new HashSet<>();

        for (Map.Entry<String, JsonElement> x : dataCatcher.entrySet())
        {
            c.add(x.getKey());
        }
        return c;
    }

    public Set<Map.Entry<String, JsonElement>> entries()
    {
        return dataCatcher.entrySet();
    }

    public boolean contains(String key)
    {
        return this.dataCatcher.has(key);
    }

    public boolean isEmpty()
    {
        return this.dataCatcher.entrySet().size() == 0;
    }

    public String toJson()
    {
        return dataCatcher.toString();
    }

    public String toPrettyJson()
    {
        return GSON.toJson(dataCatcher);
    }

    public JsonObject toJsonObject()
    {
        return dataCatcher;
    }

    public byte[] toBytes(Charset charset)
    {
        return toJson().getBytes(charset);
    }

    public byte[] toBytesAsUTF_8()
    {
        return toBytes(StandardCharsets.UTF_8);
    }

    public Document reload(File file)
    {
        return reload(file.toPath());
    }

    public Document reload(Path path)
    {
        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(path), "UTF-8"))
        {
            this.dataCatcher = PARSER.parse(reader).getAsJsonObject();
            return this;
        } catch (Exception ex)
        {
            ex.getStackTrace();
        }
        return new Document();
    }

    public boolean save(File backend)
    {
        return save(backend.toPath());
    }

    public boolean save(String path)
    {
        return save(Paths.get(path));
    }

    public boolean save(Path path)
    {
        if (path == null) return false;

        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(path), "UTF-8"))
        {
            GSON.toJson(dataCatcher, outputStreamWriter);
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static Document newDocument()
    {
        return new Document();
    }

    public static Document newDocument(File backend)
    {
        return newDocument(backend.toPath());
    }

    public static Document newDocument(Path path)
    {
        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(path), "UTF-8"))
        {
            JsonObject object = PARSER.parse(new BufferedReader(reader)).getAsJsonObject();
            return new Document(object);
        } catch (Exception ex)
        {
            ex.getStackTrace();
        }
        return new Document();
    }

    public static Document newDocument(JsonObject input)
    {
        return new Document(input);
    }

    public static Document newDocument(String input)
    {
        try (InputStreamReader reader = new InputStreamReader(new StringBufferInputStream(input), "UTF-8"))
        {
            return new Document(PARSER.parse(new BufferedReader(reader)).getAsJsonObject());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return new Document();
    }

    @Override
    public String toString()
    {
        return toJson();
    }

}