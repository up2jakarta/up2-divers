package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.io.impl.InvoiceUnitImporter;
import io.github.up2jakarta.csv.io.impl.InvoiceUnitReader;
import io.github.up2jakarta.csv.io.impl.InvoiceUnitWriter;
import io.github.up2jakarta.csv.io.misc.AUnitTests;
import io.github.up2jakarta.csv.io.misc.MyRecord;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BusinessUnitTests extends AUnitTests<MyRecord, InvoiceUnitImporter> {

    @Autowired
    BusinessUnitTests(InvoiceUnitImporter importer, CSVFormat format) throws IOException, BeanException {
        super(importer, format);
    }

    @Override
    protected InvoiceUnitWriter writer(InvoiceUnitImporter importer, CSVFormat format) throws BeanException {
        return new InvoiceUnitWriter(importer, format);
    }

    @Override
    protected InvoiceUnitReader reader(InvoiceUnitImporter importer, CSVFormat format) {
        return new InvoiceUnitReader(importer, format);
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
