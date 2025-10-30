package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.misc.AUnitTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy1Invoice;
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
import java.util.Arrays;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_CODE_LIST;
import static io.github.up2jakarta.csv.fmt.misc.Tests.fastInvoice;
import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class UnitDummy1Tests extends AUnitTest<Dummy1Invoice, MyRecord, MyError> {

    private final UnitImporter<Dummy1Invoice, GroupType, SegmentType, MyRecord, MyError> unitImporter;

    @Autowired
    UnitDummy1Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(factory.builder().unit(Dummy1Invoice.class).build(S11).build(MyError::new));
        this.unitImporter = this.get();
    }

    public MyRecord record(String code, String... data) throws CodeListException {
        final SegmentType type = SegmentType.valueOf('S' + code);
        return new MyRecord(type, "TU2025R0099", data);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new MyRecord[0]);
    }

    @Test
    void testCodeListException() throws BeanException {
        // Given
        final List<String[]> rows = Arrays.asList(
                new String[]{"RT", "TU2025R0099", null}, new String[]{"01", "TU2025R0099", null}
        );
        var pingPong = this.unitImporter.toExporter().toImporter();
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> pingPong.parse(rows));
        // Then
        assertEquals(ERROR_CODE_LIST, error.getCode());
        assertEquals(ERROR, error.getSeverity());
        assertEquals("Unknown value [RT] for CodeList[SegmentType]", error.getMessage());
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final MyRecord[] rows = new MyRecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("11", null, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
        assertEquals(1, rows[0].getErrors().size());
        assertEquals(1, rows[1].getErrors().size());
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final MyRecord[] rows = new MyRecord[]{
                record("11", null, "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
        assertEquals(3, rows[0].getErrors().size());
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final MyRecord[] rows = new MyRecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("12", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S12, rows);
        assertEquals(0, rows[0].getErrors().size());
        assertEquals(1, rows[1].getErrors().size());
        assertEquals(1, rows[2].getErrors().size());
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final MyRecord[] rows = new MyRecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S14, S11, rows);
        assertEquals(1, rows[0].getErrors().size());
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final MyRecord detached = record("90", "9999", "Warning", "Detached");
        final MyRecord[] rows = new MyRecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
        assertEquals(0, rows[0].getErrors().size());
        assertEquals(1, detached.getErrors().size());
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final MyRecord[] rows = fastInvoice(S11);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final MyRecord[] rows = new MyRecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
                record("14", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final MyRecord invalid = record("90", "1199", "Support", null);
        final MyRecord[] rows = new MyRecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
        assertEquals(0, rows[0].getErrors().size());
        assertEquals(1, invalid.getErrors().size());
    }

}
