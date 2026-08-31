package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.MessRecord;
import io.github.up2jakarta.csv.data.SimpleMessImporter;
import io.github.up2jakarta.csv.io.MessFileReader;
import io.github.up2jakarta.csv.io.MessFileWriter;
import io.github.up2jakarta.csv.io.SimpleMessReader;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SimpleMessTests extends AMessTests<MessRecord<SegmentType>, SimpleMessImporter<Invoice, TermType, SegmentType>> {

    @Autowired
    SimpleMessTests(Up2Factory<TermType> factory, CSVFormat format) throws IOException, BeanException {
        super(new SimpleMessImporter<>(factory, Invoice.class, SegmentType.class), format);
    }

    @Override
    protected MessFileWriter<Invoice> writer(SimpleMessImporter<Invoice, TermType, SegmentType> importer, CSVFormat format) throws BeanException {
        return new MessFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected MessFileReader<Invoice, TermType, SegmentType, MessRecord<SegmentType>, ?> reader(
            SimpleMessImporter<Invoice, TermType, SegmentType> importer, CSVFormat format
    ) {
        return new SimpleMessReader<>(importer, format);
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
