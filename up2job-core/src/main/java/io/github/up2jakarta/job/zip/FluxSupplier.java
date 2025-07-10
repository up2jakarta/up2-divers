package io.github.up2jakarta.job.zip;

import java.io.Closeable;
import java.io.IOException;

@FunctionalInterface
@SuppressWarnings("unused")
public interface FluxSupplier<T extends Closeable> {

    T get() throws IOException;

}
