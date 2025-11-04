package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.core.ModeType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
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
import java.util.Random;

import static io.github.up2jakarta.csv.fmt.Fixed06Generator.FV_SM;
import static io.github.up2jakarta.xml.adapters.KeyCoder.fixed;

public class TUGenerator {

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
        var lineNumber = 0;
        writer.printRecord('-');
        final String currentYear = Year.now().toString();
        final String prefix = "I" + currentYear;
        final List<String[]> data = this.clone(segments);
        for (var i = 0; i < size; i++) {
            final String randomInt = String.valueOf(RANDOM.nextInt(999_999) + 75_000_001);
            final String invoiceNumber = prefix + fixed(i + 1).toUpperCase();
            for (var j = 0; j < segments.size(); j++) {
                final String[] record = data.get(j);
                this.fill(segments.get(j), record, invoiceNumber, currentYear, randomInt, ++lineNumber);
                writer.printRecord((Object[]) record);
            }
            writer.printRecord('-');
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
