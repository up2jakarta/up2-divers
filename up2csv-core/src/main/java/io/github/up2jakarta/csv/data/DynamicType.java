package io.github.up2jakarta.csv.data;

public final class DynamicType implements DataType<DynamicType> {

    private final String name;
    private final String code;

    DynamicType(String code, String name) {
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

    @Override
    public int getMin() {
        return 0;
    }

    @Override
    public int getMax() {
        return N;
    }

}
