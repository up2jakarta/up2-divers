package io.github.up2jakarta.job.ctx;

@SuppressWarnings("unused")
public interface ContextAware {

    String getInput();

    int getOrder();

    String toString();

}
