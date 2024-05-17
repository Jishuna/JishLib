package me.jishuna.jishlib.data.object;

public class StringDataObject extends DataObject<String> {

    private StringDataObject(String value) {
        super(value);
    }

    public static StringDataObject of(String value) {
        return new StringDataObject(value);
    }
}
