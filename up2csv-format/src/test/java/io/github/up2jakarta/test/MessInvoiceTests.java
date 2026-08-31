package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.io.MessFileReader;
import io.github.up2jakarta.csv.io.MessFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.misc.AMessTests;
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
public class MessInvoiceTests extends AMessTests<TURecord, MessImporter<TermType, SegmentType, Invoice, TURecord, TUError>> {

    @Autowired
    MessInvoiceTests(Up2Factory<TermType> factory, CSVFormat format) throws IOException, BeanException {
        super(new MessImporter<>(factory, Invoice.class, SegmentType.class) {
            @Override
            protected TUHandler.Builder newBuilder(int size) {
                return new TUHandler.Builder(size);
            }
        }, format);
    }

    @Override
    protected MessFileWriter<Invoice> writer(MessImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format) throws BeanException {
        return new MessFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected MessFileReader<Invoice, TermType, SegmentType, TURecord, TUError> reader(
            MessImporter<TermType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format
    ) {
        return new MessFileReader<>(importer, format, "-") {
            @Override
            protected TURecord create(SegmentType type, String invoiceNumber, String[] data) {
                return new TURecord(type, invoiceNumber, data);
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
