package me.jishuna.jishlib.data.source.nbt;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ArrayDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;
import me.jishuna.jishlib.data.source.DataReader;

public class NBTReader implements DataReader {
    private final String pathSeperator;

    private NBTReader(String pathSeperator) {
        this.pathSeperator = pathSeperator;
    }

    public static NBTReader create(String pathSeperator) {
        return new NBTReader(pathSeperator);
    }

    @Override
    public MapDataHolder readFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return readStream(fis);
        }
    }

    @Override
    public MapDataHolder readStream(InputStream stream) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(stream)) {
            return switch (CompressionType.getCompression(bis)) {
            case NONE -> read(new DataInputStream(bis));
            case GZIP -> read(new DataInputStream(new GZIPInputStream(bis)));
            case ZLIB -> read(new DataInputStream(new InflaterInputStream(bis)));
            };
        }
    }

    private MapDataHolder read(DataInputStream input) throws IOException {
        try (input) {
            input.readByte();
            String name = input.readUTF();
            MapDataHolder data = readCompound(input);
            data.setName(name);
            return data;
        }
    }

    private MapDataHolder readCompound(DataInput reader) throws IOException {
        Map<String, DataHolder<?>> dataMap = new LinkedHashMap<>();

        byte nextTypeId;
        while ((nextTypeId = reader.readByte()) != 0) {
            String dataName = reader.readUTF();
            DataHolder<?> data = readValue(nextTypeId, reader);
            if (data != null) {
                data.setName(dataName);

                dataMap.put(dataName, data);
            }
        }

        return MapDataHolder.of("", this.pathSeperator, dataMap);
    }

    private ListDataHolder readList(DataInput reader) throws IOException {
        List<DataHolder<?>> dataList = new ArrayList<>();
        byte tagType = reader.readByte();
        System.out.println(tagType);
        int length = reader.readInt();

        for (int i = 0; i < length; i++) {
            DataHolder<?> data = readValue(tagType, reader);
            if (data != null) {
                dataList.add(data);
            }
        }

        return ListDataHolder.of(dataList);
    }

    private DataHolder<?> readValue(byte typeId, DataInput reader) throws IOException {
        return switch (typeId) {
        case 1 -> NumericDataHolder.of(reader.readByte());
        case 2 -> NumericDataHolder.of(reader.readShort());
        case 3 -> NumericDataHolder.of(reader.readInt());
        case 4 -> NumericDataHolder.of(reader.readLong());
        case 5 -> NumericDataHolder.of(reader.readFloat());
        case 6 -> NumericDataHolder.of(reader.readDouble());
        case 7 -> {
            List<DataHolder<?>> list = new ArrayList<>();
            int length = reader.readInt();
            for (int i = 0; i < length; i++) {
                list.add(NumericDataHolder.of(reader.readByte()));
            }

            yield ArrayDataHolder.of(list);
        }
        case 8 -> StringDataHolder.of(reader.readUTF());
        case 9 -> readList(reader);
        case 10 -> readCompound(reader);
        case 11 -> {
            List<DataHolder<?>> list = new ArrayList<>();
            int length = reader.readInt();
            for (int i = 0; i < length; i++) {
                list.add(NumericDataHolder.of(reader.readInt()));
            }

            yield ArrayDataHolder.of(list);
        }
        case 12 -> {
            List<DataHolder<?>> list = new ArrayList<>();
            int length = reader.readInt();
            for (int i = 0; i < length; i++) {
                list.add(NumericDataHolder.of(reader.readLong()));
            }

            yield ArrayDataHolder.of(list);
        }
        default -> null;
        };
    }
}
