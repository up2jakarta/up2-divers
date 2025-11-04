package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.io.FullFileReader;
import io.github.up2jakarta.job.core.SafeTranslator;
import io.github.up2jakarta.job.core.SafeUtil;
import io.github.up2jakarta.job.csv.dto.Invoice;
import io.github.up2jakarta.job.csv.impl.GroupType;
import io.github.up2jakarta.job.csv.impl.SegmentType;
import org.apache.commons.csv.CSVFormat;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;

import java.io.File;

import static io.github.up2jakarta.job.csv.AbstractJobITest.INPUT_FILE;
import static java.util.Objects.requireNonNull;

public class InvoiceReader extends FullFileReader<Invoice, GroupType, SegmentType, InputRecord, InputError> implements ItemReader<Up2Result<Invoice, InputError>>, StepExecutionListener {

    public InvoiceReader(InvoiceImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected InputRecord create(long lineId, String rowId, SegmentType type, String beanId, String[] data) {
        return new InputRecord(rowId, type, beanId, data);
    }

    @Override
    public void beforeStep(StepExecution context) {
        final JobParameters jps = context.getJobExecution().getJobParameters();
        try {
            super.open(new File(requireNonNull(jps.getString(INPUT_FILE))));
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

