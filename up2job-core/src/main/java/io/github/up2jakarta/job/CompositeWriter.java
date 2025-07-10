package io.github.up2jakarta.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@SuppressWarnings("unused")
public class CompositeWriter<T> implements ItemWriter<T>, StepExecutionListener {

    private final List<ItemWriter<? super T>> delegates;

    @SafeVarargs
    public CompositeWriter(ItemWriter<? super T> first, ItemWriter<? super T> second, ItemWriter<? super T>... others) {
        this.delegates = new ArrayList<>(others.length + 2);
        this.delegates.add(first);
        this.delegates.add(second);
        this.delegates.addAll(asList(others));
    }

    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {
        for (final ItemWriter<? super T> writer : delegates) {
            writer.write(chunk);
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        for (final ItemWriter<? super T> writer : delegates) {
            if (writer instanceof StepExecutionListener se) {
                se.beforeStep(stepExecution);
            }
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        for (final ItemWriter<? super T> writer : delegates) {
            if (writer instanceof StepExecutionListener se) {
                se.afterStep(stepExecution);
            }
        }
        return null;
    }

}
