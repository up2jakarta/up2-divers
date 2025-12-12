package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.io.FastFileReader;
import io.github.up2jakarta.csv.io.FastFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.misc.AFastTests;
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
public class FastInvoiceTests extends AFastTests<TURecord, FastImporter<GroupType, SegmentType, Invoice, TURecord, TUError>> {

    @Autowired
    FastInvoiceTests(Up2Factory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(new FastImporter<>(factory, Invoice.class, SegmentType.S01) {
            @Override
            protected TUHandler.Builder newBuilder(int size) {
                return new TUHandler.Builder(size);
            }
        }, format);
    }

    @Override
    protected FastFileWriter<Invoice> writer(FastImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format) throws BeanException {
        return new FastFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected FastFileReader<Invoice, GroupType, SegmentType, TURecord, TUError> reader(
            FastImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format
    ) {
        return new FastFileReader<>(importer, format, "-") {
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
