package io.github.up2jakarta.lov.core;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Alternative of {@link java.util.Optional}, but isn't immutable.
 * <p>
 * <b>It should be used in <code>final</code> properties only, like <code>record</code></b>
 * </p>
 * {@snippet lang = "java":
 *       public class BusinessObject {
 *
 *            private final Wrapper<String> reference = new Wrapper<>();
 *
 *            // ... other properties
 *
 *            public Wrapper<String> getReference() {
 *                 return this.reference;
 *            }
 *
 *            @Deprecated(forRemoval = true)
 *            public void setReference(String reference) {
 *                 this.reference.accept(reference);
 *            }
 *
 *            // ... getters and setters
 *     }
 *}
 *
 * @param <T> the value type
 * @see Beans#unwrapType(java.lang.reflect.Field, java.lang.reflect.Type...)
 */
public final class Wrapper<T> implements Supplier<T>, Consumer<T> {

    private T value;

    public Wrapper() {
        this.value = null;
    }

    public Wrapper(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void accept(T value) {
        this.value = value;
    }

    /**
     * @see java.util.Optional#orElseGet(Supplier) (Supplier)
     */
    public <X extends Throwable> void accept(Computer<? extends T, X> defaultSupplier) throws X {
        if (value == null) {
            this.value = defaultSupplier.get();
        }
    }

    /**
     * @see java.util.Optional#isEmpty()
     */
    public boolean isEmpty() {
        return value == null;
    }

    /**
     * @see java.util.Optional#isPresent()
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * @see java.util.Optional#orElseThrow(Supplier)
     */
    public <X extends Throwable> T orThrow(Supplier<? extends X> causeSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw causeSupplier.get();
        }
    }

    /**
     * @see java.util.Optional#or(Supplier)
     */
    public Wrapper<T> orElse(Supplier<? extends T> supplier) {
        if (this.value == null) {
            this.value = supplier.get();
        }
        return this;
    }

    /**
     * @see java.util.Optional#orElseGet(Supplier)
     */
    public T or(Supplier<? extends T> defaultSupplier) {
        if (this.value != null) {
            return this.value;
        }
        return defaultSupplier.get();
    }

    /**
     * @see java.util.Optional#orElse(Object)
     */
    public T or(T defaultValue) {
        if (this.value != null) {
            return this.value;
        }
        return defaultValue;
    }

    /**
     * @see java.util.Optional#map(Function)
     * @see java.util.Optional#orElse(Object)
     */
    public <U> U map(Function<? super T, ? extends U> mapper) {
        if (this.value == null) {
            return null;
        }
        return mapper.apply(value);
    }

    /**
     * @see java.util.Optional#map(Function)
     * @see java.util.Optional#orElseGet(Supplier)
     */
    public <U> U map(Function<? super T, ? extends U> mapper, Supplier<? extends U> defaultSupplier) {
        if (this.value == null) {
            return defaultSupplier.get();
        }
        return mapper.apply(value);
    }

    /**
     * @see java.util.Optional#map(Function)
     * @see java.util.Optional#orElse(Object)
     */
    public <U> U map(Function<? super T, ? extends U> mapper, U defaultValue) {
        if (this.value == null) {
            return defaultValue;
        }
        return mapper.apply(value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Wrapper<?> wrapper)) {
            return false;
        }
        return Objects.equals(value, wrapper.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
