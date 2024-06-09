package me.jishuna.jishlib.data.source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import me.jishuna.jishlib.data.object.MapDataObject;

public interface DataReader {
    public MapDataObject readFile(File file) throws IOException;

    public MapDataObject readStream(InputStream stream) throws IOException;
}
