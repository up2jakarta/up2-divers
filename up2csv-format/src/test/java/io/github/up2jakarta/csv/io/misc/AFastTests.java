package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.io.FastFileReader;
import io.github.up2jakarta.csv.io.FastFileWriter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AFastTests<R extends IFastRecord<SegmentType>, A extends FastImporter<GroupType, SegmentType, Invoice, R, ?>> extends AbstractTests<R> {

    private final FastFileWriter<Invoice> writer;
    private final FastFileReader<Invoice, GroupType, SegmentType, R, ?> reader1;
    private final FastFileReader<Invoice, GroupType, SegmentType, R, ?> reader2;

    protected AFastTests(A importer, CSVFormat format) throws IOException, BeanException {
        super(ModeType.FAST, format);
        this.reader1 = this.reader(importer, format);
        this.reader2 = this.reader(importer, format);
        this.writer = this.writer(importer, format);
    }

    protected abstract FastFileWriter<Invoice> writer(A importer, CSVFormat format) throws BeanException;

    protected abstract FastFileReader<Invoice, GroupType, SegmentType, R, ?> reader(A importer, CSVFormat format);

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
