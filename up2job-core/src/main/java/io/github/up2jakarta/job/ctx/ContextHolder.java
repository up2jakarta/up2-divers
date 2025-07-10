package io.github.up2jakarta.job.ctx;

public interface ContextHolder<T extends ContextAware> {

    T getContext();

}
