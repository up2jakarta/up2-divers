package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.Resettable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static io.github.up2jakarta.lov.core.Codes.decodeInt;
import static io.github.up2jakarta.lov.core.Codes.fixed;

/**
 * Simple record-key generator that produces fixed-length of 8 characters.
 *
 * @see io.github.up2jakarta.csv.api.IFullRecord#getReference()
 * @see io.github.up2jakarta.csv.data.SegmentWriter
 */
public final class Fixed06Generator implements IntSupplier, Supplier<String>, Resettable {

    public static final int FV_SM = decodeInt("up2v06");

    private final int firstValue;
    private final AtomicInteger generator;

    public Fixed06Generator() {
        this(FV_SM);
    }

    public Fixed06Generator(int firstValue) {
        this.firstValue = Math.max(firstValue, FV_SM);
        this.generator = new AtomicInteger(this.firstValue);
    }

    @Override
    public String get() {
        final int i = generator.getAndIncrement();
        if (generator.get() < 1) {
            generator.set(1);
        }
        return fixed(i);
    }

    @Override
    public int getAsInt() {
        return generator.get();
    }

    @Override
    public void reset() {
        generator.set(firstValue);
    }

}
