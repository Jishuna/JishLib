package me.jishuna.jishlib.data.object;

public class BooleanDataObject extends DataObject<Boolean> {

    private BooleanDataObject(Boolean value) {
        super(value);
    }

    public static BooleanDataObject of(boolean value) {
        return new BooleanDataObject(value);
    }
}
