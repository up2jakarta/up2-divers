package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.io.misc.AUnitTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.io.misc.Tests.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class UnitInvoiceTests extends AUnitTests<TURecord, UnitImporter<GroupType, SegmentType, Invoice, TURecord, TUError>> {

    @Autowired
    UnitInvoiceTests(Up2Factory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(new UnitImporter<>(factory, Invoice.class, SegmentType.S01, SegmentType.values()) {
            @Override
            protected TUHandler create(TURecord row) {
                return new TUHandler(row);
            }
        }, format);

    }

    @Override
    protected UnitFileWriter<Invoice> writer(UnitImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format) throws BeanException {
        return new UnitFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected UnitFileReader<Invoice, GroupType, SegmentType, TURecord, TUError> reader(
            UnitImporter<GroupType, SegmentType, Invoice, TURecord, TUError> importer, CSVFormat format
    ) {
        return new UnitFileReader<>(importer, format, "-") {
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
