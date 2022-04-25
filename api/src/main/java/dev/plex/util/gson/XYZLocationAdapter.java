package dev.plex.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dev.plex.util.XYZLocation;

import java.io.IOException;

public class XYZLocationAdapter extends TypeAdapter<XYZLocation>
{
    @Override
    public void write(JsonWriter out, XYZLocation value) throws IOException
    {
        if (value == null)
        {
            out.nullValue();
            return;
        }
        out.value(String.format("%s, %s, %s, %s, %s", value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch()));
    }

    @Override
    public XYZLocation read(JsonReader reader) throws IOException
    {
        if (reader.peek() == JsonToken.NULL)
        {
            reader.nextNull();
            return null;
        }
        String s = reader.nextString();
        String[] args = s.split(", ");
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);

        float yaw = Float.parseFloat(args[3]);
        float pitch = Float.parseFloat(args[4]);
        return new XYZLocation(x, y, z, yaw, pitch);
    }
}
