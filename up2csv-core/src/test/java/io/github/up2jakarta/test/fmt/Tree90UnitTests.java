package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.fmt.SimpleUnitExporter;
import io.github.up2jakarta.csv.fmt.SimpleUnitImporter;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AUnitTester;
import io.github.up2jakarta.test.fmt.tree.Tree90;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.csv.core.BusinessImporter.DETACHED;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.NODE;
import static io.github.up2jakarta.test.impl.TermType.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Tree90UnitTests {

    private final SimpleUnitImporter<Tree90, TermType, SegmentType> importer;
    private final SimpleUnitExporter<Tree90, TermType, SegmentType> exporter;

    @Autowired
    Tree90UnitTests(Up2Factory<TermType> factory) throws BeanException {
        this.importer = new SimpleUnitImporter<>(factory, Tree90.class, SegmentType.class);
        this.exporter = importer.toExporter();
    }

    private UnitRecord<SegmentType> record(String... row) throws CodeListException {
        return importer.transform(row);
    }

    @Test
    void testEmpty() {
        // Given
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
    void testLevel0() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
        final AUnitTester<UnitRecord<SegmentType>> tc = new AUnitTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel1() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
        final AUnitTester<UnitRecord<SegmentType>> tc = new AUnitTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel2() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                record("92", "10", "20", "$.10.20")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
        final AUnitTester<UnitRecord<SegmentType>> tc = new AUnitTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel3() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                record("92", "10", "20", "$.10.20"),
                record("93", "10", "20", "30", "$.10.20.30")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
        final AUnitTester<UnitRecord<SegmentType>> tc = new AUnitTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel4() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                record("92", "10", "20", "$.10.20"),
                record("93", "10", "20", "30", "$.10.20.30"),
                record("94", "10", "20", "30", "40", "$.10.20.30.40")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
        final AUnitTester<UnitRecord<SegmentType>> tc = new AUnitTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testFastDetached() {
        // Given
        final UnitRecord<SegmentType> detached = record("92");
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                detached
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(1, errors.size());
        {
            final PropertyEvent<?, ?> error = errors.getFirst();
            assertEquals(detached, error.getRecord());
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
        final UnitRecord<SegmentType> detached = record("92", "11", "21", "$.10.20");
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                record("92", "10", "20", "$.10.20"),
                detached
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(1, errors.size());
        {
            final PropertyEvent<?, ?> error = errors.getFirst();
            assertEquals(detached, error.getRecord());
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
        final UnitRecord<SegmentType> detached1 = record("93", "11", "20", "31", "$.11.20.31");
        final UnitRecord<SegmentType> detached2 = record("93", "10", "21", "31", "$.10.21.31");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
        }
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                record("92", "10", "20", "$.10.20"),
                record("93", "10", "20", "30", "$.10.20.30"),
                detached1, detached2
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(failures.size(), errors.size());
        for (final PropertyEvent<?, ?> error : errors) {
            assertTrue(failures.remove(error.getRecord()));
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
        final UnitRecord<SegmentType> detached1 = record("94", "11", "20", "30", "41", "$.11.20.30.41");
        final UnitRecord<SegmentType> detached2 = record("94", "10", "21", "30", "41", "$.10.21.30.41");
        final UnitRecord<SegmentType> detached3 = record("94", "10", "20", "31", "41", "$.10.20.31.41");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
            failures.add(detached3);
        }
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                record("92", "10", "20", "$.10.20"),
                record("93", "10", "20", "30", "$.10.20.30"),
                record("94", "10", "20", "30", "40", "$.10.20.30.40"),
                detached1, detached2, detached3
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree90 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(failures.size(), errors.size());
        for (final PropertyEvent<?, ?> error : errors) {
            assertTrue(failures.remove(error.getRecord()));
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
    void testImportValidation() throws IOException {
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", null),
                record("91", "10", null),
                record("92", "10", "20", null),
                record("93", "10", "20", "30", null),
                record("94", "10", "20", "30", "40", null)
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
            final PropertyEvent<?, ?> error = errors.get(level);
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(TermType.NONE, error.getType());
            assertEquals("must not be empty", error.getMessage());
            assertEquals(records[level], error.getRecord());
            assertNotNull(error.getCause());
            if (level < 2) {
                assertEquals(2, error.getOffset());
            } else {
                assertEquals(level + 1, error.getOffset());
            }
        }
        // When Formating
        final AUnitTester<UnitRecord<SegmentType>> tc = new AUnitTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testExportValidation() {
        // Given
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00"),
                record("91", "10"),
                record("92", "10", "20"),
                record("93", "10", "20", "30"),
                record("94", "10", "20", "30", "40")
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
            if (level < 2) {
                assertEquals(2, error.getOffset());
            } else {
                assertEquals(level + 1, error.getOffset());
            }
        }
    }

    @Test
    void testPIdValidation() {
        // Given
        final UnitRecord<SegmentType> detached = record("92", "\r", "20", "$.10.20");
        final UnitRecord<SegmentType>[] records = new UnitRecord[]{
                record("90", "00", "$"),
                record("91", "10", "$.10"),
                detached
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
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
            final PropertyEvent<?, ?> error = errors.getFirst();
            assertEquals(detached, error.getRecord());
            assertEquals(1, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(UUID, error.getType());
            assertEquals("must not be null", error.getMessage());
        }
        {
            final PropertyEvent<?, ?> error = errors.getLast();
            assertEquals(detached, error.getRecord());
            assertNull(error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals("CSV-N0DE", error.getCode());
            assertEquals(NODE, error.getType());
            assertEquals(DETACHED, error.getMessage());
        }
    }

}
