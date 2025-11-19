package io.github.up2jakarta.job.csv;

import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.job.SynchronizedReader;
import io.github.up2jakarta.job.SynchronizedWriter;
import io.github.up2jakarta.job.csv.dto.Invoice;
import io.github.up2jakarta.job.csv.impl.GroupType;
import io.github.up2jakarta.job.csv.impl.unit.InputError;
import io.github.up2jakarta.job.csv.impl.unit.InvoiceImporter;
import io.github.up2jakarta.job.csv.impl.unit.InvoiceReader;
import io.github.up2jakarta.job.csv.impl.unit.InvoiceWriter;
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

@SpringBatchTest
@SpringJUnitConfig(TUConfiguration.class)
class UnitCopyJobITests extends AbstractJobITest {

    private final Job job;

    @Autowired
    UnitCopyJobITests(ApplicationContext context, CSVFormat format) throws IOException, BeanException {
        super(ModeType.UNIT, format);
        this.job = this.fastJob(context, context.getBean(PlatformTransactionManager.class));
    }

    private Job fastJob(ApplicationContext context, PlatformTransactionManager txm) throws BeanException {
        final CSVFormat format = context.getBean(CSVFormat.class);
        final JobRepository repository = context.getBean(JobRepository.class);
        final Up2Factory<GroupType> factory = new Up2Factory<>(context::getBean, GroupType.class);
        final InvoiceImporter importer = new InvoiceImporter(factory);
        final InvoiceReader reader = new InvoiceReader(importer, format);
        final InvoiceWriter writer = new InvoiceWriter(importer, format);
        final TaskletStep step = new StepBuilder("copy", repository)
                .<Up2Result<Invoice, InputError>, Up2Result<Invoice, InputError>>chunk(5, txm)
                .reader(new SynchronizedReader<>(reader))
                .writer(new SynchronizedWriter<>(writer))
                .taskExecutor(context.getBean(AsyncTaskExecutor.class))
                .faultTolerant()
                .noRetry(Throwable.class)
                .skip(RuntimeException.class)
                .build();
        return new JobBuilder("FAST", repository)
                .start(step)
                .build();
    }

    @Test
    void test() throws Exception {
        // GIVEN
        final JobParameters jps = this.input(100);
        this.launcher.setJob(job);
        // WHEN
        final JobExecution job = launcher.launchJob(jps);
        // THEN Execution
        assertStatus(job);
        assertSteps(job);
        assertFile(jps.getString(INPUT_FILE));
        assertFile(jps.getString(OUTPUT_FILE));
    }

}
