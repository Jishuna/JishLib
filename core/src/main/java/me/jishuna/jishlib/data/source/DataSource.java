package me.jishuna.jishlib.data.source;

import java.io.File;
import me.jishuna.jishlib.data.object.MapDataObject;

public interface DataSource {

    public MapDataObject read(File file);

    public void write(MapDataObject data, File file);
}
