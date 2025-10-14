package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.io.misc.AFastTests;
import io.github.up2jakarta.csv.io.misc.InputErrorEntity;
import io.github.up2jakarta.csv.io.misc.InputHandler;
import io.github.up2jakarta.csv.io.misc.InputRowEntity;
import io.github.up2jakarta.csv.ops.FastAggregator;
import io.github.up2jakarta.csv.ops.Fixed08Generator;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class FastInvoiceTests extends AFastTests<InputRowEntity, FastAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity>> {

    @Autowired
    FastInvoiceTests(MapperFactory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(new FastAggregator<>(factory, Invoice.class, SegmentType.S01, SegmentType.values()) {
            @Override
            protected InputHandler create(InputRowEntity row) {
                return new InputHandler(row);
            }
        }, format);
    }

    @Override
    protected FastFileWriter<Invoice> writer(FastAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator, CSVFormat format) {
        return new FastFileWriter<>(aggregator, format);
    }

    @Override
    protected FastFileReader<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> reader(
            FastAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator, CSVFormat format
    ) {
        final Fixed08Generator generator = new Fixed08Generator();
        return new FastFileReader<>(aggregator, format, "-") {
            @Override
            protected InputRowEntity create(SegmentType type, String beanId, String[] data) {
                final InputRowEntity result = new InputRowEntity();
                result.setReference(generator.get());
                result.setKey(new InputRowEntity.PKey());
                result.setType(type);
                result.setBusinessReference(beanId);
                result.setColumns(data);
                return result;
            }
        };
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
