package me.jishuna.jishlib.data.adapter;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import me.jishuna.jishlib.data.object.NumericDataObject;

public final class DefaultAdapters {

    public static final NumericAdapter<Integer> INT = new NumericAdapter<>(NumericDataObject::intValue, Ints::tryParse);
    public static final NumericAdapter<Long> LONG = new NumericAdapter<>(NumericDataObject::longValue, Longs::tryParse);
    public static final NumericAdapter<Float> FLOAT = new NumericAdapter<>(NumericDataObject::floatValue, Floats::tryParse);
    public static final NumericAdapter<Double> DOUBLE = new NumericAdapter<>(NumericDataObject::doubleValue, Doubles::tryParse);

    public static final MaterialAdapter MATERIAL = new MaterialAdapter();
    public static final NamespaceKeyAdapter NAMESPACE = new NamespaceKeyAdapter();
    public static final ComponentAdapter COMPONENT = new ComponentAdapter();
    public static final ItemStackAdapter ITEM_STACK = new ItemStackAdapter();

    private DefaultAdapters() {
    }
}
