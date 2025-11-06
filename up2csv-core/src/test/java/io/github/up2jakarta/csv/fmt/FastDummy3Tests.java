package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.misc.AFastTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy3Invoice;
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

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FastDummy3Tests extends AFastTest<Dummy3Invoice, FastRecord<SegmentType>, ECause<GroupType, FastRecord<SegmentType>>> {

    private final SimpleFastImporter<Dummy3Invoice, GroupType, SegmentType> fastImporter;

    @Autowired
    FastDummy3Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(new SimpleFastImporter<>(factory, Dummy3Invoice.class, S31));
        this.fastImporter = this.get();
    }

    private FastRecord<SegmentType> record(String... row) throws CodeListException {
        return fastImporter.transform(row);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new FastRecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final FastRecord<SegmentType>[] rows = new FastRecord[]{
                record("31", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("31", "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check1Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final FastRecord<SegmentType>[] rows = new FastRecord[]{
                record("31", "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final FastRecord<SegmentType>[] rows = new FastRecord[]{
                record("31", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("32", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("32", "TU2025R0099", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("33", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("34", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S32, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final FastRecord<SegmentType>[] rows = new FastRecord[]{
                record("31", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("32", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("33", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S34, S31, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final FastRecord<SegmentType> detached = record("90", "TU2025R0099", "9999", "Warning", "Detached");
        final FastRecord<SegmentType>[] rows = new FastRecord[]{
                record("31", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("32", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("33", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("34", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final FastRecord<SegmentType>[] rows = fastInvoice(S31);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final FastRecord<SegmentType>[] rows = new FastRecord[]{
                record("31", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("32", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("33", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("34", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                record("34", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final FastRecord<SegmentType> invalid = record("90", "TU2025R0099", "1199", "Support", null);
        final FastRecord<SegmentType>[] rows = new FastRecord[]{
                record("31", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("32", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("33", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("34", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
