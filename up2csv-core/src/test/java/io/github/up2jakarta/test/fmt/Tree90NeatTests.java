package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.SimpleNeatExporter;
import io.github.up2jakarta.csv.data.SimpleNeatImporter;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.ANeatTester;
import io.github.up2jakarta.test.fmt.misc.Record;
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
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static io.github.up2jakarta.test.impl.TermType.NODE;
import static io.github.up2jakarta.test.impl.TermType.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Tree90NeatTests {

    private final SimpleNeatImporter<Tree90, TermType, SegmentType> importer;
    private final SimpleNeatExporter<Tree90, TermType, SegmentType> exporter;

    @Autowired
    Tree90NeatTests(Up2Factory<TermType> factory) throws BeanException {
        this.importer = new SimpleNeatImporter<>(factory, Tree90.class, SegmentType.class);
        this.exporter = importer.toExporter();
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
    void testLevel0() {
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00", "$")
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
        final ANeatTester<Record> tc = new ANeatTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel1() {
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                new Record(S91, "10", "$.10")
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
        final ANeatTester<Record> tc = new ANeatTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel2() {
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                new Record(S91, "10", "$.10"),
                new Record(S92, "20", "$.10.20")
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
        final ANeatTester<Record> tc = new ANeatTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel3() {
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                new Record(S91, "10", "$.10"),
                new Record(S92, "20", "$.10.20"),
                new Record(S93, "30", "$.10.20.30")
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
        final ANeatTester<Record> tc = new ANeatTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testLevel4() {
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                new Record(S91, "10", "$.10"),
                new Record(S92, "20", "$.10.20"),
                new Record(S93, "30", "$.10.20.30"),
                new Record(S94, "40", "$.10.20.30.40")
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
        final ANeatTester<Record> tc = new ANeatTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testDetached1() {
        // Given
        final Record detached = new Record(S93, "30", "$.10.XX.30");
        final Record[] records = new Record[]{
                detached,
                new Record(S90, "00", "$"),
                new Record(S91, "10", "$.10")
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
        final Record detached = new Record(S94, "40", "$.10.20.XX.40");
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                new Record(S91, "10", "$.10"),
                new Record(S92, "20", "$.10.20"),
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
        final Record detached1 = new Record(S93, "30", "$.10.XX.30");
        final Record detached2 = new Record(S94, "40", "$.10.20.XX.40");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
        }
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                detached1,
                new Record(S91, "10", "$.10"),
                detached2,
                new Record(S92, "20", "$.10.20"),
                new Record(S93, "30", "$.10.20.30"),
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
        final Record detached1 = new Record(S92, "21", "$.XX.21");
        final Record detached2 = new Record(S93, "31", "$.10.XX.31");
        final Record detached3 = new Record(S94, "41", "$.10.20.XX.41");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
            failures.add(detached3);
        }
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                detached1, detached2, detached3,
                new Record(S91, "10", "$.10"),
                new Record(S92, "20", "$.10.20"),
                new Record(S93, "30", "$.10.20.30"),
                new Record(S94, "40", "$.10.20.30.40")
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
    void testImportValidation() {
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00", null),
                new Record(S91, "10", null),
                new Record(S92, "20", null),
                new Record(S93, "30", null),
                new Record(S94, "40", null)
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
            assertEquals(2, error.getOffset());
        }
        // When Formating
        final ANeatTester<Record> tc = new ANeatTester<>(records);
        exporter.format(root, tc::assertExists);
        tc.assertEmpty();
    }

    @Test
    void testExportValidation() {
        // Given
        final Record[] records = new Record[]{
                new Record(S90, "00"),
                new Record(S91, "10"),
                new Record(S92, "20"),
                new Record(S93, "30"),
                new Record(S94, "40")
        };
        final Tree90 root = importer.parse(records, (i, r) -> i);
        // When Validating
        final List<? extends IEvent<TermType>> errors = exporter.validate(root);
        // Then Errors
        assertEquals(5, errors.size());
        for (final IEvent<TermType> error : errors) {
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(TermType.NONE, error.getType());
            assertEquals("must not be empty", error.getMessage());
            assertEquals(2, error.getOffset());
        }
    }

    @Test
    void testNullIdValidation() {
        // Given
        final Record detached = new Record(S92, null, "$.10.20");
        final Record[] records = new Record[]{
                new Record(S90, "00", "$"),
                new Record(S91, "10", "$.10"),
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
        assertEquals(1, node1.getNodes().size());
        // Then Errors
        assertEquals(1, errors.size());
        {
            final PropertyEvent<?, ?> error = errors.getFirst();
            assertEquals(detached, error.getRecord());
            assertEquals(1, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_COMPLIANCE, error.getCode());
            assertEquals(UUID, error.getType());
            assertEquals("must not be null", error.getMessage());
        }
    }

}
