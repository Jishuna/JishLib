package me.jishuna.jishlib.data.source;

import java.io.File;
import java.io.Reader;
import me.jishuna.jishlib.data.object.MapDataObject;

public interface DataSource {

    public MapDataObject read(File file);

    public MapDataObject read(Reader reader);

    public void write(MapDataObject data, File file);
}
