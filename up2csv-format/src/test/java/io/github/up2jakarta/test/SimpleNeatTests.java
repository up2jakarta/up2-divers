package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.csv.data.SimpleNeatImporter;
import io.github.up2jakarta.csv.io.NeatFileReader;
import io.github.up2jakarta.csv.io.NeatFileWriter;
import io.github.up2jakarta.csv.io.SimpleNeatReader;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.dto.Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import io.github.up2jakarta.test.misc.ANeatTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SimpleNeatTests extends ANeatTests<NeatRecord<SegmentType>, SimpleNeatImporter<Invoice, TermType, SegmentType>> {

    @Autowired
    SimpleNeatTests(Up2Factory<TermType> factory, CSVFormat format) throws IOException, BeanException {
        super(new SimpleNeatImporter<>(factory, Invoice.class, SegmentType.class), format);
    }

    @Override
    protected NeatFileWriter<Invoice> writer(SimpleNeatImporter<Invoice, TermType, SegmentType> importer, CSVFormat format) throws BeanException {
        return new NeatFileWriter<>(importer.toExporter(), format);
    }

    @Override
    protected NeatFileReader<Invoice, TermType, SegmentType, NeatRecord<SegmentType>, ?> reader(
            SimpleNeatImporter<Invoice, TermType, SegmentType> importer, CSVFormat format
    ) {
        return new SimpleNeatReader<>(importer, format);
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
