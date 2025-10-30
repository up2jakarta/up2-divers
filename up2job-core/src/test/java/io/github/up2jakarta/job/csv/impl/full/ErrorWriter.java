package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Format;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.io.SingleWriter;
import io.github.up2jakarta.job.ConditionalWriter;
import io.github.up2jakarta.job.csv.dto.Invoice;
import org.apache.commons.csv.CSVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemStreamException;

import java.io.File;
import java.io.IOException;

import static io.github.up2jakarta.job.core.SafeUtil.call;
import static io.github.up2jakarta.job.csv.AbstractJobITest.ERR_FILE;
import static java.util.Objects.requireNonNull;

public class ErrorWriter extends ConditionalWriter<Up2Result<Invoice, InputError>> {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorWriter.class);
    private final SingleWriter<InputError, DynamicType> delegate;

    public ErrorWriter(Up2Format<InputError, DynamicType> mapper, CSVFormat format) {
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
        return !item.getErrors().isEmpty();
    }

    @Override
    public void write(Up2Result<Invoice, InputError> item) throws IOException, BeanException {
        final String invoiceNumber = item.getErrors().getFirst().getKey().getRecord().getBusinessReference();
        LOG.warn("#Invoice[{}] has ({}) errors", invoiceNumber, item.getErrors().size());
        delegate.write(item.getErrors());
    }

    @Override
    public ExitStatus afterStep(StepExecution context) {
        call(ItemStreamException::new, delegate::flush, delegate::close);
        return null;
    }

}
