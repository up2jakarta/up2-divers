package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.fmt.FastRecord;
import io.github.up2jakarta.csv.fmt.SimpleUnitImporter;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AUnitTest;
import io.github.up2jakarta.test.fmt.misc.Dummy4Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.test.fmt.misc.Tests.unitInvoice;
import static io.github.up2jakarta.test.impl.SegmentType.S02;
import static io.github.up2jakarta.test.impl.SegmentType.S41;
import static io.github.up2jakarta.test.impl.TermType.A002;
import static io.github.up2jakarta.test.impl.TermType.D009;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class UnitDummy4Tests extends AUnitTest<Dummy4Invoice, UnitRecord<SegmentType>, PropertyEvent<TermType, UnitRecord<SegmentType>>> {

    private final SimpleUnitImporter<Dummy4Invoice, TermType, SegmentType> unitImporter;

    @Autowired
    UnitDummy4Tests(Up2Factory<TermType> factory) throws BeanException {
        super(new SimpleUnitImporter<>(factory, Dummy4Invoice.class, SegmentType.class));
        this.unitImporter = this.get();
    }

    private UnitRecord<SegmentType> record(String... row) throws CodeListException {
        return unitImporter.transform(row);
    }

    @Test
    void testEmpty() {
        checkEmpty(new FastRecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("41", null, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() {
        // Given
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("02", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows);
    }

    @Test
    void testCardinality4() {
        // Given
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S41, rows);
    }

    @Test
    void testDetached() {
        // Given
        final UnitRecord<SegmentType> detached = record("09", "9999", "Warning", "Detached");
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, D009, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final UnitRecord<SegmentType>[] rows = unitInvoice(S41, UnitRecord::new);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "1199", "Software", "2", "120", "100", "20"),
                record("04", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() {
        // Given
        final UnitRecord<SegmentType> invalid = record("09", "1199", "Support", null);
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, A002, rows);
    }

}
