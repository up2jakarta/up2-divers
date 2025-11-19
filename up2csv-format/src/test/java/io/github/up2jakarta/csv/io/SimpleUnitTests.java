package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.SimpleUnitImporter;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.io.misc.AUnitTests;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SimpleUnitTests extends AUnitTests<UnitRecord<SegmentType>, SimpleUnitImporter<Invoice, GroupType, SegmentType>> {

    @Autowired
    SimpleUnitTests(Up2Factory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(new SimpleUnitImporter<>(factory, Invoice.class, SegmentType.S01), format);
    }

    @Override
    protected UnitFileWriter<Invoice> writer(SimpleUnitImporter<Invoice, GroupType, SegmentType> importer, CSVFormat format) throws BeanException {
        return new UnitFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected UnitFileReader<Invoice, GroupType, SegmentType, UnitRecord<SegmentType>, ?> reader(
            SimpleUnitImporter<Invoice, GroupType, SegmentType> importer, CSVFormat format
    ) {
        return new SimpleUnitReader<>(importer, format);
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
