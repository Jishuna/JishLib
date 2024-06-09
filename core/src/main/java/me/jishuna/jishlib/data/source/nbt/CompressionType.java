package me.jishuna.jishlib.data.source.nbt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public enum CompressionType {
    NONE, GZIP, ZLIB;

    public static CompressionType getCompression(InputStream in) throws IOException {
        if (!in.markSupported()) {
            in = new BufferedInputStream(in);
        }
        CompressionType type = NONE;

        in.mark(1);
        int firstByte = in.read();

        if (firstByte == 120) {
            type = ZLIB;
        }

        if (firstByte == 31) {
            type = GZIP;
        }

        in.reset();
        return type;
    }
}
