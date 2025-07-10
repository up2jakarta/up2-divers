package io.github.up2jakarta.job.ctx;

public interface ContextBuilder<B extends ContextAware, T extends SupportAware> {

    T build(FlowHolder<? extends B> factory, String input);

}
