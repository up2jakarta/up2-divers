package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.fmt.misc.AUnitTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy4Invoice;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.fmt.misc.Tests.unitInvoice;
import static io.github.up2jakarta.csv.impl.SegmentType.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class UnitDummy4Tests extends AUnitTest<Dummy4Invoice, UnitRecord<SegmentType>, PropertyEvent<GroupType, UnitRecord<SegmentType>>> {

    private final SimpleUnitImporter<Dummy4Invoice, GroupType, SegmentType> unitImporter;

    @Autowired
    UnitDummy4Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(new SimpleUnitImporter<>(factory, Dummy4Invoice.class, S41));
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
                record("42", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("42", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("43", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("44", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S42, rows);
    }

    @Test
    void testCardinality4() {
        // Given
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("42", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("43", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S44, S41, rows);
    }

    @Test
    void testDetached() {
        // Given
        final UnitRecord<SegmentType> detached = record("90", "9999", "Warning", "Detached");
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("42", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("43", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("44", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final UnitRecord<SegmentType>[] rows = unitInvoice(S44, UnitRecord::new);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("42", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("43", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("44", "1199", "Software", "2", "120", "100", "20"),
                record("44", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() {
        // Given
        final UnitRecord<SegmentType> invalid = record("90", "1199", "Support", null);
        final UnitRecord<SegmentType>[] rows = new UnitRecord[]{
                record("41", null, "2025-03-12", "120", "100", "20"),
                record("42", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("43", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("44", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
