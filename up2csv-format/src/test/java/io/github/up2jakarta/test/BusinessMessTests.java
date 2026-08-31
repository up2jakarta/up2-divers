package io.github.up2jakarta.test;

import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.impl.InvoiceMessImporter;
import io.github.up2jakarta.test.impl.InvoiceMessReader;
import io.github.up2jakarta.test.impl.InvoiceMessWriter;
import io.github.up2jakarta.test.misc.AMessTests;
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
class BusinessMessTests extends AMessTests<TURecord, InvoiceMessImporter> {

    @Autowired
    BusinessMessTests(InvoiceMessImporter importer, CSVFormat format) throws IOException, BeanException {
        super(importer, format);
    }

    @Override
    protected InvoiceMessWriter writer(InvoiceMessImporter importer, CSVFormat format) throws BeanException {
        return new InvoiceMessWriter(importer, format);
    }

    @Override
    protected InvoiceMessReader reader(InvoiceMessImporter importer, CSVFormat format) {
        return new InvoiceMessReader(importer, format);
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
