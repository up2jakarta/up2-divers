package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.fmt.FullImporter;
import io.github.up2jakarta.csv.fmt.hdl.PathRecord;
import io.github.up2jakarta.csv.fmt.hdl.PathSource;
import io.github.up2jakarta.csv.io.FullFileReader;
import io.github.up2jakarta.csv.io.FullFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AFullTests<R extends PathRecord<SegmentType>, A extends FullImporter<Invoice, GroupType, SegmentType, R, ?>> extends ABusinessTest<R> {

    private final FullFileWriter<Invoice> writer;
    private final FullFileReader<Invoice, GroupType, SegmentType, PathSource, R, ?> reader1;
    private final FullFileReader<Invoice, GroupType, SegmentType, PathSource, R, ?> reader2;

    protected AFullTests(A importer, CSVFormat format) throws IOException, BeanException {
        super(ModeType.FULL, format);
        this.reader1 = this.reader(importer, format);
        this.reader2 = this.reader(importer, format);
        this.writer = this.writer(importer, format);
    }

    protected abstract FullFileWriter<Invoice> writer(A importer, CSVFormat format) throws BeanException;

    protected abstract FullFileReader<Invoice, GroupType, SegmentType, PathSource, R, ?> reader(A importer, CSVFormat format);

    @Override
    final void assertRecord(R data, R source) {
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
        final PathSource fileSource = new PathSource("TU", filePath);
        final PathSource copySource = new PathSource("TU", copyPath);
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
