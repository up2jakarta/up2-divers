package io.github.up2jakarta.test.impl.fast;

import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.io.FastFileWriter;
import io.github.up2jakarta.job.core.SafeTranslator;
import io.github.up2jakarta.job.core.SafeUtil;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
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

import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.test.AbstractJobITest.OUTPUT_FILE;
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
    public void write(Chunk<? extends Up2Result<Invoice, InputError>> chunk) throws IOException {
        for (final Up2Result<Invoice, InputError> item : chunk) {
            final long warnings = item.toList().stream().filter(e -> ERROR.compareTo(e.getLevel()) > 0).count();
            if (warnings == item.toList().size()) {
                LOG.info("#Invoice[{}] has been imported with ({}) warnings", item.get().getReference(), warnings);
                this.write(item.get());
            } else {
                final String invoiceNumber = item.toList().getFirst().getRecord().getPivot();
                LOG.error("#Invoice[{}] has ({}) errors", invoiceNumber, item.toList().size());
                var i = 1;
                for (final InputError e : item.toList()) {
                    final Throwable cause = e.getCause().getCause();
                    LOG.warn("{}) #Offset[{}] #Type[{}] - {}", i++, e.getOffset(), e.getType(), e, cause);
                }
            }
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution context) {
        SafeUtil.safe(new SafeTranslator<>(ItemStreamException::new), super::close).propagate();
        return null;
    }

}
