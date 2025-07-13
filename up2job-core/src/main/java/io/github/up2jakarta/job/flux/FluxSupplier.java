package io.github.up2jakarta.job.flux;

import java.io.Closeable;
import java.io.IOException;

@FunctionalInterface
public interface FluxSupplier<T extends Closeable> {

    T get() throws IOException;

}
