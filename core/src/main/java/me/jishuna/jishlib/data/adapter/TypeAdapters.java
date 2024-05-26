package me.jishuna.jishlib.data.adapter;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import me.jishuna.jishlib.data.object.PrimitiveDataObject;

public final class TypeAdapters {

    public static final NumericAdapter<Integer> INT = new NumericAdapter<>(PrimitiveDataObject::asInt, Ints::tryParse);
    public static final NumericAdapter<Long> LONG = new NumericAdapter<>(PrimitiveDataObject::asLong, Longs::tryParse);
    public static final NumericAdapter<Float> FLOAT = new NumericAdapter<>(PrimitiveDataObject::asFloat, Floats::tryParse);
    public static final NumericAdapter<Double> DOUBLE = new NumericAdapter<>(PrimitiveDataObject::asDouble, Doubles::tryParse);

    public static final MaterialAdapter MATERIAL = new MaterialAdapter();
    public static final NamespaceKeyAdapter NAMESPACE = new NamespaceKeyAdapter();
    public static final ComponentAdapter COMPONENT = new ComponentAdapter();
    public static final ItemStackAdapter ITEM_STACK = new ItemStackAdapter();

    private TypeAdapters() {
    }
}
