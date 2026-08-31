package io.github.up2jakarta.test.misc;

import io.github.up2jakarta.csv.api.IMessRecord;
import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.io.MessFileReader;
import io.github.up2jakarta.csv.io.MessFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AMessTests<R extends IMessRecord<SegmentType>, A extends MessImporter<TermType, SegmentType, Invoice, R, ?>> extends Tests.AbstractTests<R> {

    private final MessFileWriter<Invoice> writer;
    private final MessFileReader<Invoice, TermType, SegmentType, R, ?> reader1;
    private final MessFileReader<Invoice, TermType, SegmentType, R, ?> reader2;

    protected AMessTests(A importer, CSVFormat format) throws IOException, BeanException {
        super(ModeType.MESS, format);
        this.reader1 = this.reader(importer, format);
        this.reader2 = this.reader(importer, format);
        this.writer = this.writer(importer, format);
    }

    protected abstract MessFileWriter<Invoice> writer(A importer, CSVFormat format) throws BeanException;

    protected abstract MessFileReader<Invoice, TermType, SegmentType, R, ?> reader(A importer, CSVFormat format);

    @Override
    final void assertRecord(R data, R origin) {
        assertEquals(data.getPivot(), data.getPivot());
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
