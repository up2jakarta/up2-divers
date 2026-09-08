package io.github.up2jakarta.test.impl.full;

import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.data.HeaderType;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.io.SingleWriter;
import io.github.up2jakarta.job.ConditionalWriter;
import io.github.up2jakarta.job.core.SafeTranslator;
import io.github.up2jakarta.job.core.SafeUtil;
import io.github.up2jakarta.test.dto.Invoice;
import org.apache.commons.csv.CSVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemStreamException;

import java.io.File;
import java.io.IOException;

import static io.github.up2jakarta.test.AbstractJobITest.ERR_FILE;
import static java.util.Objects.requireNonNull;

public class ErrorWriter extends ConditionalWriter<Up2Result<Invoice, InputError>> {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorWriter.class);
    private final SingleWriter<InputError, HeaderType> delegate;

    public ErrorWriter(Up2Flatter<InputError, HeaderType> mapper, CSVFormat format) {
        this.delegate = new SingleWriter<>(mapper, format);
    }

    @Override
    public void beforeStep(StepExecution context) {
        final JobParameters parameters = context.getJobExecution().getJobParameters();
        try {
            delegate.open(new File(requireNonNull(parameters.getString(ERR_FILE))));
        } catch (Exception e) {
            throw new ItemStreamException(e);
        }
    }

    @Override
    public boolean isTransient(Up2Result<Invoice, InputError> item) {
        return !item.toList().isEmpty();
    }

    @Override
    public void write(Up2Result<Invoice, InputError> item) throws IOException {
        final String invoiceNumber = item.toList().getFirst().getKey().getRecord().getPivot();
        LOG.warn("#Invoice[{}] has ({}) errors", invoiceNumber, item.toList().size());
        delegate.write(item.toList());
    }

    @Override
    public ExitStatus afterStep(StepExecution context) {
        SafeUtil.safe(new SafeTranslator<>(ItemStreamException::new), delegate::flush, delegate::close).propagate();
        return null;
    }

}
