package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Deprecated;

public final class EnumLike implements CodeList<EnumLike> {

    @Deprecated
    public static final EnumLike NAN = new EnumLike("N", "NAN");
    public static final EnumLike ONE = new EnumLike("1", "ONE");

    private final String name;
    private final String code;

    private EnumLike(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }
}
