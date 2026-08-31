package io.github.up2jakarta.csv.hdl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntSupplier;

public final class LazyCounter implements IntSupplier {

    private final Lock mutex = new ReentrantLock();
    private final IntSupplier repository;
    private AtomicInteger value;

    public LazyCounter(IntSupplier repository) {
        this.repository = repository;
    }

    @Override
    public int getAsInt() {
        this.mutex.lock();
        try {
            if (value == null) {
                value = new AtomicInteger(repository.getAsInt());
            }
            return value.getAndIncrement();
        } finally {
            this.mutex.unlock();
        }
    }

}
