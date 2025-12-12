package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.SimpleUnitImporter;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.csv.io.SimpleUnitReader;
import io.github.up2jakarta.csv.io.UnitFileReader;
import io.github.up2jakarta.csv.io.UnitFileWriter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.misc.AUnitTests;
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
