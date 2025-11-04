package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.fmt.Fixed06Generator;
import io.github.up2jakarta.csv.io.FullFileWriter;
import io.github.up2jakarta.job.ConditionalWriter;
import io.github.up2jakarta.job.core.SafeTranslator;
import io.github.up2jakarta.job.core.SafeUtil;
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

import static io.github.up2jakarta.job.csv.AbstractJobITest.OUTPUT_FILE;
import static java.util.Objects.requireNonNull;

public class InvoiceWriter extends ConditionalWriter<Up2Result<Invoice, InputError>> {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceWriter.class);

    private final FullFileWriter<Invoice> delegate;

    public InvoiceWriter(InvoiceImporter importer, CSVFormat format) throws BeanException {
        this.delegate = new FullFileWriter<>(importer.toExporter(), format, new Fixed06Generator());
    }

    @Override
    public void beforeStep(StepExecution context) {
        final JobParameters parameters = context.getJobExecution().getJobParameters();
        try {
            delegate.open(new File(requireNonNull(parameters.getString(OUTPUT_FILE))));
        } catch (Exception e) {
            throw new ItemStreamException(e);
        }
    }

    @Override
    public boolean isTransient(Up2Result<Invoice, InputError> item) {
        return item.getBean() != null && !item.getErrors().isEmpty();
    }

    @Override
    public void write(Up2Result<Invoice, InputError> item) throws IOException, BeanException {
        LOG.debug("#Invoice[{}] has been imported successfully", item.getBean().getReference());
        delegate.write(item.getBean());
    }

    @Override
    public ExitStatus afterStep(StepExecution context) {
        SafeUtil.safe(new SafeTranslator<>(ItemStreamException::new), delegate::flush, delegate::close).propagate();
        return null;
    }

}
