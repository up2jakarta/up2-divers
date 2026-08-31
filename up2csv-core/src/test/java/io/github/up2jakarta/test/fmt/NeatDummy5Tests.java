package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.MessRecord;
import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.csv.data.SimpleNeatImporter;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.ANeatTest;
import io.github.up2jakarta.test.fmt.misc.Dummy5Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.test.fmt.misc.Tests.neatInvoice;
import static io.github.up2jakarta.test.impl.SegmentType.S02;
import static io.github.up2jakarta.test.impl.SegmentType.S51;
import static io.github.up2jakarta.test.impl.TermType.A002;
import static io.github.up2jakarta.test.impl.TermType.D009;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class NeatDummy5Tests extends ANeatTest<Dummy5Invoice, NeatRecord<SegmentType>, PropertyEvent<TermType, NeatRecord<SegmentType>>> {

    private final SimpleNeatImporter<Dummy5Invoice, TermType, SegmentType> snImporter;

    @Autowired
    NeatDummy5Tests(Up2Factory<TermType> factory) throws BeanException {
        super(new SimpleNeatImporter<>(factory, Dummy5Invoice.class, SegmentType.class));
        this.snImporter = this.get();
    }

    private NeatRecord<SegmentType> record(String... row) throws CodeListException {
        return snImporter.transform(row);
    }

    @Test
    void testCodeListException() throws BeanException {
        // Given
        final List<String[]> rows = Arrays.asList(
                new String[]{"RT", "TU2025R0099", null}, new String[]{"01", "TU2025R0099", null}
        );
        var pingPong = this.snImporter.toExporter().toImporter();
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> pingPong.transform(rows));
        // Then
        assertEquals(EC_CODE_LIST, error.getCode());
        assertEquals(ERROR, error.getLevel());
        assertEquals("Unknown input [RT] for CodeList[SegmentType]", error.getMessage());
    }

    @Test
    void testEmpty() {
        checkEmpty(new MessRecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final NeatRecord<SegmentType>[] rows = new NeatRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("51", null, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final NeatRecord<SegmentType>[] rows = new NeatRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final NeatRecord<SegmentType>[] rows = new NeatRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("02", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows, importer.toExporter());
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final NeatRecord<SegmentType>[] rows = new NeatRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S51, rows, importer.toExporter());
    }

    @Test
    void testDetached() {
        // Given
        final NeatRecord<SegmentType> detached = record("09", "9999", "Warning", "Detached");
        final NeatRecord<SegmentType>[] rows = new NeatRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                detached, // must be at the end
                record("04", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkDetached(detached, D009, rows);
    }

    @Test
    void testValid1() throws BeanException {
        // Given
        final NeatRecord<SegmentType>[] rows = neatInvoice(S51, NeatRecord::new);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() {
        // Given
        final NeatRecord<SegmentType>[] rows = new NeatRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
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
        final NeatRecord<SegmentType> invalid = record("09", "Support", null);
        final NeatRecord<SegmentType>[] rows = new NeatRecord[]{
                record("51", null, "2025-03-12", "120", "100", "20"),
                record("02", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, A002, rows);
    }

}
