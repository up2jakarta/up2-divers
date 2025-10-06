package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.ops.impl.InputErrorEntity;
import io.github.up2jakarta.csv.ops.impl.InputRowEntity;
import io.github.up2jakarta.csv.ops.impl.Invoice1Aggregator;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice1;
import io.github.up2jakarta.csv.test.ABusinessTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.ops.impl.SegmentType.*;
import static io.github.up2jakarta.csv.test.Tests.create;
import static io.github.up2jakarta.csv.test.Tests.fullInvoice;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class BusinessInvoice1Tests extends ABusinessTest<Invoice1, InputRowEntity, InputErrorEntity> {

    @Autowired
    BusinessInvoice1Tests(Invoice1Aggregator aggregator) {
        super(aggregator);
    }

    @Test
    void testEmpty() throws BeanException {
        testEmpty(new InputRowEntity[0]);
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S01, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        testCardinality1(S01, rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        testCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S02, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        testCardinality3(S02, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
        };
        // When & Then
        testCardinality4(S04, S01, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final InputRowEntity detached = create(S90, "9999", "Warning", "Detached");
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
                create(S04, "2299", "Hardware", "1", "600", "500", "100"),
                detached,
        };
        // When & Then
        testDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException {
        // Given
        final InputRowEntity[] rows = fullInvoice(S01);
        // When & Then
        testValid1(rows);
    }

    @Test
    void testValid2() throws BeanException {
        // Given
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
                create(S04, "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        testValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final InputRowEntity invalid = create(S90, "1199", "Support", null);
        final InputRowEntity[] rows = {
                create(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                create(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                create(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                create(S04, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };

        // When & Then
        testValidation(invalid, rows);
    }

}
