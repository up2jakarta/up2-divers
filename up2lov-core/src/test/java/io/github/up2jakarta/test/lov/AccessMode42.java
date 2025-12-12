package io.github.up2jakarta.test.lov;

import io.github.up2jakarta.lov.CodeList;

public sealed interface AccessMode42 extends CodeList<AccessMode40> permits AccessMode40 {

    AccessMode40 WO = new AccessMode40("WO", "Write only");

}
