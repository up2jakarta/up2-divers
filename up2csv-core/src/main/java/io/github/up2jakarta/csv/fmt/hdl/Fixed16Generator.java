package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.data.Resettable;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.csv.fmt.FullExporter;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import static io.github.up2jakarta.xml.adapters.KeyCoder.fixed;

/**
 * Simple record-id generator that produces fixed-length of 16 characters.
 *
 * @see FullExporter#format(io.github.up2jakarta.csv.data.Referencable, Supplier, SegmentWriter)
 */
public final class Fixed16Generator implements LongSupplier, Supplier<String>, Resettable {

    private final long firstValue;
    private final AtomicLong generator;

    public Fixed16Generator() {
        this(1);
    }

    public Fixed16Generator(long firstValue) {
        this(new AtomicLong(firstValue));
    }

    public Fixed16Generator(AtomicLong generator) {
        this.generator = generator;
        this.firstValue = generator.get();
    }

    @Override
    public String get() {
        return fixed(generator.getAndIncrement());
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
