package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.data.Resettable;
import io.github.up2jakarta.csv.data.SegmentWriter;
import io.github.up2jakarta.csv.fmt.FullExporter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static io.github.up2jakarta.xml.adapters.KeyCoder.fixed;

/**
 * Simple record-id generator that produces fixed-length of 8 characters.
 *
 * @see FullExporter#format(io.github.up2jakarta.csv.data.Referencable, Supplier, SegmentWriter)
 */
public final class Fixed08Generator implements IntSupplier, Supplier<String>, Resettable {

    private final int firstValue;
    private final AtomicInteger generator;

    public Fixed08Generator() {
        this(1);
    }

    public Fixed08Generator(int firstValue) {
        this(new AtomicInteger(firstValue));
    }

    public Fixed08Generator(AtomicInteger generator) {
        this.generator = generator;
        this.firstValue = generator.get();
    }

    @Override
    public String get() {
        return fixed(generator.getAndIncrement());
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
