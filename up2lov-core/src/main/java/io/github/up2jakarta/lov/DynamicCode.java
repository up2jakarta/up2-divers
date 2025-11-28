package io.github.up2jakarta.lov;

/**
 * Simple {@link CodeList} implementation for {@link DynamicProvider}.
 */
public final class DynamicCode implements CodeList<DynamicCode> {

    private final String name;
    private final String code;

    public DynamicCode(String code, String name) {
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


    @Override
    public String toString() {
        return name;
    }

}
