package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.io.UnitFileReader;
import io.github.up2jakarta.csv.io.UnitFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AUnitTests<R extends IRecord<SegmentType>, A extends UnitImporter<GroupType, SegmentType, Invoice, R, ?>> extends AbstractTests<R> {

    private final UnitFileWriter<Invoice> writer;
    private final UnitFileReader<Invoice, GroupType, SegmentType, R, ?> reader1;
    private final UnitFileReader<Invoice, GroupType, SegmentType, R, ?> reader2;

    protected AUnitTests(A importer, CSVFormat format) throws IOException, BeanException {
        super(ModeType.UNIT, format);
        this.reader1 = this.reader(importer, format);
        this.reader2 = this.reader(importer, format);
        this.writer = this.writer(importer, format);
    }

    protected abstract UnitFileWriter<Invoice> writer(A importer, CSVFormat format) throws BeanException;

    protected abstract UnitFileReader<Invoice, GroupType, SegmentType, R, ?> reader(A importer, CSVFormat format);

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
