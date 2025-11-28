package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.CodeList;

public class Bound<T extends CodeList<?>> {

    public final CodeList<? extends T> type1;
    public final CodeList<? super T> type2;

    public Bound(CodeList<? extends T> type1, CodeList<? super T> type2) {
        this.type1 = type1;
        this.type2 = type2;
    }

}
