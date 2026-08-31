package io.github.up2jakarta.test.misc;

import io.github.up2jakarta.csv.api.IMessRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.io.NeatFileReader;
import io.github.up2jakarta.csv.io.NeatFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ANeatTests<R extends IRecord<SegmentType>, A extends NeatImporter<TermType, SegmentType, Invoice, R, ?>> extends Tests.AbstractTests<R> {

    private final NeatFileWriter<Invoice> writer;
    private final NeatFileReader<Invoice, TermType, SegmentType, R, ?> reader1;
    private final NeatFileReader<Invoice, TermType, SegmentType, R, ?> reader2;

    protected ANeatTests(A importer, CSVFormat format) throws IOException, BeanException {
        super(ModeType.NEAT, format);
        this.reader1 = this.reader(importer, format);
        this.reader2 = this.reader(importer, format);
        this.writer = this.writer(importer, format);
    }

    protected abstract NeatFileWriter<Invoice> writer(A importer, CSVFormat format) throws BeanException;

    protected abstract NeatFileReader<Invoice, TermType, SegmentType, R, ?> reader(A importer, CSVFormat format);

    @Override
    final void assertRecord(R data, R origin) {
        if (data instanceof IMessRecord<?> fd && origin instanceof IMessRecord<?> fo) {
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
