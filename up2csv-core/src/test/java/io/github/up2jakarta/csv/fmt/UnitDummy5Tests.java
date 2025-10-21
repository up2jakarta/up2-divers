package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.hdl.MiniError;
import io.github.up2jakarta.csv.fmt.hdl.MiniRecord;
import io.github.up2jakarta.csv.fmt.misc.AUnitTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy5Invoice;
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
class UnitDummy5Tests extends AUnitTest<Dummy5Invoice, MiniRecord<SegmentType>, MiniError<GroupType, MiniRecord<SegmentType>>> {

    private final SimpleUnitImporter<Dummy5Invoice, GroupType, SegmentType> unitImporter;

    @Autowired
    UnitDummy5Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(new SimpleUnitImporter<>(factory, Dummy5Invoice.class, S51));
        this.unitImporter = this.get();
    }

    private MiniRecord<SegmentType> record(String... row) throws CodeListException {
        return unitImporter.record(row);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new MiniRecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("51", null, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check1Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("52", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("52", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("53", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("54", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S52, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("52", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("53", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S54, S51, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final MiniRecord<SegmentType> detached = record("90", "9999", "Warning", "Detached");
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("52", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("53", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("54", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final MiniRecord<SegmentType>[] rows = unitInvoice(S54);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("52", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("53", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("54", "1199", "Software", "2", "120", "100", "20"),
                record("54", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final MiniRecord<SegmentType> invalid = record("90", "1199", "Support", null);
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("52", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("53", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("54", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
