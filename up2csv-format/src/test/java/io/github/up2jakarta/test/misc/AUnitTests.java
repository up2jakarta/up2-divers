package io.github.up2jakarta.test.misc;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.io.UnitFileReader;
import io.github.up2jakarta.csv.io.UnitFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AUnitTests<R extends IRecord<SegmentType>, A extends UnitImporter<TermType, SegmentType, Invoice, R, ?>> extends Tests.AbstractTests<R> {

    private final UnitFileWriter<Invoice> writer;
    private final UnitFileReader<Invoice, TermType, SegmentType, R, ?> reader1;
    private final UnitFileReader<Invoice, TermType, SegmentType, R, ?> reader2;

    protected AUnitTests(A importer, CSVFormat format) throws IOException, BeanException {
        super(ModeType.UNIT, format);
        this.reader1 = this.reader(importer, format);
        this.reader2 = this.reader(importer, format);
        this.writer = this.writer(importer, format);
    }

    protected abstract UnitFileWriter<Invoice> writer(A importer, CSVFormat format) throws BeanException;

    protected abstract UnitFileReader<Invoice, TermType, SegmentType, R, ?> reader(A importer, CSVFormat format);

    @Override
    final void assertRecord(R data, R origin) {
        if (data instanceof IFastRecord<?> fd && origin instanceof IFastRecord<?> fo) {
            assertEquals(fd.getPivot(), fo.getPivot());
        }
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
