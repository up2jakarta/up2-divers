package io.github.up2jakarta.job.ctx;

import org.slf4j.Logger;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.step.StepExecution;

import java.util.Map;

public abstract class FlowHandler<T extends ContextAware> implements ContextHolder<T> {

    abstract Map<String, Object> initialize(StepExecution execution, Logger logger, int stepId);

    abstract void finalize(StepExecution execution, Logger logger, int stepId);

    abstract void initialize(JobExecution execution, Logger logger);

}
