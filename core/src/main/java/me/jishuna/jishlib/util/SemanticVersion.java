package me.jishuna.jishlib.util;

import java.util.Objects;

public class SemanticVersion implements Comparable<SemanticVersion> {

    private final int major;
    private final int minor;
    private final int patch;
    private final long packed;

    public SemanticVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.packed = packValues();
    }

    public static SemanticVersion fromString(String version) {
        version = version.replaceAll("[^\\d.]", "");
        String[] parts = version.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("String must be in the format major.minor.patch");
        }

        try {
            return new SemanticVersion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("String must be in the format major.minor.patch");
        }
    }

    public boolean newerThan(SemanticVersion other) {
        return this.packed > other.packed;
    }

    public boolean newerThanOrEqual(SemanticVersion other) {
        return this.packed >= other.packed;
    }

    public boolean olderThan(SemanticVersion other) {
        return this.packed < other.packed;
    }

    public boolean olderThanOrEqual(SemanticVersion other) {
        return this.packed <= other.packed;
    }

    @Override
    public int compareTo(SemanticVersion o) {
        return Long.compare(this.packed, o.packed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.major, this.minor, this.patch);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SemanticVersion other)) {
            return false;
        }
        return this.packed == other.packed;
    }

    @Override
    public String toString() {
        return this.major + "." + this.minor + "." + this.patch;
    }

    private long packValues() {
        return ((long) this.major << 42) | ((long) this.minor << 21) | (this.patch);
    }
}
