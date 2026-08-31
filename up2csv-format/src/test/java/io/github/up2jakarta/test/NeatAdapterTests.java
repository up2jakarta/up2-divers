package io.github.up2jakarta.test;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.csv.data.SimpleNeatImporter;
import io.github.up2jakarta.csv.io.NeatAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static io.github.up2jakarta.csv.io.NeatAdapter.DEL;
import static io.github.up2jakarta.csv.io.NeatAdapter.SEP;
import static io.github.up2jakarta.lov.SeverityType.FATAL;
import static io.github.up2jakarta.test.TreeType.*;
import static java.nio.charset.StandardCharsets.UTF_16;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

enum TreeType implements IType<TreeType> {

    D0("0", "1st depth (Tree)"),
    D1("1", "2nd depth (Node)"),
    D2("2", "3rd depth (Node)"),
    D3("3", "4th depth (Node)");

    private final String code;
    private final String name;

    TreeType(String code, String name) {
        assertEquals(1, code.length());
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class TreeLinker implements ILinker<Tree, Tree> {

    @Override
    public List<Tree> from(Tree parent) {
        return parent.getNodes();
    }

    @Override
    public void link(Tree parent, Tree child) {
        parent.getNodes().add(child);
    }
}

@Valid
@Error(value = "CSV-TREE", level = FATAL)
@BusinessObject(value = "0", overrides = {
        @Overlink(value = @BusinessLink("1"), replaces = @Sublink(value = "1", with = "2")),
        @Overlink(value = @BusinessLink("2"), replaces = @Sublink(value = "1", with = "3")),
        @Overlink(value = @BusinessLink("3"), excludes = "1") // Leaf node
})
final class Tree implements Segment, Serializable {

    @Position(0)
    private final @NotNull(payload = Warning.class) String value;

    @BusinessLink(value = "1", bean = @Linker(TreeLinker.class))
    private final List<Tree> nodes = new LinkedList<>();

    public Tree(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public List<Tree> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tree that) || !Objects.equals(value, that.value)) {
            return false;
        }
        return Arrays.equals(this.nodes.toArray(), that.nodes.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class NeatAdapterTests {

    private static final int REPEAT = 20_000;
    private static final List<NeatRecord<TreeType>> records = List.of(
            new NeatRecord<>(D0, "$"),
                new NeatRecord<>(D1, "$1"),
                    new NeatRecord<>(D2, "$1.1"),
                    new NeatRecord<>(D2, "$1.2"),
                        new NeatRecord<>(D3, "$1.2.1"),
                        new NeatRecord<>(D3, "$1.2.2"),
                    new NeatRecord<>(D2, "$1.3"),
                new NeatRecord<>(D1, "$2"),
                    new NeatRecord<>(D2, "$2.1"),
                        new NeatRecord<>(D3, "$2.1.1"),
                        new NeatRecord<>(D3, "$2.1.2"),
                    new NeatRecord<>(D2, "$2.2"),
                new NeatRecord<>(D1, "$3"),
                    new NeatRecord<>(D2, "$3.1"),
                    new NeatRecord<>(D2, "$3.2"),
                        new NeatRecord<>(D3, "$3.2.1"),
                        new NeatRecord<>(D3, "$3.2.2"),
                new NeatRecord<>(D1, "$4")
    );

    private final Tree object;
    private final Up2Factory<?> factory;
    private final NeatAdapter<Tree> adapter;

    @Autowired
    NeatAdapterTests(ConfigurableApplicationContext context) throws BeanException {
        if (!context.containsBean("TreeLinker")) {
            context.getBeanFactory().registerSingleton("TreeLinker", new TreeLinker());
        }
        this.factory = context.getBean(Up2Factory.class);
        this.adapter = new NeatAdapter<>(factory, Tree.class, TreeType.class);
        var importer = new SimpleNeatImporter<>(factory, Tree.class, TreeType.class);
        this.object = importer.parse(records, (i, r) -> {
            assertEquals(0, r.size());
            return i;
        });
    }

    private static String[] splitOutput(String out) {
        final String sep = String.valueOf(SEP);
        final String del = Pattern.quote(sep);
        return out.split(del);
    }

    private static void assertOutput(String out, char delimiter) {
        final String[] lines = splitOutput(out);
        assertEquals(records.size(), lines.length);
        var i = 0;
        for (final NeatRecord<TreeType> record : records) {
            final String line = lines[i++];
            assertEquals(delimiter, line.charAt(1));
            assertEquals(record.getType().getCode(), line.substring(0, 1), line);
            assertEquals(record.getData()[0], line.substring(2), line);
        }
    }

    @SuppressWarnings("unused")
    private static void printOutput(String out) {
        final String[] lines = splitOutput(out);
        for (final String line : lines) {
            final int depth = Integer.parseInt(line.substring(0, 1));
            System.out.println(">> ".repeat(depth) + line.substring(2));
        }
    }

    @Test
    void testCSV() throws BeanException {
        // Given
        final char delimiter = ',';
        final NeatAdapter<Tree> adapter = new NeatAdapter<>(factory, delimiter, Tree.class, TreeType.class);
        // When Format
        final String out = adapter.format(object);
        // Then
        assertOutput(out, delimiter);
        //printOutput(out);
        // When Parse
        final Tree tree = adapter.parse(out);
        // Then
        assertEquals(object, tree);
    }

    @Test
    void testTSV() {
        // When Format
        final String out = adapter.format(object);
        // Then
        assertOutput(out, DEL);
        // When Parse
        final Tree tree = adapter.parse(out);
        // Then
        assertEquals(object, tree);
    }

    @Test
    void testSeparator() {
        // Given
        final Tree input = new Tree("L1" + SEP + "L2");
        {
            input.getNodes().add(new Tree("L1" + SEP + "L2"));
            input.getNodes().add(new Tree("L1" + DEL + "L2"));
        }
        // When Format
        final String out = adapter.format(input);
        // When Parse
        final Tree tree = adapter.parse(out);
        // Then
        assertEquals(input, tree);
    }

    @Test
    void testNulls() {
        // When Parse
        final Tree tree = adapter.parse(null);
        // Then
        assertNull(tree);
        // When Format
        final String out = adapter.format(null);
        // Then
        assertNull(out);
    }

    @Test
    void invalidDelimiter() {
        // When
        final IllegalArgumentException cause = assertThrows(
                IllegalArgumentException.class,
                () -> new NeatAdapter<>(factory, SEP, Tree.class, TreeType.class)
        );
        // Then
        assertEquals("The delimiter cannot be a line break", cause.getMessage());
    }

    @Test
    void ignoreWarning() {
        // Given
        final String input = "0" + DEL;
        // When Parse
        final Tree tree = adapter.parse(input);
        // Then
        assertNotNull(tree);
        assertNull(tree.getValue());
        assertEquals(0, tree.getNodes().size());
        // When Format
        final String out = adapter.format(tree);
        // Then
        assertEquals(input, out);
    }

    @Test
    void testCompact() throws IOException {
        // Given
        final String out = adapter.format(object);
        final int min = out.getBytes(UTF_8).length;
        final int max = out.getBytes(UTF_16).length;
        // When
        final ByteArrayOutputStream bos = new ByteArrayOutputStream((int) Math.ceil(max * 2.366));
        try (final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
        }
        // Then
        assertEquals(126, min);
        assertEquals(254, max);
        assertTrue(bos.size() > min);
        assertTrue(bos.size() > max);
    }

    @Test
    void perfCSV() {
        final Instant start = Instant.now();
        for (var i = 0; i < REPEAT; i++) {
            // When Format
            final String out = adapter.format(object);
            // When Parse
            final Tree tree = adapter.parse(out);
            // Then
            assertEquals(object, tree);
        }
        System.out.println("CSV: " + Duration.between(start, Instant.now()));
    }

    @Test
    void perfSER() throws IOException, ClassNotFoundException {
        final Instant start = Instant.now();
        for (var i = 0; i < REPEAT; i++) {
            // When Format
            final ByteArrayOutputStream bos = new ByteArrayOutputStream(601);
            try (final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(object);
            }
            // When Parse
            final Tree tree;
            final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            try (final ObjectInputStream ois = new ObjectInputStream(bis)) {
                tree = (Tree) ois.readObject();
            }
            // Then
            assertEquals(object, tree);
        }
        System.out.println("SER: " + Duration.between(start, Instant.now()));
    }

}
