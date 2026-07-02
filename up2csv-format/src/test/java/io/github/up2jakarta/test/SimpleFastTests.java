package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.FastRecord;
import io.github.up2jakarta.csv.fmt.SimpleFastImporter;
import io.github.up2jakarta.csv.io.FastFileReader;
import io.github.up2jakarta.csv.io.FastFileWriter;
import io.github.up2jakarta.csv.io.SimpleFastReader;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.misc.AFastTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SimpleFastTests extends AFastTests<FastRecord<SegmentType>, SimpleFastImporter<Invoice, TermType, SegmentType>> {

    @Autowired
    SimpleFastTests(Up2Factory<TermType> factory, CSVFormat format) throws IOException, BeanException {
        super(new SimpleFastImporter<>(factory, Invoice.class, SegmentType.class), format);
    }

    @Override
    protected FastFileWriter<Invoice> writer(SimpleFastImporter<Invoice, TermType, SegmentType> importer, CSVFormat format) throws BeanException {
        return new FastFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected FastFileReader<Invoice, TermType, SegmentType, FastRecord<SegmentType>, ?> reader(
            SimpleFastImporter<Invoice, TermType, SegmentType> importer, CSVFormat format
    ) {
        return new SimpleFastReader<>(importer, format);
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
