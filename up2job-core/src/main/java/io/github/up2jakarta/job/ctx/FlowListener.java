package io.github.up2jakarta.job.ctx;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.step.StepExecution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.github.up2jakarta.job.ctx.FlowFormatter.REJECTION;
import static io.github.up2jakarta.job.ctx.FlowFormatter.WARNING;
import static io.github.up2jakarta.job.ctx.FlowSource.JOB;
import static io.github.up2jakarta.job.ctx.Flows.*;
import static java.util.function.Predicate.not;

@SuppressWarnings("unused")
public abstract class FlowListener<B extends ContextAware, I extends SupportAware, T extends B> extends Loggable implements JobExecutionListener {

    private final List<FlowHolder<T>> holders;

    protected FlowListener(Class<T> fi, Class<B> si, ContextBuilder<B, I> cb, ContextProvider<?, ?> cp, String[] inputs) {
        if (!fi.isInterface()) {
            throw new IllegalArgumentException("finalInterface");
        }
        if (!si.isInterface()) {
            throw new IllegalArgumentException("superInterface");
        }
        final List<FlowHolder<T>> result = new ArrayList<>(inputs.length);
        for (var i = 0; i < inputs.length; i++) {
            result.add(new FlowFactory<>(fi, si, cb, cp, inputs[i], i + 1));
        }
        this.holders = List.copyOf(result);
    }

    protected final long failures() {
        return this.holders().map(FlowHolder::getHandler).map(FlowHandler::getContext)
                .filter(FaultAware.class::isInstance)
                .map(FaultAware.class::cast)
                .mapToLong(FaultAware::getFailures)
                .sum();
    }

    protected final Stream<FlowHolder<T>> holders() {
        return holders.stream();
    }

    @Override
    public void beforeJob(JobExecution job) {
        this.holders().map(FlowHolder::getHandler).forEach(h -> h.initialize(job, this.getLogger()));
    }

    @Override
    public void afterJob(JobExecution job) {
        final List<Throwable> jobErrors = job.getFailureExceptions().stream().filter(not(Flows::isFatal)).toList();
        final List<StepExecution> rejects = job.getStepExecutions().stream().filter(Flows::isRejected).toList();
        final List<StepExecution> passes = job.getStepExecutions().stream().filter(Flows::isContinued).toList();
        if (!jobErrors.isEmpty()) {
            job.setExitStatus(new ExitStatus(JOB_FAILED, exitMessage(JOB, jobErrors)));
        } else if (!rejects.isEmpty()) {
            final String details = rejects.stream().map(Flows::summary).collect(joining());
            final String message = REJECTION.format(JOB, rejects.size()) + ":\n" + details;
            job.setExitStatus(new ExitStatus(JOB_REJECTED, message));
        } else if (!passes.isEmpty()) {
            job.setStatus(BatchStatus.UNKNOWN);
            final String details = passes.stream().map(Flows::summary).collect(joining());
            final String message = WARNING.format(JOB, passes.size()) + ":\n" + details;
            job.setExitStatus(new ExitStatus(JOB_CONTINUED, message));
        }
    }

}
