package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.misc.AFullTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy2Invoice;
import io.github.up2jakarta.csv.fmt.misc.MyError;
import io.github.up2jakarta.csv.fmt.misc.MyRecord;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.fmt.misc.Tests.fastInvoice;
import static io.github.up2jakarta.csv.impl.SegmentType.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FullDummy2Tests extends AFullTest<Dummy2Invoice, MyRecord, MyError> {

    @Autowired
    FullDummy2Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(factory.builder().full(Dummy2Invoice.class).build(S21).build(MyError::new));
    }

    private MyRecord record(SegmentType type, String... data) throws CodeListException {
        return new MyRecord(type, "TU2025R0099", data);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new MyRecord[0]);
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final MyRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S21, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final MyRecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final MyRecord[] rows = {
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
        final MyRecord[] rows = {
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
        final MyRecord detached = record(S90, "9999", "Warning", "Detached");
        final MyRecord[] rows = {
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
        final MyRecord[] rows = fastInvoice(S21);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final MyRecord[] rows = {
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
        final MyRecord invalid = record(S90, "1199", "Support", null);
        final MyRecord[] rows = {
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
