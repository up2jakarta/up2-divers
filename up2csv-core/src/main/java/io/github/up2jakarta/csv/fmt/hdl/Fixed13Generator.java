package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.data.Resettable;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.csv.fmt.FullExporter;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static io.github.up2jakarta.xml.adapters.KeyCoder.decode;
import static io.github.up2jakarta.xml.adapters.KeyCoder.fixed;

/**
 * Simple record-id generator that produces fixed-length of 16 characters.
 *
 * @see FullExporter#format(io.github.up2jakarta.csv.data.Referencable, Supplier, SegmentWriter)
 */
public final class Fixed13Generator implements LongSupplier, Supplier<String>, Resettable {

    public static final long FV_LG = decode("1up2jakarta13");

    private final long firstValue;
    private final AtomicLong generator;

    public Fixed13Generator() {
        this(FV_LG);
    }

    public Fixed13Generator(long firstValue) {
        this.firstValue = Math.max(firstValue, FV_LG);
        this.generator = new AtomicLong(this.firstValue);
    }

    @Override
    public String get() {
        final long i = generator.getAndIncrement();
        if (generator.get() < 1) {
            generator.set(1);
        }
        return fixed(i);
    }

    @Override
    public long getAsLong() {
        return generator.get();
    }

    @Override
    public void reset() {
        generator.set(firstValue);
    }

}
