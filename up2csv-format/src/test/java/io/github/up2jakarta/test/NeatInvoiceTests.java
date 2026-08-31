package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.io.NeatFileReader;
import io.github.up2jakarta.csv.io.NeatFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.misc.ANeatTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.test.misc.Tests.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class NeatInvoiceTests extends ANeatTests<TURecord, NeatImporter<TermType, SegmentType, Invoice, TURecord, TUError>> {

    @Autowired
    NeatInvoiceTests(Up2Factory<TermType> factory, CSVFormat format) throws IOException, BeanException {
        super(new NeatImporter<>(factory, Invoice.class, SegmentType.class) {
            @Override
            protected TUHandler.Builder newBuilder(int size) {
                return new TUHandler.Builder(size);
            }
        }, format);

    }

    @Override
    protected NeatFileWriter<Invoice> writer(NeatImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format) throws BeanException {
        return new NeatFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected NeatFileReader<Invoice, TermType, SegmentType, TURecord, TUError> reader(
            NeatImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format
    ) {
        return new NeatFileReader<>(importer, format, "-") {
            @Override
            protected TURecord create(SegmentType type, String[] data) {
                return new TURecord(type, null, data);
            }
        };
    }

    @Test
    void test10() throws IOException {
        testFile(10);
    }

    @Test
    void test100() throws IOException {
        testFile(100);
    }

}
