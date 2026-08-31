package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.lov.CodeList;

/**
 * Dynamic {@link ITerm} Model for {@link Up2Header} annotation.
 */
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

    @Override
    public String toString() {
        return CodeList.toString(code, name);
    }
}
