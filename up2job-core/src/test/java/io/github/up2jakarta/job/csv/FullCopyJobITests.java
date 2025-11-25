package io.github.up2jakarta.job.csv;

import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.job.CompositeWriter;
import io.github.up2jakarta.job.SynchronizedReader;
import io.github.up2jakarta.job.SynchronizedWriter;
import io.github.up2jakarta.job.csv.dto.Invoice;
import io.github.up2jakarta.job.csv.impl.GroupType;
import io.github.up2jakarta.job.csv.impl.full.*;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

import static io.github.up2jakarta.csv.data.DataTypeResolver.dynamic;

@SpringBatchTest
@SpringJUnitConfig(TUConfiguration.class)
class FullCopyJobITests extends AbstractJobITest {

    private final Job job;

    @Autowired
    FullCopyJobITests(ApplicationContext context, CSVFormat format) throws IOException, BeanException {
        super(ModeType.FULL, format);
        this.job = this.fullJob(context, context.getBean(PlatformTransactionManager.class));
    }

    private ErrorWriter errorWriter(Up2Factory<GroupType> factory, CSVFormat format) throws BeanException {
        final Up2Flatter<InputError, DynamicType> mapper = factory.format(InputError.class, dynamic());
        return new ErrorWriter(mapper, format);
    }

    private Job fullJob(ApplicationContext context, PlatformTransactionManager txm) throws BeanException {
        final CSVFormat format = context.getBean(CSVFormat.class);
        final JobRepository repository = context.getBean(JobRepository.class);
        final Up2Factory<GroupType> factory = new Up2Factory<>(context::getBean, GroupType.class);
        final InvoiceImporter importer = new InvoiceImporter(factory);
        final CompositeWriter<Up2Result<Invoice, InputError>> writer = new CompositeWriter<>(
                new SynchronizedWriter<>(this.errorWriter(factory, format)),
                new SynchronizedWriter<>(new InvoiceWriter(importer, format))
        );
        final TaskletStep step = new StepBuilder("copy", repository)
                .<Up2Result<Invoice, InputError>, Up2Result<Invoice, InputError>>chunk(5, txm)
                .reader(new SynchronizedReader<>(new InvoiceReader(importer, format)))
                .writer(writer)
                .taskExecutor(context.getBean(AsyncTaskExecutor.class))
                .faultTolerant()
                .noRetry(Throwable.class)
                .skip(RuntimeException.class)
                .build();
        return new JobBuilder("FULL", repository)
                .start(step)
                .build();
    }

    @Test
    void test() throws Exception {
        // GIVEN
        final JobParameters jps = this.input(1_000);
        this.launcher.setJob(job);
        // WHEN
        final JobExecution job = launcher.launchJob(jps);
        // THEN Execution
        assertStatus(job);
        assertSteps(job);
        assertFile(jps.getString(INPUT_FILE));
        assertFile(jps.getString(OUTPUT_FILE));
        assertFile(jps.getString(ERR_FILE));
    }

}
