package io.github.up2jakarta.job.ctx;

import org.slf4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

class FlowFactory<B extends ContextAware, I extends SupportAware, T extends B, P extends Enum<P> & ContextParameter<P>> implements FlowHolder<T> {

    private final FlowHandler<T> handler;
    private final String name;
    private final int order;

    @SuppressWarnings("unchecked")
    FlowFactory(Class<T> bi, Class<B> bc, ContextBuilder<B, I> cb, ContextProvider<P, ?> lp, String input, int order) {
        this.order = order;
        final I baseInstance = cb.build(this, input);
        this.name = new File(baseInstance.getInput()).getName();
        final ContextHandler<B, I> handler = new ContextHandler<>(bc, baseInstance, lp, order);
        final T proxy = (T) Proxy.newProxyInstance(bi.getClassLoader(), new Class[]{bi, FaultAware.class}, handler);
        this.handler = new FlowHandler<>() {
            @Override
            public Map<String, Object> initialize(StepExecution execution, Logger logger, int stepId) {
                final Map<String, Object> data = handler.initialize(execution, logger, stepId);
                lp.copyInputs(logger, baseInstance, execution.getExecutionContext(), stepId, data);
                return data;
            }

            @Override
            void initialize(JobExecution execution, Logger logger) {
                handler.initialize(execution, logger);
            }

            @Override
            public void finalize(StepExecution execution, Logger logger, int stepId) {
                handler.finalize(execution, logger, stepId);
                final List<Throwable> errors = execution.getFailureExceptions();
                if (errors.isEmpty()) {
                    lp.checkOutputs(logger, execution.getExecutionContext(), baseInstance, stepId);
                }
            }

            @Override
            public T getContext() {
                return proxy;
            }
        };
    }

    @Override
    public T getContext() {
        return handler.getContext();
    }

    @Override
    public FlowHandler<T> getHandler() {
        return handler;
    }

    @Override
    public int flowId() {
        return order;
    }

    @Override
    public String flowName() {
        return Flows.flowName(order, name);
    }

    @Override
    public String stepName(int order) {
        return Flows.stepName(order, name);
    }

}
