package me.jishuna.jishlib.data.source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;

public interface DataReader {
    public MapDataHolder readFile(File file) throws IOException;

    public MapDataHolder readStream(InputStream stream) throws IOException;
}
