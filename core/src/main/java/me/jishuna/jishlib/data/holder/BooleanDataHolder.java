package me.jishuna.jishlib.data.holder;

import java.io.IOException;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.source.DataWriter;

public class BooleanDataHolder extends DataHolder<Boolean> {

    protected BooleanDataHolder(String name, Boolean value) {
        super(name, value);
    }

    public static BooleanDataHolder of(Boolean value) {
        return of("", value);
    }

    public static BooleanDataHolder of(String name, Boolean value) {
        return new BooleanDataHolder(name, value);
    }

    @Override
    public void write(DataWriter writer) throws IOException {
        writer.writeBoolean(this.name, this);
    }

    @Override
    public HolderType getType() {
        return HolderType.BYTE;
    }
}
