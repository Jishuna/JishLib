package me.jishuna.jishlib.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConfigField {

    private final Field field;
    private final boolean isStatic;
    private String path;

    public ConfigField(Field field) {
        this.field = field;
        this.isStatic = Modifier.isStatic(field.getModifiers());

        calculatePath();
    }

    public Field getField() {
        return field;
    }

    public String getPath() {
        return path;
    }
    
    public Class<?> getType() {
       return this.field.getType();
    }

    public boolean isStatic() {
        return isStatic;
    }

    private void calculatePath() {
        this.path = this.field.getAnnotation(ConfigEntry.class).value();

        if (this.path.isBlank()) {
            this.path = field.getName();
        }
    }
}
