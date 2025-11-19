package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.Source;

@Source(AccessMode30.class)
public final class AccessMode32 extends AccessMode30 {

    public static final AccessMode32 WO = new AccessMode32("WO", "Write only");

    private AccessMode32(String name, String code) {
        super(name, code);
    }

}
