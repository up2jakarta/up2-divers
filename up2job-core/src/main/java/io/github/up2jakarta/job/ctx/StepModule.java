package io.github.up2jakarta.job.ctx;

import org.springframework.batch.core.step.tasklet.TaskletStep;

@SuppressWarnings("unused")
public abstract class StepModule<C extends ContextAware, S, P> {

    public abstract TaskletStep build(P config, S service, FlowHandler<? extends C> handler, String name);

    public abstract int getOrder();

}
