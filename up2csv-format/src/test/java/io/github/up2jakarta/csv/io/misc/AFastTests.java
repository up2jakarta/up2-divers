package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.io.FastFileReader;
import io.github.up2jakarta.csv.io.FastFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.ops.FastAggregator;
import io.github.up2jakarta.csv.ops.ModeType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AFastTests<R extends IRecord<SegmentType>, A extends FastAggregator<Invoice, GroupType, SegmentType, R, ?>> extends ABusinessTest<R> {

    private final FastFileWriter<Invoice> writer;
    private final FastFileReader<Invoice, GroupType, SegmentType, R, ?> reader1;
    private final FastFileReader<Invoice, GroupType, SegmentType, R, ?> reader2;

    protected AFastTests(A aggregator, CSVFormat format) throws IOException {
        super(ModeType.FAST, format);
        this.reader1 = this.reader(aggregator, format);
        this.reader2 = this.reader(aggregator, format);
        this.writer = this.writer(aggregator, format);
    }

    protected abstract FastFileWriter<Invoice> writer(A aggregator, CSVFormat format);

    protected abstract FastFileReader<Invoice, GroupType, SegmentType, R, ?> reader(A aggregator, CSVFormat format);

    @Override
    final void assertRecord(R data, R origin) {
    }

    protected final void testFile(int size) throws IOException, BeanException {
        // GIVEN
        final Path filePath = this.input(size);
        final Path copyPath = this.output(filePath);
        // WHEN
        reader1.open(filePath.toFile());
        writer.open(copyPath.toFile());
        this.copy(reader1, writer);
        // THEN
        reader1.open(filePath.toFile());
        reader2.open(copyPath.toFile());
        this.assertFiles(reader1, reader2);
    }

}
