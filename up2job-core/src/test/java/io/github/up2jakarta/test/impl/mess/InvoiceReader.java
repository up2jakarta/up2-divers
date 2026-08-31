package io.github.up2jakarta.test.impl.mess;

import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.io.MessFileReader;
import io.github.up2jakarta.job.core.SafeTranslator;
import io.github.up2jakarta.job.core.SafeUtil;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.apache.commons.csv.CSVFormat;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;

import java.io.File;

import static io.github.up2jakarta.test.AbstractJobITest.INPUT_FILE;
import static java.util.Objects.requireNonNull;

public class InvoiceReader extends MessFileReader<Invoice, TermType, SegmentType, InputRecord, InputError> implements ItemReader<Up2Result<Invoice, InputError>>, StepExecutionListener {

    public InvoiceReader(InvoiceImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected InputRecord create(SegmentType type, String invoiceNumber, String[] data) {
        return new InputRecord(type, invoiceNumber, data);
    }

    @Override
    public void beforeStep(StepExecution context) {
        final JobParameters parameters = context.getJobExecution().getJobParameters();
        try {
            super.open(new File(requireNonNull(parameters.getString(INPUT_FILE))));
        } catch (Exception e) {
            throw new ItemStreamException(e);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution context) {
        SafeUtil.safe(new SafeTranslator<>(ItemStreamException::new), super::close).propagate();
        return null;
    }

}

