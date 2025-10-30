package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.UnitImporter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.io.misc.AUnitTests;
import io.github.up2jakarta.csv.io.misc.MyError;
import io.github.up2jakarta.csv.io.misc.MyHandler;
import io.github.up2jakarta.csv.io.misc.MyRecord;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class UnitInvoiceTests extends AUnitTests<MyRecord, UnitImporter<Invoice, GroupType, SegmentType, MyRecord, MyError>> {

    @Autowired
    UnitInvoiceTests(Up2Factory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(new UnitImporter<>(factory, Invoice.class, SegmentType.S01, SegmentType.values()) {
            @Override
            protected MyHandler create(MyRecord row) {
                return new MyHandler(row);
            }
        }, format);

    }

    @Override
    protected UnitFileWriter<Invoice> writer(UnitImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> importer, CSVFormat format) throws BeanException {
        return new UnitFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected UnitFileReader<Invoice, GroupType, SegmentType, MyRecord, MyError> reader(
            UnitImporter<Invoice, GroupType, SegmentType, MyRecord, MyError> importer, CSVFormat format
    ) {
        return new UnitFileReader<>(importer, format, "-") {
            @Override
            protected MyRecord create(SegmentType type, String[] data) {
                return new MyRecord(type, null, data);
            }
        };
    }

    @Test
    void test10() throws BeanException, IOException {
        testFile(10);
    }

    @Test
    void test100() throws BeanException, IOException {
        testFile(100);
    }

}
