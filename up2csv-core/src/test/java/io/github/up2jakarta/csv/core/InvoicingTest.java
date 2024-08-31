package io.github.up2jakarta.csv.core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.EventHandler.SimpleHandler;
import io.github.up2jakarta.csv.entities.invoincing.InvoiceEntity;
import io.github.up2jakarta.csv.entities.invoincing.InvoiceNoteEntity;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.input.InputRepository;
import io.github.up2jakarta.csv.misc.Listable;
import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.mapper.*;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.AddressSegment;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ClientSegment;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.ComplexAddress;
import io.github.up2jakarta.csv.test.bean.mapper.oneshot.SimpleAddress;
import io.github.up2jakarta.csv.test.bean.processor.ProcessorBean;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.input.InputRowEntity;
import io.github.up2jakarta.csv.test.input.SegmentType;
import io.github.up2jakarta.csv.test.input.SimpleErrorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.github.up2jakarta.csv.core.EventHandler.failFast;
import static io.github.up2jakarta.csv.core.MapperFactory.LOGGER;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_VALIDATOR;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class InvoicingTest {

    private final MapperFactory factory;

    @Autowired
    InvoicingTest(MapperFactory factory) {
        this.factory = factory;
    }

    @Test
    void testInvoiceSegment() throws BeanException {
        // When
        final Mapper<InvoiceEntity> mapper1 = factory.build(InvoiceEntity.class);
        // Then
        assertNotNull(mapper1);
    }

    @Test
    void testInvoiceNoteSegment() throws BeanException {
        // When
        final Mapper<InvoiceNoteEntity> mapper1 = factory.build(InvoiceNoteEntity.class);
        // Then
        assertNotNull(mapper1);
    }
}
