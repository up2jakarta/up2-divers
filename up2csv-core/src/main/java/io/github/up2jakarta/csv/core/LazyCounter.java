package io.github.up2jakarta.csv.core;

import java.util.function.IntSupplier;

public final class LazyCounter implements IntSupplier {

    static final int NAN = -1;

    private final IntSupplier supplier;
    private volatile int value = NAN;

    LazyCounter(IntSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public synchronized int getAsInt() {
        if (value == NAN) {
            value = supplier.getAsInt();
        }
        return value;
    }

}
