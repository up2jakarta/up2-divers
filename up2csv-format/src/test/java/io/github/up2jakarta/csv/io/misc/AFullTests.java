package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.io.FullFileReader;
import io.github.up2jakarta.csv.io.FullFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.ops.FullAggregator;
import io.github.up2jakarta.csv.ops.ModeType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AFullTests<A extends FullAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity>> extends ABusinessTest<InputRowEntity> {

    private final FullFileWriter<Invoice> writer;
    private final FullFileReader<Invoice, GroupType, SegmentType, InputFileEntity, InputRowEntity, ?> reader1;
    private final FullFileReader<Invoice, GroupType, SegmentType, InputFileEntity, InputRowEntity, ?> reader2;

    protected AFullTests(A aggregator, CSVFormat format) throws IOException {
        super(ModeType.FULL, format);
        this.reader1 = this.reader(aggregator, format);
        this.reader2 = this.reader(aggregator, format);
        this.writer = this.writer(aggregator, format);
    }

    protected abstract FullFileWriter<Invoice> writer(A aggregator, CSVFormat format);

    protected abstract FullFileReader<Invoice, GroupType, SegmentType, InputFileEntity, InputRowEntity, InputErrorEntity> reader(
            A aggregator, CSVFormat format
    );

    @Override
    final void assertRecord(InputRowEntity data, InputRowEntity source) {
        assertNotNull(source.getReference());
        assertNotNull(source.getKey());
        assertNotNull(source.getKey().getSource());
        assertNotNull(source.getKey().getRecordNumber());
        assertEquals(source.getBusinessReference(), data.getBusinessReference());
    }

    protected final void testFile(int size) throws IOException, BeanException {
        // GIVEN
        final Path filePath = this.input(size);
        final Path copyPath = this.output(filePath);
        final InputFileEntity fileSource = new InputFileEntity(filePath);
        final InputFileEntity copySource = new InputFileEntity(copyPath);
        // WHEN
        reader1.open(filePath.toFile(), fileSource);
        writer.open(copyPath.toFile());
        this.copy(reader1, writer);
        // THEN
        reader1.open(filePath.toFile(), fileSource);
        reader2.open(copyPath.toFile(), copySource);
        this.assertFiles(reader1, reader2);
    }

}
