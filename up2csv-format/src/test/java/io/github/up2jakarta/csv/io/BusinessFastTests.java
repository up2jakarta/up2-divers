package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.io.impl.InvoiceFastImporter;
import io.github.up2jakarta.csv.io.impl.InvoiceFastReader;
import io.github.up2jakarta.csv.io.impl.InvoiceFastWriter;
import io.github.up2jakarta.csv.io.misc.AFastTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.io.misc.Tests.TURecord;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BusinessFastTests extends AFastTests<TURecord, InvoiceFastImporter> {

    @Autowired
    BusinessFastTests(InvoiceFastImporter importer, CSVFormat format) throws IOException, BeanException {
        super(importer, format);
    }

    @Override
    protected InvoiceFastWriter writer(InvoiceFastImporter importer, CSVFormat format) throws BeanException {
        return new InvoiceFastWriter(importer, format);
    }

    @Override
    protected InvoiceFastReader reader(InvoiceFastImporter importer, CSVFormat format) {
        return new InvoiceFastReader(importer, format);
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
