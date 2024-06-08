package me.jishuna.jishlib.data.source.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import me.jishuna.jishlib.data.object.ArrayDataObject;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;
import me.jishuna.jishlib.data.source.DataSource;

public class NBTDataSource implements DataSource {
    private final String pathSeperator;

    public NBTDataSource(String pathSeperator) {
        this.pathSeperator = pathSeperator;
    }

    @Override
    public MapDataObject read(File file) {
        try (FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            return switch (CompressionType.getCompression(fis)) {
            case NONE -> read(new DataInputStream(bis));
            case GZIP -> read(new DataInputStream(new GZIPInputStream(bis)));
            case ZLIB -> read(new DataInputStream(new InflaterInputStream(bis)));
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MapDataObject.empty("");
    }

    private MapDataObject read(DataInputStream input) {
        try (input) {
            input.readByte();
            String name = input.readUTF();
            MapDataObject data = readCompound(input);
            data.setName(name);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MapDataObject.empty("");
    }

    @Override
    public void write(MapDataObject data, File file) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                DataOutputStream writer = new DataOutputStream(new GZIPOutputStream(bos))) {
            writer.writeByte(TagType.COMPOUND.id());
            writer.writeUTF(data.getName());
            data.write(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MapDataObject readCompound(DataInput reader) throws IOException {
        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();

        byte nextTypeId;
        while ((nextTypeId = reader.readByte()) != 0) {
            String dataName = reader.readUTF();
            DataObject<?> data = readValue(nextTypeId, reader);
            if (data != null) {
                data.setName(dataName);

                dataMap.put(dataName, data);
            }
        }

        return MapDataObject.of("", this.pathSeperator, dataMap);
    }

    private ListDataObject readList(DataInput reader) throws IOException {
        List<DataObject<?>> dataList = new ArrayList<>();
        byte tagType = reader.readByte();
        System.out.println(tagType);
        int length = reader.readInt();

        for (int i = 0; i < length; i++) {
            DataObject<?> data = readValue(tagType, reader);
            if (data != null) {
                dataList.add(data);
            }
        }

        return ListDataObject.of(dataList);
    }

    private DataObject<?> readValue(byte typeId, DataInput reader) throws IOException {
        return switch (typeId) {
        case 1 -> NumericDataObject.of(reader.readByte());
        case 2 -> NumericDataObject.of(reader.readShort());
        case 3 -> NumericDataObject.of(reader.readInt());
        case 4 -> NumericDataObject.of(reader.readLong());
        case 5 -> NumericDataObject.of(reader.readFloat());
        case 6 -> NumericDataObject.of(reader.readDouble());
        case 7 -> {
            List<DataObject<?>> list = new ArrayList<>();
            int length = reader.readInt();
            for (int i = 0; i < length; i++) {
                list.add(NumericDataObject.of(reader.readByte()));
            }

            yield ArrayDataObject.of(list);
        }
        case 8 -> StringDataObject.of(reader.readUTF());
        case 9 -> readList(reader);
        case 10 -> readCompound(reader);
        case 11 -> {
            List<DataObject<?>> list = new ArrayList<>();
            int length = reader.readInt();
            for (int i = 0; i < length; i++) {
                list.add(NumericDataObject.of(reader.readInt()));
            }

            yield ArrayDataObject.of(list);
        }
        case 12 -> {
            List<DataObject<?>> list = new ArrayList<>();
            int length = reader.readInt();
            for (int i = 0; i < length; i++) {
                list.add(NumericDataObject.of(reader.readLong()));
            }

            yield ArrayDataObject.of(list);
        }
        default -> null;
        };
    }

    @Override
    public MapDataObject read(Reader reader) {
        // TODO Auto-generated method stub
        return null;
    }
}
