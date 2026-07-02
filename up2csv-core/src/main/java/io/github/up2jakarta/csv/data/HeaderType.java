package io.github.up2jakarta.csv.data;

public final class HeaderType implements ITerm<HeaderType> {

    private final String name;
    private final String code;

    HeaderType(String code, String name) {
        this.code = code;
        this.name = name;
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
