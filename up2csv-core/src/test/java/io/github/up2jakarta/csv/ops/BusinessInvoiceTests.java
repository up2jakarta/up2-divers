package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.impl.InputErrorEntity;
import io.github.up2jakarta.csv.impl.InputRowEntity;
import io.github.up2jakarta.csv.impl.InvoiceAggregator;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.ops.misc.AFastTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.csv.ops.misc.Tests.fullInvoice;
import static io.github.up2jakarta.csv.ops.misc.Tests.record;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class BusinessInvoiceTests extends AFastTest<Invoice, InputRowEntity, InputErrorEntity> {

    @Autowired
    BusinessInvoiceTests(InvoiceAggregator aggregator) {
        super(aggregator);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new InputRowEntity[0]);
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S01, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(S01, rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S02, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S04, S01, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final InputRowEntity detached = record(S90, "9999", "Warning", "Detached");
        final InputRowEntity[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                record(S04, "2299", "Hardware", "1", "600", "500", "100"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final InputRowEntity[] rows = fullInvoice(S01);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final InputRowEntity[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                record(S04, "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final InputRowEntity invalid = record(S90, "1199", "Support", null);
        final InputRowEntity[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };

        // When & Then
        checkValidation(invalid, rows);
    }

}
