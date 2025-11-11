package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.data.Resettable;
import io.github.up2jakarta.csv.data.SegmentWriter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static io.github.up2jakarta.xml.adapters.KeyCoder.decodeInt;
import static io.github.up2jakarta.xml.adapters.KeyCoder.fixed;

/**
 * Simple record-id generator that produces fixed-length of 8 characters.
 *
 * @see io.github.up2jakarta.csv.core.FullExporter#format(io.github.up2jakarta.csv.data.Segment, Supplier, SegmentWriter)
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
