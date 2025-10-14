package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.impl.SimpleRecord;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.*;
import io.github.up2jakarta.csv.io.misc.AFastTests;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class BusinessInvoiceTests extends AFastTests<SimpleRecord<GroupType, SegmentType>, InvoiceAggregator> {

    @Autowired
    BusinessInvoiceTests(InvoiceAggregator aggregator, CSVFormat format) throws IOException {
        super(aggregator, format);
    }

    @Override
    protected FastFileWriter<Invoice> writer(InvoiceAggregator aggregator, CSVFormat format) {
        return new InvoiceWriter(aggregator, format);
    }

    @Override
    protected FastFileReader<Invoice, GroupType, SegmentType, SimpleRecord<GroupType, SegmentType>, ?> reader(InvoiceAggregator aggregator, CSVFormat format) {
        return new InvoiceReader(aggregator, format);
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
