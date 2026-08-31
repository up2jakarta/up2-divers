package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.IMode;
import io.github.up2jakarta.csv.core.ModeType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
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

import static io.github.up2jakarta.csv.data.Fixed06Generator.FV_SM;
import static io.github.up2jakarta.lov.core.Codes.fixed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration
public abstract class AbstractJobITest {

    public static final String INPUT_FILE = "input-file";
    public static final String OUTPUT_FILE = "output-file";
    public static final String ERR_FILE = "error-file";

    private final TUGenerator generator;

    @Autowired
    protected JobLauncherTestUtils launcher;

    protected AbstractJobITest(IMode mode, CSVFormat format) throws IOException {
        this.generator = new TUGenerator(mode, this.getClass(), format);
    }

    protected static void assertSteps(JobExecution job) {
        assertEquals(1, job.getStepExecutions().size());
        job.getStepExecutions().forEach(step -> {
            assertEquals(BatchStatus.COMPLETED, step.getStatus());
            assertEquals(ExitStatus.COMPLETED, step.getExitStatus());
            assertEquals(0, step.getFailureExceptions().size());
        });
    }

    protected static void assertFile(String path) {
        final File file = new File(path);
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.length() > 0);
    }

    protected static void assertStatus(JobExecution job) {
        assertEquals(BatchStatus.COMPLETED, job.getStatus());
        assertEquals(ExitStatus.COMPLETED, job.getExitStatus());
        assertEquals(0, job.getAllFailureExceptions().size());
    }

    private String file(File inputFile, String target) {
        return inputFile.getAbsolutePath().replace("import_", target + "_");
    }

    protected final JobParameters input(int size) throws IOException {
        final File input = generator.generate("import_" + size + ".csv", size).toFile();
        return new JobParametersBuilder()
                .addString(INPUT_FILE, input.getAbsolutePath())
                .addString(OUTPUT_FILE, this.file(input, "export"))
                .addString(ERR_FILE, this.file(input, "error"))
                .toJobParameters();
    }

    private static class TUGenerator {

        private static final Random RANDOM = new Random();

        private final Path path;
        private final IMode mode;
        private final CSVFormat format;
        private final InputStream template;

        TUGenerator(IMode mode, Class<?> tester, CSVFormat format) throws IOException {
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
            final List<String[]> tmpl = records.stream()
                    .map(r -> {
                        final String[] tpl = r.values();
                        if (mode == ModeType.NEAT && "09".equals(tpl[0])) {
                            final String[] rs = new String[tpl.length - 1];
                            rs[0] = tpl[0];
                            rs[1] = tpl[1];
                            rs[2] = tpl[3];
                            rs[3] = tpl[4];
                            return rs;
                        }
                        return tpl;
                    })
                    .toList();
            final Path csv = this.generate(tmpl, fileName, size);
            template.close();
            TUConfiguration.LOG.info("Input file with ({}) invoices -> {}", size, csv);
            return csv;
        }

        private Path generate(List<String[]> segments, String fileName, int size) throws IOException {
            final Path csv = path.resolve(fileName);
            final CSVPrinter writer = this.format.print(csv, StandardCharsets.UTF_8);
            // Write segments
            final String currentYear = Year.now().toString();
            final String prefix = "I" + currentYear;
            final List<String[]> data = this.clone(segments);
            var ces = 0;
            var cen = 0;
            for (int i = 1, ln = 0; i <= size; i++) {
                writer.printComment("Generated Invoice N°" + i);
                final String randomInt = String.valueOf(RANDOM.nextInt(999_999) + 75_000_001);
                final String invoiceNumber = prefix + fixed(i + 1).toUpperCase();
                for (var j = 0; j < segments.size(); j++) {
                    final String[] segment = data.get(j);
                    var dn = this.fill(segments.get(j), segment, invoiceNumber, currentYear, randomInt, ++ln);
                    // Error simulation ->
                    if (dn) {
                        cen++;
                        writer.printRecord((Object[]) segment);
                    } else {
                        final int rs = RANDOM.nextInt(size * 5);
                        if (i != rs) {
                            writer.printRecord((Object[]) segment);
                        } else {
                            ces++;
                        }
                    }
                    // <- Error simulation
                }
            }
            if (ces != 0) {
                TUConfiguration.LOG.info("Error simulation - {} segments have been deleted", ces);
            }
            if (cen != 0) {
                TUConfiguration.LOG.info("Error simulation - {} segments have been tampered with null values", cen);
            }
            //close the writer
            writer.close();
            return csv;
        }

        private boolean fill(String[] tmpl, String[] data, String invoiceNumber, String year, String randomInt, int ln) {
            final int s, p;
            if (mode == IMode.FULL) {
                data[0] = fixed(FV_SM + ln);
                p = s = 1;
            } else {
                if (mode == ModeType.NEAT && !"01".equals(tmpl[0])) {
                    data[0] = tmpl[0];
                    s = 2;
                    p = -1;
                } else {
                    s = 1;
                    p = 0;
                }
            }
            var count = 0;
            final int rs = RANDOM.nextInt(tmpl.length * 100) + mode.getLength() + 1;
            for (var i = s; i < tmpl.length; i++) {
                data[i + p] = this.replace(tmpl[i], invoiceNumber, year, randomInt);
                // Error simulation ->
                if (rs == i + p) {
                    count++;
                    data[rs] = null;
                }
                // <- Error simulation
            }
            return count != 0;
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
            final int p = (mode == IMode.FULL) ? 1 : 0;
            final List<String[]> data = new ArrayList<>(tmpl.size());
            for (final String[] segment : tmpl) {
                final int s = (mode == ModeType.NEAT && !"01".equals(segment[0])) ? 1 : 0;
                final String[] copy = new String[segment.length + p - s];
                copy[p] = segment[0];
                data.add(copy);
            }
            return data;
        }

    }

}
