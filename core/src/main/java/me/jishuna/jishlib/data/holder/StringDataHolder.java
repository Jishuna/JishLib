package me.jishuna.jishlib.data.holder;

import java.io.IOException;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.source.DataWriter;

public class StringDataHolder extends DataHolder<String> {
    protected StringDataHolder(String name, String value) {
        super(name, value);
    }

    public static StringDataHolder of(String value) {
        return of("", value);
    }

    public static StringDataHolder of(String name, String value) {
        return new StringDataHolder(name, value);
    }

    @Override
    public void write(DataWriter writer) throws IOException {
        writer.writeString(this.name, this);
    }

    @Override
    public HolderType getType() {
        return HolderType.STRING;
    }
}
