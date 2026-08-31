package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.MessRecord;
import io.github.up2jakarta.csv.data.SimpleMessImporter;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.tree.*;
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

import static io.github.up2jakarta.csv.core.MessImporter.DETACHED;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static io.github.up2jakarta.test.impl.TermType.NODE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Tree80MessTests {

    private final SimpleMessImporter<Tree80, TermType, SegmentType> importer;

    @Autowired
    Tree80MessTests(Up2Factory<TermType> factory) throws BeanException {
        this.importer = new SimpleMessImporter<>(factory, Tree80.class, SegmentType.class);
    }

    private MessRecord<SegmentType> record(SegmentType type, String... data) throws CodeListException {
        return new MessRecord<>(type, null, data);
    }

    @Test
    void testEmpty() {
        // Given
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When
        final Tree80 root = importer.parse(List.of(), (i, r) -> {
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
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        {
            assertEquals("00", root.getReference());
            assertEquals("$", root.getValue());
            assertEquals(0, root.getNodes().size());
        }
    }

    @Test
    void testLevel1() {
        // Given
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Node81 node = root.getNodes().getFirst();
        {
            assertEquals("10", node.getReference());
            assertEquals("$.10", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
    }

    @Test
    void testLevel2() {
        // Given
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10"),
                record(S82, "10", "20", "$.10.20")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Node81 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Node82 node = node1.getNodes().getFirst();
        {
            assertEquals("20", node.getReference());
            assertEquals("10", node.getNode1Id());
            assertEquals("$.10.20", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
    }

    @Test
    void testLevel3() {
        // Given
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10"),
                record(S82, "10", "20", "$.10.20"),
                record(S83, "10", "20", "30", "$.10.20.30")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Node81 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Node82 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Node83 node = node2.getNodes().getFirst();
        {
            assertEquals("30", node.getReference());
            assertEquals("10", node.getNode1Id());
            assertEquals("20", node.getNode2Id());
            assertEquals("$.10.20.30", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
    }

    @Test
    void testLevel4() {
        // Given
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10"),
                record(S82, "10", "20", "$.10.20"),
                record(S83, "10", "20", "30", "$.10.20.30"),
                record(S84, "10", "20", "30", "40", "$.10.20.30.40")
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
            errors.addAll(r);
            return i;
        });
        // Then
        assertNotNull(root);
        assertEquals(0, errors.size());
        assertEquals(1, root.getNodes().size());
        final Node81 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Node82 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Node83 node3 = node2.getNodes().getFirst();
        assertEquals(1, node3.getNodes().size());
        final Leaf84 node = node3.getNodes().getFirst();
        {
            assertEquals("40", node.getReference());
            assertEquals("10", node.getNode1Id());
            assertEquals("20", node.getNode2Id());
            assertEquals("30", node.getNode3Id());
            assertEquals("$.10.20.30.40", node.getValue());
        }
    }

    @Test
    void testDetached2() {
        // Given
        final MessRecord<SegmentType> detached = record(S82, "11", "21", "$.10.20");
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10"),
                record(S82, "10", "20", "$.10.20"),
                detached
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
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
        final Node81 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Node82 node = node1.getNodes().getFirst();
        {
            assertEquals("20", node.getReference());
            assertEquals("10", node.getNode1Id());
            assertEquals("$.10.20", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
    }

    @Test
    void testDetached3() {
        final MessRecord<SegmentType> detached1 = record(S83, "11", "20", "31", "$.11.20.31");
        final MessRecord<SegmentType> detached2 = record(S83, "10", "21", "31", "$.10.21.31");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
        }
        // Given
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10"),
                record(S82, "10", "20", "$.10.20"),
                record(S83, "10", "20", "30", "$.10.20.30"),
                detached1, detached2
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
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
        final Node81 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Node82 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Node83 node = node2.getNodes().getFirst();
        {
            assertEquals("30", node.getReference());
            assertEquals("10", node.getNode1Id());
            assertEquals("20", node.getNode2Id());
            assertEquals("$.10.20.30", node.getValue());
            assertEquals(0, node.getNodes().size());
        }
    }

    @Test
    void testDetached4() {
        // Given
        final MessRecord<SegmentType> detached1 = record(S84, "11", "20", "30", "41", "$.11.20.30.41");
        final MessRecord<SegmentType> detached2 = record(S84, "10", "21", "30", "41", "$.10.21.30.41");
        final MessRecord<SegmentType> detached3 = record(S84, "10", "20", "31", "41", "$.10.20.31.41");
        final List<Object> failures = new ArrayList<>(2);
        {
            failures.add(detached1);
            failures.add(detached2);
            failures.add(detached3);
        }
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10"),
                record(S82, "10", "20", "$.10.20"),
                record(S83, "10", "20", "30", "$.10.20.30"),
                record(S84, "10", "20", "30", "40", "$.10.20.30.40"),
                detached1, detached2, detached3
        };
        final List<PropertyEvent<?, ?>> errors = new LinkedList<>();
        // When & Then
        final Tree80 root = importer.parse(records, (i, r) -> {
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
        final Node81 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Node82 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Node83 node3 = node2.getNodes().getFirst();
        assertEquals(1, node3.getNodes().size());
        final Leaf84 node = node3.getNodes().getFirst();
        {
            assertEquals("40", node.getReference());
            assertEquals("10", node.getNode1Id());
            assertEquals("20", node.getNode2Id());
            assertEquals("30", node.getNode3Id());
            assertEquals("$.10.20.30.40", node.getValue());
        }
    }

    @Test
    void testExportReferenceIds() throws BeanException {
        // Given
        final MessRecord<SegmentType>[] records = new MessRecord[]{
                record(S80, "00", "$"),
                record(S81, "10", "$.10"),
                record(S82, "10", "20", "$.10.20"),
                record(S83, "10", "20", "30", "$.10.20.30"),
                record(S84, "10", "20", "30", "40", "$.10.20.30.40")
        };
        final Tree80 root = importer.parse(records, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
        assertNotNull(root);
        assertEquals(1, root.getNodes().size());
        final Node81 node1 = root.getNodes().getFirst();
        assertEquals(1, node1.getNodes().size());
        final Node82 node2 = node1.getNodes().getFirst();
        assertEquals(1, node2.getNodes().size());
        final Node83 node3 = node2.getNodes().getFirst();
        assertEquals(1, node3.getNodes().size());
        final Leaf84 node4 = node3.getNodes().getFirst();
        // When
        node2.setNode1Id("Ignore R1");
        node3.setNode1Id("Ignore R1");
        node3.setNode2Id("Ignore R2");
        node4.setNode1Id("Ignore R1");
        node4.setNode2Id("Ignore R2");
        node4.setNode3Id("Ignore R3");
        // Then
        final List<? extends IRecord<?>> store = new ArrayList<>(asList(records));
        importer.toExporter().format(root, row -> {
            final IRecord<?> found = store.stream().filter(r -> row[0].equals(r.getType().getCode()))
                    .findAny()
                    .orElseThrow();
            final String[] src = found.getData();
            final int offset = (found.getType() == S80) ? 1 : 2;
            assertEquals(src.length, row.length - offset);
            for (int i = offset; i < row.length; i++) {
                assertEquals(row[i], src[i - offset]);
            }
            store.remove(found);
        });
        assertEquals(0, store.size());
    }

}
