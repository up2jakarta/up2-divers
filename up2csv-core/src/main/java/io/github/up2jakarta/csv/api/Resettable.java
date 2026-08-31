package io.github.up2jakarta.csv.api;

@FunctionalInterface
public interface Resettable {
    /**
     * Resets the buffer to the initial state.
     */
    void reset();
}
