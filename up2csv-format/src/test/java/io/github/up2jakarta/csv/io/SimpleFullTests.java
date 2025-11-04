package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.Fixed06Generator;
import io.github.up2jakarta.csv.fmt.InputRecord;
import io.github.up2jakarta.csv.fmt.SimpleFullImporter;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.io.misc.AFullTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SimpleFullTests extends AFullTests<InputRecord<SegmentType>, SimpleFullImporter<Invoice, GroupType, SegmentType>> {

    @Autowired
    SimpleFullTests(Up2Factory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(new SimpleFullImporter<>(factory, Invoice.class, SegmentType.S01, SegmentType.values()), format);
    }

    @Override
    protected FullFileWriter<Invoice> writer(SimpleFullImporter<Invoice, GroupType, SegmentType> importer, CSVFormat format) throws BeanException {
        return new FullFileWriter<>(importer.toExporter(), format, new Fixed06Generator());
    }

    @Override
    protected SimpleFullReader<Invoice, GroupType, SegmentType> reader(SimpleFullImporter<Invoice, GroupType, SegmentType> importer, CSVFormat format) {
        return new SimpleFullReader<>(importer, format);
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
