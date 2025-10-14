package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.io.dto.Invoice;
import io.github.up2jakarta.csv.io.impl.GroupType;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.csv.io.misc.*;
import io.github.up2jakarta.csv.ops.Fixed08Generator;
import io.github.up2jakarta.csv.ops.FullAggregator;
import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.io.impl.SegmentType.S01;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class FullInvoiceTests extends AFullTests<FullAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity>> {

    @Autowired
    FullInvoiceTests(MapperFactory<GroupType> factory, CSVFormat format) throws IOException, BeanException {
        super(factory.builder().full(Invoice.class).full(S01).build(InputHandler::create, (r) -> 0), format);
    }

    @Override
    protected FullFileWriter<Invoice> writer(FullAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator, CSVFormat format) {
        return new FullFileWriter<>(aggregator, format, new Fixed08Generator());
    }

    @Override
    protected FullFileReader<Invoice, GroupType, SegmentType, InputFileEntity, InputRowEntity, InputErrorEntity> reader(
            FullAggregator<Invoice, GroupType, SegmentType, InputRowEntity, InputErrorEntity> aggregator, CSVFormat format
    ) {
        return new FullFileReader<>(aggregator, format, "-") {
            @Override
            protected InputRowEntity create(String recordId, SegmentType type, String beanId, String[] data) {
                final InputRowEntity result = new InputRowEntity();
                result.setReference(recordId);
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
