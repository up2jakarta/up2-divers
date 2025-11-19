package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.Fixed06Generator;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.io.misc.AFullTests;
import io.github.up2jakarta.csv.io.misc.InputError;
import io.github.up2jakarta.csv.io.misc.InputRecord;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.io.impl.SegmentType.S01;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class FullInvoiceTests extends AFullTests<InputRecord, FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError>> {

    @Autowired
    FullInvoiceTests(Up2Factory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(factory.builder().full(Invoice.class).build(S01).build(InputError::new, (r) -> 0), format);
    }

    @Override
    protected FullFileWriter<Invoice> writer(FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> importer, CSVFormat format) throws BeanException {
        return new FullFileWriter<>(importer.toExporter(), format, new Fixed06Generator());
    }

    @Override
    protected FullFileReader<Invoice, GroupType, SegmentType, InputRecord, InputError> reader(
            FullImporter<GroupType, SegmentType, Invoice, InputRecord, InputError> importer, CSVFormat format
    ) {
        return new FullFileReader<>(importer, format, "-") {
            @Override
            protected InputRecord create(long lineId, String recordId, SegmentType type, String beanId, String[] data) {
                return new InputRecord(recordId, type, beanId, data);
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
