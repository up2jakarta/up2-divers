package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * Up2J {@link io.github.up2jakarta.csv.cfg.Position} checker implementation that checks if each offset is unique
 * and no gap between offsets, useful for persist-able output result.
 */
@Named
@Singleton
public final class UniqueGapChecker implements TypeListener {

    @Override
    public TypeContext beforeSegment(Class<? extends Segment> type) {
        return new Context(type);
    }

    private static final class Node {
        private final List<Integer> offsets = new LinkedList<>();
        private final Class<?> source;
        private final String locator;
        private final int offset;

        Node(Class<?> source, String locator, int offset) {
            this.locator = locator;
            this.offset = offset;
            this.source = source;
        }

        int min() {
            return offsets.stream().min(Integer::compareTo).orElse(0);
        }

        int max() {
            return offsets.stream().max(Integer::compareTo).orElse(0);
        }

        int[] gap() {
            return IntStream.range(0, this.max()).filter(i -> !offsets.contains(i)).toArray();
        }
    }

    private static final class Context implements TypeContext {
        private final List<Node> fragments = new LinkedList<>();
        private final List<Node> stack = new LinkedList<>();
        private final Node root;

        private Context(Class<? extends Segment> type) {
            this.root = new Node(type, CLASS, 0);
            stack.addLast(root);
        }

        private void checkUnique(Node entry, Field property, int offset) throws BeanException {
            if (entry.offsets.contains(offset)) {
                throw BeanException.of(property, "@Position[value] must be unique");
            }
            entry.offsets.add(offset);
        }

        @Override
        public void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
            final Node node = stack.getLast();
            this.checkUnique(node, property, offset);
            if (node != root) {
                this.checkUnique(root, property, offset);
            }
        }

        @Override
        public void beforeFragmentProperty(Field fragment, Class<? extends Segment> type, int offset) {
            final Class<?> source = stack.getLast().source;
            stack.addLast(new Node(source, fragment.getName(), offset));
        }

        @Override
        public void afterFragmentProperty(Field fragment, Class<? extends Segment> type) {
            fragments.add(stack.removeLast());
        }

        @Override
        public void close() throws BeanException {
            final int[] gap = root.gap();
            if (gap.length != 0) {
                final Node node = fragments.stream().filter(n -> {
                    final int min = n.min();
                    final int max = n.max();
                    return Arrays.stream(gap).allMatch(o -> min < o && o < max);
                }).findAny().orElse(root);
                final int offset = node.offset;
                final String cn = stream(gap).mapToObj(o -> String.valueOf(o - offset)).collect(joining(", "));
                throw new BeanException(node.source, node.locator, "must not have gap on @Position[value]: " + cn);
            }
        }

    }
}
