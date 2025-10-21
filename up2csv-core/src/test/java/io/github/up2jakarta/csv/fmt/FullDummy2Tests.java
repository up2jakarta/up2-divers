package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.hdl.PathError;
import io.github.up2jakarta.csv.fmt.hdl.PathRecord;
import io.github.up2jakarta.csv.fmt.misc.AFullTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy2Invoice;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.SegmentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.core.ModeType.FULL;
import static io.github.up2jakarta.csv.fmt.misc.Tests.invoice;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static io.github.up2jakarta.csv.impl.SegmentType.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FullDummy2Tests extends AFullTest<Dummy2Invoice, PathRecord<SegmentType>, PathError<GroupType, PathRecord<SegmentType>>> {

    @Autowired
    FullDummy2Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(new SimpleFullImporter<>(factory, Dummy2Invoice.class, S21));
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new InputRecord[0]);
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S21, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S22, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S22, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S23, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S24, "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S22, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S22, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S23, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S24, S21, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final InputRecord detached = record(S90, "9999", "Warning", "Detached");
        final InputRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S22, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S23, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S24, "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final InputRecord[] rows = invoice(S21, FULL);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final InputRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S22, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S23, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S24, "1199", "Software", "2", "120", "100", "20"),
                record(S24, "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final InputRecord invalid = record(S90, "1199", "Support", null);
        final InputRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S22, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S23, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S24, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
