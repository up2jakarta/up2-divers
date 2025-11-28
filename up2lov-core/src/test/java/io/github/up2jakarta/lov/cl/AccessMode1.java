package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Source;

@Source(AccessMode2.class)
public class AccessMode1 implements CodeList<AccessMode1> {

    public static final AccessMode1 RO = new AccessMode1("RO", "Read only");
    public static final AccessMode1 WO = new AccessMode1("WO", "Write only");

    private final String code;
    private final String name;

    private AccessMode1(String code, String name) {
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
