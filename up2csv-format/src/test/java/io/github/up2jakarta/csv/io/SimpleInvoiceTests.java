package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.SimpleAggregator;
import io.github.up2jakarta.csv.impl.SimpleRecord;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
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
public class SimpleInvoiceTests extends AFastTests<SimpleRecord<GroupType, SegmentType>, SimpleAggregator<Invoice, GroupType, SegmentType>> {

    @Autowired
    SimpleInvoiceTests(MapperFactory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(new SimpleAggregator<>(factory, Invoice.class, SegmentType.S01, SegmentType.values()), format);
    }

    @Override
    protected FastFileWriter<Invoice> writer(SimpleAggregator<Invoice, GroupType, SegmentType> aggregator, CSVFormat format) {
        return new SimpleWriter<>(aggregator, format);
    }

    @Override
    protected FastFileReader<Invoice, GroupType, SegmentType, SimpleRecord<GroupType, SegmentType>, ?> reader(
            SimpleAggregator<Invoice, GroupType, SegmentType> aggregator, CSVFormat format
    ) {
        return new SimpleReader<>(aggregator, format);
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
