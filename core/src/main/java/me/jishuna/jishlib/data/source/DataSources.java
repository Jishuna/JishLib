package me.jishuna.jishlib.data.source;

public final class DataSources {
    public static final YamlDataSource YAML = new YamlDataSource();
    public static final JsonDataSource JSON = new JsonDataSource();

    private DataSources() {
    }
}
