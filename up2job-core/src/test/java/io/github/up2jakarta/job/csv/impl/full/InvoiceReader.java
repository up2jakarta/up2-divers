package io.github.up2jakarta.job.csv.impl.full;

import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.fmt.hdl.PathSource;
import io.github.up2jakarta.csv.io.FullFileReader;
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

import java.nio.file.Path;

import static io.github.up2jakarta.job.core.SafeUtil.call;
import static io.github.up2jakarta.job.csv.AbstractJobITest.INPUT_FILE;
import static java.util.Objects.requireNonNull;

public class InvoiceReader extends FullFileReader<Invoice, GroupType, SegmentType, PathSource, InputRecord, InputError> implements ItemReader<Up2Result<Invoice, InputError>>, StepExecutionListener {

    public InvoiceReader(InvoiceImporter importer, CSVFormat format) {
        super(importer, format, "-");
    }

    @Override
    protected InputRecord create(long lineId, String rowId, SegmentType type, String beanId, String[] data) {
        return new InputRecord(source, lineId, rowId, type, beanId, data);
    }

    @Override
    public void beforeStep(StepExecution context) {
        final JobParameters parameters = context.getJobExecution().getJobParameters();
        try {
            final Path path = Path.of(requireNonNull(parameters.getString(INPUT_FILE)));
            super.open(path.toFile(), new PathSource("TU", path));
        } catch (Exception e) {
            throw new ItemStreamException(e);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution context) {
        call(ItemStreamException::new, super::close);
        return null;
    }
}

