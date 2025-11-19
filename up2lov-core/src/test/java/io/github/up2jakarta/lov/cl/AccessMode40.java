package io.github.up2jakarta.lov.cl;

public final class AccessMode40 implements AccessMode41, AccessMode42 {

    @SuppressWarnings("unused")
    private static final AccessMode40 NA = new AccessMode40("NA", "No access");

    private final String name;
    private final String code;

    AccessMode40(String name, String code) {
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
