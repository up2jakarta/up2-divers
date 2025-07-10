package io.github.up2jakarta.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.SynchronizedItemWriter;

@SuppressWarnings("unused")
public class SynchronizedWriter<T> extends SynchronizedItemWriter<T> implements StepExecutionListener {

    private final ItemWriter<T> delegate;

    public SynchronizedWriter(ItemWriter<T> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        if (delegate instanceof StepExecutionListener listener) {
            listener.beforeStep(stepExecution);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (delegate instanceof StepExecutionListener listener) {
            return listener.afterStep(stepExecution);
        }
        return null;
    }

}
