package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.BusinessWriter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.io.BaseFileReader;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTests<R extends IRecord<SegmentType>> {

    private final TUGenerator generator;

    public AbstractTests(ModeType mode, CSVFormat format) throws IOException {
        this.generator = new TUGenerator(mode, this.getClass(), format);
    }

    private void assertExists(R data, final List<R> origin) {
        for (var it = origin.listIterator(); it.hasNext(); ) {
            final R source = it.next();
            if (source.getType() == data.getType() && source.getColumns().length == data.getColumns().length) {
                for (var i = 0; i < source.getColumns().length; i++) {
                    if (!Objects.equals(source.getColumns()[i], data.getColumns()[i])) {
                        break;
                    }
                }
                this.assertRecord(data, source);
                //assertEquals(source.getBusinessReference(), data.getBusinessReference());
                it.remove();
                return;
            }
        }
        //throw new AssertionFailedError(data.getType() + " - " + data.getBusinessReference() + ": " + Arrays.toString(data.getColumns()) + " does not exists");
    }

    final Path input(int size) throws IOException {
        return generator.generate("import_" + size + ".csv", size);
    }

    final Path output(Path input) {
        return Path.of(input.toFile().getAbsolutePath().replace("import_", "export_"));
    }

    final <BR extends BaseFileReader<Invoice, GroupType, SegmentType, R, ?>> void assertFiles(BR reader1, BR reader2) throws IOException {
        while (reader1.hasNext()) {
            assertTrue(reader2.hasNext());
            final List<R> source = reader1.next();
            assertNotNull(source);
            final List<R> target = reader2.next();
            assertNotNull(target);
            assertEquals(source.size(), target.size());
            for (final R segment : target) {
                assertExists(segment, source);
            }
        }
        reader1.close();
        reader2.close();
    }

    final void copy(BaseFileReader<Invoice, GroupType, SegmentType, R, ?> reader1, BusinessWriter<Invoice> writer) throws IOException, BeanException {
        while (reader1.hasNext()) {
            final Up2Result<Invoice, ?> item = reader1.read();
            assertEquals(0, item.getErrors().size());
            assertNotNull(item.getBean());
            writer.write(item.getBean());
        }
        reader1.close();
        writer.close();
    }

    abstract void assertRecord(R data, R origin);

}
