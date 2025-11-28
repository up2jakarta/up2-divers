package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.Deprecated;

@SuppressWarnings("ALL")
public final class AccessMode40 implements AccessMode41, AccessMode42 {

    // Renaming
    public static final AccessMode40 R = RO;
    public static final AccessMode40 W = WO;

    @Deprecated
    private static final AccessMode40 NA = new AccessMode40("NA", "No access");
    @Deprecated(exclude = false)
    private static final AccessMode40 RW = new AccessMode40("RW", "Read & Write");
    private final String code;
    private final String name;

    AccessMode40(String code, String name) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
