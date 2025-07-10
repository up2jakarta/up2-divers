package io.github.up2jakarta.job.ctx;

@SuppressWarnings("unused")
public interface FlowHolder<T extends ContextAware> {

    T getContext();

    FlowHandler<T> getHandler();

    String flowName();

    int flowId();

    String stepName(int order);

}
