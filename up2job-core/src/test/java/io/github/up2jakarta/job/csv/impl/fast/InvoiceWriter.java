package io.github.up2jakarta.job.csv.impl.fast;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.io.FastFileWriter;
import io.github.up2jakarta.job.csv.dto.Invoice;
import org.apache.commons.csv.CSVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.IOException;

import static io.github.up2jakarta.job.core.SafeUtil.call;
import static io.github.up2jakarta.job.csv.AbstractJobITest.OUTPUT_FILE;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.util.Objects.requireNonNull;

public class InvoiceWriter extends FastFileWriter<Invoice> implements ItemWriter<Up2Result<Invoice, InputError>>, StepExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceWriter.class);

    public InvoiceWriter(InvoiceImporter importer, CSVFormat format) throws BeanException {
        super(importer.toExporter(), format);
    }

    @Override
    public void beforeStep(StepExecution context) {
        final JobParameters parameters = context.getJobExecution().getJobParameters();
        try {
            super.open(new File(requireNonNull(parameters.getString(OUTPUT_FILE))));
        } catch (Exception e) {
            throw new ItemStreamException(e);
        }
    }

    @Override
    public void write(Chunk<? extends Up2Result<Invoice, InputError>> chunk) throws IOException, BeanException {
        for (final Up2Result<Invoice, InputError> item : chunk) {
            final long warnings = item.getErrors().stream().filter(e -> ERROR.compareTo(e.getSeverity()) > 0).count();
            if (warnings == item.getErrors().size()) {
                LOG.info("#Invoice[{}] has been imported with ({}) warnings", item.getBean().getReference(), warnings);
                this.write(item.getBean());
            } else {
                final String invoiceNumber = item.getErrors().getFirst().getRecord().getBusinessReference();
                LOG.error("#Invoice[{}] has ({}) errors", invoiceNumber, item.getErrors().size());
                var i = 1;
                for (final InputError e : item.getErrors()) {
                    final Throwable cause = e.getCause().getCause();
                    LOG.warn("{}) #Offset[{}] #Type[{}] - {}", i++, e.getOffset(), e.getType(), e, cause);
                }
            }
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution context) {
        call(ItemStreamException::new, super::flush, super::close);
        return null;
    }

}
