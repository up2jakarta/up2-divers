package io.github.up2jakarta.job.ctx;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;

import java.util.List;

import static io.github.up2jakarta.job.ctx.FlowFormatter.FAILURE;
import static io.github.up2jakarta.job.ctx.FlowSource.FLOW;
import static io.github.up2jakarta.job.ctx.FlowSource.STEP;
import static io.github.up2jakarta.job.ctx.Flows.*;

@SuppressWarnings("unused")
public abstract class StepListener<T extends ContextAware> extends Loggable implements StepExecutionListener {

    protected final int order;
    protected final T context;
    private final FlowHandler<? extends T> handler;

    protected StepListener(StepModule<T, ?, ?> module, FlowHandler<? extends T> delegate) {
        this.context = delegate.getContext();
        this.order = module.getOrder();
        this.handler = delegate;
    }

    private long addFailures(final StepExecution execution) {
        final long failures = failCount(execution);
        final long result;
        if (context instanceof FaultAware collector) {
            if (failures == 0) {
                result = collector.getFailures();
            } else {
                result = failures;
            }
            collector.addFailures(failures);
        } else {
            result = failures;
        }
        return Math.min(result, processCount(execution));
    }

    private ExitStatus decide(StepExecution execution, long failures) {
        final List<Throwable> errors = execution.getFailureExceptions();
        if (errors.stream().anyMatch(Flows::isFatal)) {
            errors.forEach(ex -> warning(this.getLogger(), execution.getStepName(), execution.getId(), ex));
            return new ExitStatus(JOB_REJECTED, "");
        } else if (execution.getStatus() == BatchStatus.COMPLETED) {
            if (failures != 0) {
                execution.setStatus(BatchStatus.UNKNOWN);
                return new ExitStatus(JOB_CONTINUED, FAILURE.format(FLOW, failures));
            } else if (!errors.isEmpty()) {
                execution.setStatus(BatchStatus.FAILED);
                errors.forEach(ex -> warning(this.getLogger(), execution.getStepName(), execution.getId(), ex));
                return new ExitStatus(JOB_REJECTED, exitMessage(STEP, errors));
            }
        }
        return null;
    }

    protected void afterStep(T context, StepExecution execution) {
    }

    protected void beforeStep(T context, StepExecution execution) {
    }

    @Override
    public final void beforeStep(StepExecution execution) {
        handler.initialize(execution, this.getLogger(), order);
        beforeStep(context, execution);
    }

    @Override
    public final ExitStatus afterStep(StepExecution execution) {
        this.afterStep(context, execution);
        final long failures = this.addFailures(execution);
        final ExitStatus status = this.decide(execution, failures);
        handler.finalize(execution, this.getLogger(), order);
        this.logDuration(context.toString(), execution.getId(), execution.getStartTime());
        return status;
    }

}
