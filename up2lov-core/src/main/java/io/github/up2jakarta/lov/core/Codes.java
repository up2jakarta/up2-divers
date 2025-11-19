package io.github.up2jakarta.lov.core;

import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;

public abstract class Codes {

    private static final CollapsedStringAdapter TOKEN_ADAPTER = new CollapsedStringAdapter();

    private static final int RADIX = Character.MAX_RADIX;
    private static final int LONG_SIZE = encode(Long.MAX_VALUE).length();
    private static final int INT_SIZE = encode(Integer.MAX_VALUE).length();

    public static String token(String value) {
        return TOKEN_ADAPTER.unmarshal(value);
    }

    public static String encode(long value) {
        return Long.toString(value, RADIX);
    }

    public static long decode(String value) {
        return Long.valueOf(value, RADIX);
    }

    public static String encodeInt(int value) {
        return Integer.toString(value, RADIX);
    }

    public static int decodeInt(String value) {
        return Integer.valueOf(value, RADIX);
    }

    public static String fixed(long value) {
        final String str = Long.toString(value, RADIX);
        return pad(str, LONG_SIZE, '0');
    }

    public static String fixed(int value) {
        final String str = Integer.toString(value, RADIX);
        return pad(str, INT_SIZE, '0');
    }

    public static String pad(String value, int size, char c) {
        if (value.length() < size) {
            final char[] source = value.toCharArray();
            final char[] target = new char[size];
            final int index = size - source.length;
            System.arraycopy(source, 0, target, index, source.length);
            for (var i = 0; i < index; i++) {
                target[i] = c;
            }
            value = new String(target);
        }
        return value;
    }

}
