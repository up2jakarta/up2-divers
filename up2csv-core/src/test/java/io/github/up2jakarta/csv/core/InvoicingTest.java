package io.github.up2jakarta.csv.core;


import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.entities.invoincing.InvoiceEntity;
import io.github.up2jakarta.csv.entities.invoincing.InvoiceNoteEntity;
import io.github.up2jakarta.csv.exception.BeanException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
