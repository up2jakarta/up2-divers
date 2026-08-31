package io.github.up2jakarta.test;

import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.InvoiceNeatImporter;
import io.github.up2jakarta.test.impl.InvoiceNeatReader;
import io.github.up2jakarta.test.impl.InvoiceNeatWriter;
import io.github.up2jakarta.test.misc.ANeatTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.test.misc.Tests.TURecord;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BusinessNeatTests extends ANeatTests<TURecord, InvoiceNeatImporter> {

    @Autowired
    BusinessNeatTests(InvoiceNeatImporter importer, CSVFormat format) throws IOException, BeanException {
        super(importer, format);
    }

    @Override
    protected InvoiceNeatWriter writer(InvoiceNeatImporter importer, CSVFormat format) throws BeanException {
        return new InvoiceNeatWriter(importer, format);
    }

    @Override
    protected InvoiceNeatReader reader(InvoiceNeatImporter importer, CSVFormat format) {
        return new InvoiceNeatReader(importer, format);
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
