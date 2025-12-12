package io.github.up2jakarta.test;

import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.InvoiceFullImporter;
import io.github.up2jakarta.test.impl.InvoiceFullReader;
import io.github.up2jakarta.test.impl.InvoiceFullWriter;
import io.github.up2jakarta.test.misc.AFullTests;
import io.github.up2jakarta.test.misc.InputRecord;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BusinessFullTests extends AFullTests<InputRecord, InvoiceFullImporter> {

    @Autowired
    BusinessFullTests(InvoiceFullImporter importer, CSVFormat format) throws IOException, BeanException {
        super(importer, format);
    }

    @Override
    protected InvoiceFullWriter writer(InvoiceFullImporter importer, CSVFormat format) throws BeanException {
        return new InvoiceFullWriter(importer, format);
    }

    @Override
    protected InvoiceFullReader reader(InvoiceFullImporter importer, CSVFormat format) {
        return new InvoiceFullReader(importer, format);
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
