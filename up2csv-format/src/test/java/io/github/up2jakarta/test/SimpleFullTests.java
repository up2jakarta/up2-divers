package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.Fixed06Generator;
import io.github.up2jakarta.csv.data.FullRecord;
import io.github.up2jakarta.csv.data.SimpleFullImporter;
import io.github.up2jakarta.csv.io.FullFileWriter;
import io.github.up2jakarta.csv.io.SimpleFullReader;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.misc.AFullTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SimpleFullTests extends AFullTests<FullRecord<SegmentType>, SimpleFullImporter<Invoice, TermType, SegmentType>> {

    @Autowired
    SimpleFullTests(Up2Factory<TermType> factory, CSVFormat format) throws IOException, BeanException {
        super(new SimpleFullImporter<>(factory, Invoice.class, SegmentType.class), format);
    }

    @Override
    protected FullFileWriter<Invoice> writer(SimpleFullImporter<Invoice, TermType, SegmentType> importer, CSVFormat format) throws BeanException {
        return new FullFileWriter<>(importer.toExporter(), format, new Fixed06Generator());
    }

    @Override
    protected SimpleFullReader<Invoice, TermType, SegmentType> reader(SimpleFullImporter<Invoice, TermType, SegmentType> importer, CSVFormat format) {
        return new SimpleFullReader<>(importer, format);
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
