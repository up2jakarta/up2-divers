package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.IMode;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.HeaderResolver;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.job.CompositeWriter;
import io.github.up2jakarta.job.SynchronizedReader;
import io.github.up2jakarta.job.SynchronizedWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.impl.full.*;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.item.ChunkOrientedStep;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBatchTest
@SpringJUnitConfig(TUConfiguration.class)
class FullCopyJobITests extends AbstractJobITest {

    private final Job job;

    @Autowired
    FullCopyJobITests(ApplicationContext context, CSVFormat format) throws IOException, BeanException {
        super(IMode.FULL, format);
        this.job = this.job(context);
    }

    private ErrorWriter errorWriter(Up2Factory<TermType> factory, CSVFormat format) throws BeanException {
        var mapper = factory.of(HeaderResolver.getInstance()).flatter(InputError.class);
        return new ErrorWriter(mapper, format);
    }

    private Job job(ApplicationContext context) throws BeanException {
        final PlatformTransactionManager txm = context.getBean(PlatformTransactionManager.class);
        final AsyncTaskExecutor executor = context.getBean(AsyncTaskExecutor.class);
        final JobRepository repository = context.getBean(JobRepository.class);
        final Up2Factory<TermType> factory = Up2Factory.of(context::getBean);
        final CSVFormat format = context.getBean(CSVFormat.class);
        final InvoiceImporter importer = new InvoiceImporter(factory);
        final CompositeWriter<Up2Result<Invoice, InputError>> writer = new CompositeWriter<>(
                new SynchronizedWriter<>(this.errorWriter(factory, format)),
                new SynchronizedWriter<>(new InvoiceWriter(importer, format))
        );
        final ChunkOrientedStep<?, ?> step = new StepBuilder("copy", repository)
                .<Up2Result<Invoice, InputError>, Up2Result<Invoice, InputError>>chunk(5)
                .reader(new SynchronizedReader<>(new InvoiceReader(importer, format)))
                .writer(writer)
                .transactionManager(txm)
                .taskExecutor(executor)
                .faultTolerant()
                .skip(RuntimeException.class)
                .build();
        return new JobBuilder("FULL", repository)
                .start(step)
                .build();
    }

    @Test
    void test() throws Exception {
        // GIVEN
        final JobParameters jps = this.input(100);
        this.launcher.setJob(job);
        // WHEN
        final JobExecution job = launcher.startJob(jps);
        while (job.isRunning()) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        // THEN Execution
        assertStatus(job);
        assertSteps(job);
        assertFile(jps.getString(INPUT_FILE));
        assertFile(jps.getString(OUTPUT_FILE));
        assertFile(jps.getString(ERR_FILE));
    }

}
