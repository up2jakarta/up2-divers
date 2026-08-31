package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AFullTester;
import io.github.up2jakarta.test.fmt.tree.Tree90;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.core.MessImporter.DETACHED;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.NODE;
import static io.github.up2jakarta.test.impl.TermType.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Tree90FullTests {

    private final SimpleFullImporter<Tree90, TermType, SegmentType> importer;
    private final SimpleFullExporter<Tree90, TermType, SegmentType> exporter;

    @Autowired
    Tree90FullTests(Up2Factory<TermType> factory) throws BeanException {
        this.importer = new SimpleFullImporter<>(factory, Tree90.class, SegmentType.class);
        this.exporter = importer.toExporter();
    }

    private FullRecord<SegmentType> record(String... row) throws CodeListException {
        return importer.transform(row);
    }

    @Test
    void testEmpty() {
        // Given
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When
        final Tree90 root = importer.parse(List.of(), (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNull(root);
        assertEquals(0, errors.size());
    }

    @Test
    void testLevel0() {
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$")
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        {
            assertEquals("00", root.getKey());
            assertEquals("$", root.getValue());
            assertEquals(0, root.getNodes().size());
        }
        // When Formating
        final AFullTester<FullRecord<SegmentType>> tc = new AFullTester<>(root, records);
        final Fixed06Generator uid = new Fixed06Generator();
        exporter.format(root, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

    @Test
    void testLevel1() {
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10")
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Tree90 node = root.getNodes().getFirst();
        {
            assertEquals("10", node.getKey());
            assertEquals("$.10", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
        // When Formating
        final AFullTester<FullRecord<SegmentType>> tc = new AFullTester<>(root, records);
        final Fixed06Generator uid = new Fixed06Generator();
        exporter.format(root, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

    @Test
    void testLevel2() {
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                record("up2v08", "92", "00", "10", "20", "$.10.20")
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Tree90 node = node1.getNodes().getFirst();
        {
            assertEquals("20", node.getKey());
            assertEquals("$.10.20", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
        // When Formating
        final AFullTester<FullRecord<SegmentType>> tc = new AFullTester<>(root, records);
        final Fixed06Generator uid = new Fixed06Generator();
        exporter.format(root, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

    @Test
    void testLevel3() {
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                record("up2v08", "92", "00", "10", "20", "$.10.20"),
                record("up2v09", "93", "00", "10", "20", "30", "$.10.20.30")
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Tree90 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Tree90 node = node2.getNodes().getFirst();
        {
            assertEquals("30", node.getKey());
            assertEquals("$.10.20.30", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
        // When Formating
        final AFullTester<FullRecord<SegmentType>> tc = new AFullTester<>(root, records);
        final Fixed06Generator uid = new Fixed06Generator();
        exporter.format(root, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

    @Test
    void testLevel4() {
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                record("up2v08", "92", "00", "10", "20", "$.10.20"),
                record("up2v09", "93", "00", "10", "20", "30", "$.10.20.30"),
                record("up2v0a", "94", "00", "10", "20", "30", "40", "$.10.20.30.40")
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Tree90 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Tree90 node3 = node2.getNodes().getFirst();
        assertEquals(1, node3.getNodes().size());
        final Tree90 node = node3.getNodes().getFirst();
        {
            assertEquals("40", node.getKey());
            assertEquals("$.10.20.30.40", node.getValue());
        }
        // When Formating
        final AFullTester<FullRecord<SegmentType>> tc = new AFullTester<>(root, records);
        final Fixed06Generator uid = new Fixed06Generator();
        exporter.format(root, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

    @Test
    void testFastDetached() {
        // Given
        final FullRecord<SegmentType> detached = record("up2v09", "92", "00");
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                detached
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(1, errors.size());
        {
            final FullError<?, ?> error = errors.getFirst();
            assertEquals(detached, error.getKey().getRecord());
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-N0DE", error.getCode());
            assertEquals(NODE, error.getType());
            assertEquals(DETACHED, error.getMessage());
        }
    }

    @Test
    void testDetached2() {
        // Given
        final FullRecord<SegmentType> detached = record("up2v09", "92", "00", "11", "21", "$.10.20");
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                record("up2v08", "92", "00", "10", "20", "$.10.20"),
                detached
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(1, errors.size());
        {
            final FullError<?, ?> error = errors.getFirst();
            assertEquals(detached, error.getKey().getRecord());
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-N0DE", error.getCode());
            assertEquals(NODE, error.getType());
            assertEquals(DETACHED, error.getMessage());
        }
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Tree90 node = node1.getNodes().getFirst();
        {
            assertEquals("20", node.getKey());
            assertEquals("$.10.20", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
    }

    @Test
    void testDetached3() {
        final FullRecord<SegmentType> detached1 = record("up2v0a", "93", "00", "11", "20", "31", "$.11.20.31");
        final FullRecord<SegmentType> detached2 = record("up2v0b", "93", "00", "10", "21", "31", "$.10.21.31");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
        }
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                record("up2v08", "92", "00", "10", "20", "$.10.20"),
                record("up2v09", "93", "00", "10", "20", "30", "$.10.20.30"),
                detached1, detached2
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(failures.size(), errors.size());
        for (final FullError<?, ?> error : errors) {
            assertTrue(failures.remove(error.getKey().getRecord()));
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-N0DE", error.getCode());
            assertEquals(NODE, error.getType());
            assertEquals(DETACHED, error.getMessage());
        }
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Tree90 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Tree90 node = node2.getNodes().getFirst();
        {
            assertEquals("30", node.getKey());
            assertEquals("$.10.20.30", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
    }

    @Test
    void testDetached4() {
        // Given
        final FullRecord<SegmentType> detached1 = record("up2v0b", "94", "00", "11", "20", "30", "41", "$.11.20.30.41");
        final FullRecord<SegmentType> detached2 = record("up2v0c", "94", "00", "10", "21", "30", "41", "$.10.21.30.41");
        final FullRecord<SegmentType> detached3 = record("up2v0d", "94", "00", "10", "20", "31", "41", "$.10.20.31.41");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
            failures.add(detached3);
        }
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                record("up2v08", "92", "00", "10", "20", "$.10.20"),
                record("up2v09", "93", "00", "10", "20", "30", "$.10.20.30"),
                record("up2v0a", "94", "00", "10", "20", "30", "40", "$.10.20.30.40"),
                detached1, detached2, detached3
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(failures.size(), errors.size());
        for (final FullError<?, ?> error : errors) {
            assertTrue(failures.remove(error.getKey().getRecord()));
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-N0DE", error.getCode());
            assertEquals(NODE, error.getType());
            assertEquals(DETACHED, error.getMessage());
        }
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Tree90 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Tree90 node3 = node2.getNodes().getFirst();
        assertEquals(1, node3.getNodes().size());
        final Tree90 node = node3.getNodes().getFirst();
        {
            assertEquals("40", node.getKey());
            assertEquals("$.10.20.30.40", node.getValue());
        }
    }

    @Test
    void testImportValidation() {
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", null),
                record("up2v07", "91", "00", "10", null),
                record("up2v08", "92", "00", "10", "20", null),
                record("up2v09", "93", "00", "10", "20", "30", null),
                record("up2v0a", "94", "00", "10", "20", "30", "40", null)
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When Parsing
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then Tree
        assertNotNull(root);
        assertEquals("00", root.getKey());
        assertNull(root.getValue());
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals("10", node1.getKey());
        assertNull(node1.getValue());
        assertEquals(1, node1.getNodes().size());
        final Tree90 node2 = node1.getNodes().getFirst();
        assertEquals("20", node2.getKey());
        assertNull(node2.getValue());
        assertEquals(1, node2.getNodes().size());
        final Tree90 node3 = node2.getNodes().getFirst();
        assertEquals("30", node3.getKey());
        assertNull(node3.getValue());
        assertEquals(1, node3.getNodes().size());
        final Tree90 node4 = node3.getNodes().getFirst();
        assertEquals("40", node4.getKey());
        assertNull(node4.getValue());
        // Then Errors
        assertEquals(5, errors.size());
        for (var level = 0; level < errors.size(); level++) {
            final FullError<?, ?> error = errors.get(level);
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(TermType.NONE, error.getType());
            assertEquals("must not be empty", error.getMessage());
            assertEquals(records[level], error.getKey().getRecord());
            assertNull(error.getTrace());
            assertEquals(level + 3, error.getOffset());
        }
        // When Formating
        final AFullTester<FullRecord<SegmentType>> tc = new AFullTester<>(root, records);
        final Fixed06Generator uid = new Fixed06Generator();
        exporter.format(root, d -> {
            d[0] = uid.get();
            tc.assertExists(d);
        });
        tc.assertEmpty();
    }

    @Test
    void testExportValidation() {
        // Given
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00"),
                record("up2v07", "91", "00", "10"),
                record("up2v08", "92", "00", "10", "20"),
                record("up2v09", "93", "00", "10", "20", "30"),
                record("up2v0a", "94", "00", "10", "20", "30", "40")
        };
        final Tree90 root = importer.parse(records, (i, r) -> i);
        // When Validating
        final List<? extends IEvent<TermType>> errors = exporter.validate(root);
        // Then Errors
        assertEquals(5, errors.size());
        for (var level = 0; level < errors.size(); level++) {
            final IEvent<TermType> error = errors.get(level);
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(TermType.NONE, error.getType());
            assertEquals("must not be empty", error.getMessage());
            assertEquals(level + 3, error.getOffset());
        }
    }

    @Test
    void testPIdValidation() {
        // Given
        final FullRecord<SegmentType> detached = record("up2v08", "92", "00", "\n", "20", "$.10.20");
        final FullRecord<SegmentType>[] records = new FullRecord[]{
                record("up2v06", "90", "00", "$"),
                record("up2v07", "91", "00", "10", "$.10"),
                detached
        };
        final List<FullError<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(1, root.getNodes().size());
        final Tree90 node1 = root.getNodes().getFirst();
        assertEquals(0, node1.getNodes().size());
        // Then Errors
        assertEquals(2, errors.size());
        {
            final FullError<?, ?> error = errors.getFirst();
            assertEquals(detached, error.getKey().getRecord());
            assertEquals(3, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(UUID, error.getType());
            assertEquals("must not be null", error.getMessage());
        }
        {
            final FullError<?, ?> error = errors.getLast();
            assertEquals(detached, error.getKey().getRecord());
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-N0DE", error.getCode());
            assertEquals(NODE, error.getType());
            assertEquals(DETACHED, error.getMessage());
        }
    }

}
