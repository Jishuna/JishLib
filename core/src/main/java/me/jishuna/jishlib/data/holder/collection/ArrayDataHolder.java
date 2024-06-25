package me.jishuna.jishlib.data.holder.collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.source.DataWriter;

public class ArrayDataHolder extends ListDataHolder {

    private ArrayDataHolder(String name, List<DataHolder<?>> value) {
        super(name, value);
    }

    public static ArrayDataHolder empty(String name) {
        return new ArrayDataHolder(name, new ArrayList<>());
    }

    public static ArrayDataHolder of(List<DataHolder<?>> value) {
        return of("", value);
    }

    public static ArrayDataHolder of(String name, List<DataHolder<?>> value) {
        return new ArrayDataHolder(name, value);
    }

    @Override
    public void write(DataWriter<?> writer) throws IOException {
        writer.writeArray(this.name, this);
    }

    @Override
    public HolderType getType() {
        if (this.value.isEmpty()) {
            return HolderType.BYTE_ARRAY;
        }

        return switch (this.value.get(0).getType()) {
        case INT -> HolderType.INT_ARRAY;
        case LONG -> HolderType.LONG_ARRAY;
        default -> HolderType.BYTE_ARRAY;
        };
    }
}
