package io.github.up2jakarta.job.core;

@SuppressWarnings("unused")
public abstract class KeyCoder {

    private static final int RADIX = 16;

    public static String encode(long value) {
        return Long.toUnsignedString(value, RADIX);
    }

    public static long decode(String value) {
        return Long.parseUnsignedLong(value, RADIX);
    }

}
