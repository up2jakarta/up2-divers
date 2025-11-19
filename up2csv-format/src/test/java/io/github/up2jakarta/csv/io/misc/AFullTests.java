package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.io.FullFileReader;
import io.github.up2jakarta.csv.io.FullFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AFullTests<R extends IFullRecord<SegmentType, ?>, A extends FullImporter<GroupType, SegmentType, Invoice, R, ?>> extends Tests.AbstractTests<R> {

    private final FullFileWriter<Invoice> writer;
    private final FullFileReader<Invoice, GroupType, SegmentType, R, ?> reader1;
    private final FullFileReader<Invoice, GroupType, SegmentType, R, ?> reader2;

    protected AFullTests(A importer, CSVFormat format) throws IOException, BeanException {
        super(ModeType.FULL, format);
        this.reader1 = this.reader(importer, format);
        this.reader2 = this.reader(importer, format);
        this.writer = this.writer(importer, format);
    }

    protected abstract FullFileWriter<Invoice> writer(A importer, CSVFormat format) throws BeanException;

    protected abstract FullFileReader<Invoice, GroupType, SegmentType, R, ?> reader(A importer, CSVFormat format);

    @Override
    final void assertRecord(R data, R source) {
        assertNotNull(source.getReference());
        assertEquals(source.getPivot(), data.getPivot());
    }

    protected final void testFile(int size) throws IOException {
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
