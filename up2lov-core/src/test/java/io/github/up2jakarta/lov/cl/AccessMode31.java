package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.Source;

@Source(AccessMode30.class)
public final class AccessMode31 extends AccessMode30 {

    public static final AccessMode30 RO = new AccessMode30("RO", "Read only");

    private AccessMode31(String code, String name) {
        super(code, name);
    }

}
