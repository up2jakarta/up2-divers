package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BusinessWriter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.hdl.EventModeBuilder;
import io.github.up2jakarta.csv.core.hdl.PropertyCollector;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.fmt.FastRecord;
import io.github.up2jakarta.csv.io.BaseFileReader;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.lov.PropertyException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.opentest4j.AssertionFailedError;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static io.github.up2jakarta.csv.fmt.Fixed06Generator.FV_SM;
import static io.github.up2jakarta.lov.core.Codes.fixed;
import static org.junit.jupiter.api.Assertions.*;

public abstract class Tests {

    public static class TURecord extends FastRecord<SegmentType, String> {
        public TURecord(SegmentType type, String businessKey, String... data) {
            super(type, businessKey, data);
        }
    }

    public static class TUHandler extends PropertyCollector<GroupType, TURecord, TUError> {
        public TUHandler(TURecord row) {
            super(row, TUError::new);
        }

        public static final class Builder extends EventModeBuilder<GroupType, TURecord, TUError> {
            public Builder(int size) {
                super(size);
            }

            @Override
            protected TUHandler newHandler(TURecord record) {
                return new TUHandler(record);
            }
        }
    }

    public static class TUError extends PropertyEvent<GroupType, TURecord> {
        public TUError(TURecord row, Integer offset, GroupType type, PropertyException cause) {
            super(row, offset, type, cause);
        }
    }

    public static class TUGenerator {
        private static final Random RANDOM = new Random();

        private final Path path;
        private final ModeType mode;
        private final InputStream template;
        private final CSVFormat format;

        TUGenerator(ModeType mode, Class<?> tester, CSVFormat format) throws IOException {
            this.format = format;
            this.mode = mode;
            this.template = new ClassPathResource("template.csv").getInputStream();
            this.path = path(tester);
        }

        public static Path path(Class<?> tester) throws IOException {
            final Path path = Path.of(".", "target", "_io_" + tester.getSimpleName());
            if (!path.toFile().exists()) {
                Files.createDirectory(path);
            }
            return path;
        }

        Path generate(String fileName, int size) throws IOException {
            final CSVParser parser = format.parse(new InputStreamReader(template));
            final List<CSVRecord> records = new ArrayList<>(parser.stream().toList());
            final List<String[]> tmpl = records.stream().map(CSVRecord::values).toList();
            final Path csv = this.generate(tmpl, fileName, size);
            template.close();
            return csv;
        }

        private Path generate(List<String[]> segments, String fileName, int size) throws IOException {
            final Path csv = Path.of(path.toFile().getAbsolutePath(), fileName);
            final CSVPrinter writer = this.format.print(csv, StandardCharsets.UTF_8);
            // Write segments
            final String currentYear = Year.now().toString();
            final String prefix = "I" + currentYear;
            final List<String[]> data = this.clone(segments);
            for (int i = 1, ln = 0; i <= size; i++) {
                writer.printComment("Generated Invoice N°" + i);
                final String randomInt = String.valueOf(RANDOM.nextInt(999_999) + 75_000_001);
                final String invoiceNumber = prefix + fixed(i + 1).toUpperCase();
                for (var j = 0; j < segments.size(); j++) {
                    final String[] record = data.get(j);
                    this.fill(segments.get(j), record, invoiceNumber, currentYear, randomInt, ++ln);
                    writer.printRecord((Object[]) record);
                }
            }
            //close the writer
            writer.close();
            return csv;
        }

        private void fill(String[] tmpl, String[] data, String invoiceNumber, String year, String randomInt, int ln) {
            final int s, p;
            if (mode == ModeType.FULL) {
                data[0] = fixed(FV_SM + ln);
                p = s = 1;
            } else {
                if (mode == ModeType.UNIT && !"01".equals(tmpl[0])) {
                    data[0] = tmpl[0];
                    s = 2;
                    p = -1;
                } else {
                    s = 1;
                    p = 0;
                }
            }
            for (var i = s; i < tmpl.length; i++) {
                data[i + p] = this.replace(tmpl[i], invoiceNumber, year, randomInt);
            }
        }

        private String replace(String value, String invoiceNumber, String year, String randomInt) {
            if (value == null) {
                return null;
            }
            return value.replace("${in}", invoiceNumber)
                    .replace("${ri}", randomInt)
                    .replace("${cy}", year);
        }

        private List<String[]> clone(List<String[]> tmpl) {
            final int p = (mode == ModeType.FULL) ? 1 : 0;
            final List<String[]> data = new ArrayList<>(tmpl.size());
            for (final String[] segment : tmpl) {
                final int s = (mode == ModeType.UNIT && !"01".equals(segment[0])) ? 1 : 0;
                final String[] copy = new String[segment.length + p - s];
                copy[p] = segment[0];
                data.add(copy);
            }
            return data;
        }

    }

    abstract static class AbstractTests<R extends IRecord<SegmentType>> {
        private final TUGenerator generator;

        AbstractTests(ModeType mode, CSVFormat format) throws IOException {
            this.generator = new TUGenerator(mode, this.getClass(), format);
        }

        private void assertExists(R data, final List<R> origin) {
            for (var it = origin.listIterator(); it.hasNext(); ) {
                final R source = it.next();
                if (source.getType() == data.getType() && source.getData().length == data.getData().length) {
                    for (var i = 0; i < source.getData().length; i++) {
                        if (!Objects.equals(source.getData()[i], data.getData()[i])) {
                            break;
                        }
                    }
                    this.assertRecord(data, source);
                    it.remove();
                    return;
                }
            }
            throw new AssertionFailedError(data + "\tdoes not exists");
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

        final void copy(BaseFileReader<Invoice, GroupType, SegmentType, R, ?> reader1, BusinessWriter<Invoice> writer) throws IOException {
            while (reader1.hasNext()) {
                final Up2Result<Invoice, ?> item = reader1.read();
                assertEquals(0, item.toList().size());
                assertNotNull(item.get());
                writer.write(item.get());
            }
            reader1.close();
            writer.close();
        }

        abstract void assertRecord(R data, R origin);
    }
}
