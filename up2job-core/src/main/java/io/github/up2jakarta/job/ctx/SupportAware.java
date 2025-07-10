package io.github.up2jakarta.job.ctx;

public abstract class SupportAware implements ContextAware {

    private final FlowHolder<? extends ContextAware> factory;
    private final String input;
    private int order = 0;

    protected SupportAware(FlowHolder<? extends ContextAware> factory, String input) {
        this.factory = factory;
        this.input = input;
    }

    @Override
    public final String getInput() {
        return input;
    }

    @Override
    public final int getOrder() {
        return order;
    }

    final void setOrder(int order) {
        this.order = order;
    }

    @Override
    public final String toString() {
        return factory.stepName(this.order);
    }

}
