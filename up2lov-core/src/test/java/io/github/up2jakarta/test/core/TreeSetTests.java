package io.github.up2jakarta.test.core;

import io.github.up2jakarta.lov.bst.RedBlackSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SequencedSet;

import static io.github.up2jakarta.test.core.TreeMapTests.random;
import static io.github.up2jakarta.test.core.TreeMapTests.shuffle;
import static org.junit.jupiter.api.Assertions.*;

public class TreeSetTests {

    private static void randomAdd(IntSet tree, List<Integer> source) {
        final List<Integer> keys = shuffle(source);
        var size = tree.size();
        for (Integer key : keys) {
            tree.add(key);
            tree.assertValid();
            assertEquals(++size, tree.size());
        }
        assertEquals(0, tree.getFirst());
        assertEquals(source.size() - 1, tree.getLast());
        assertEquals(source.size(), tree.size());
        assertEquals(source.size(), tree.count());
    }

    @Test
    void shouldInsertOrder() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        // THEN
        tree.assertValid();
    }

    @RepeatedTest(10)
    void shouldNotInsertExisting() {
        // GIVEN
        final List<Integer> keys = random(100);
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        // THEN
        final int size = tree.size();
        final int key = random(keys);
        tree.add(key);
        assertEquals(size, tree.size());
    }

    @Test
    void shouldContainsKey() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        final int notFound = keys.getLast() + 1;
        // THEN
        for (Integer key : keys) {
            assertTrue(tree.contains(key));
        }
        assertFalse(tree.contains(notFound));
    }

    @Test
    void shouldValidWhenDelete1() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        // THEN
        final Iterator<Integer> it = new ArrayList<>(keys).iterator();
        while (it.hasNext()) {
            final Integer key = it.next();
            it.remove();
            // Validate
            assertTrue(tree.remove(key));
            tree.assertValid();
        }
        assertEquals(0, tree.size());
    }

    @Test
    void shouldValidWhenDelete2() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        // THEN
        final Iterator<Integer> it = shuffle(keys).iterator();
        while (it.hasNext()) {
            final Integer key = it.next();
            it.remove();
            // Validate
            assertTrue(tree.contains(key), "contains " + key);
            assertTrue(tree.remove(key), "remove " + key);
            tree.assertValid();
        }
        assertEquals(0, tree.size());
    }

    @Test
    void shouldNotDeleteNotFound() {
        // GIVEN
        final List<Integer> keys = random(10);
        final Integer highestKey = keys.getLast();
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        final int size = tree.size();
        assertFalse(tree.remove(highestKey + 1));
        // THEN
        assertEquals(size, tree.size());
        tree.assertValid();
    }

    @Test
    void testRemove() {
        // GIVEN
        final IntSet tree = new IntSet();
        randomAdd(tree, random(1000));
        // WHEN
        for (final Integer key : tree.reversed()) {
            tree.remove(key);
        }
        // THEN
        assertEquals(0, tree.size());
        assertEquals(0, tree.count());
    }

    @Test
    void testArray() {
        // GIVEN
        final int max = 9;
        final List<Integer> keys = random(max + 1);
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        // THEN
        assertArrayEquals(keys.toArray(), tree.toArray());
        assertArrayEquals(keys.toArray(Integer[]::new), tree.toArray(Integer[]::new));
        // First
        assertEquals(0, tree.getFirst());
        assertEquals(0, tree.removeFirst());
        assertEquals(1, tree.getFirst());
        // Last
        assertEquals(max, tree.getLast());
        assertEquals(max, tree.removeLast());
        assertEquals(max - 1, tree.getLast());
    }

    @Test
    void testReversed() {
        // GIVEN
        final int max = 9;
        final List<Integer> keys = random(max + 1);
        final IntSet tree = new IntSet();
        // WHEN
        randomAdd(tree, keys);
        final SequencedSet<Integer> reversed = tree.reversed();
        // THEN
        assertArrayEquals(keys.reversed().toArray(), reversed.toArray());
        assertArrayEquals(keys.reversed().toArray(Integer[]::new), reversed.toArray(Integer[]::new));
        assertSame(tree, reversed.reversed());
        assertSame(reversed, tree.reversed());
        // First
        assertEquals(0, tree.getFirst());
        assertEquals(0, reversed.getLast());
        assertEquals(0, reversed.removeLast());
        assertEquals(1, reversed.getLast());
        assertEquals(1, tree.getFirst());
        // Last
        assertEquals(max, tree.getLast());
        assertEquals(max, reversed.getFirst());
        assertEquals(max, reversed.removeFirst());
        assertEquals(max - 1, reversed.getFirst());
        assertEquals(max - 1, tree.getLast());
    }

    @Test
    void testEmpty() {
        // GIVEN
        final IntSet tree = new IntSet();
        // THEN
        assertNull(tree.getLast());
        assertNull(tree.getFirst());
        assertNull(tree.removeFirst());
        assertNull(tree.removeLast());
        assertFalse(tree.iterator().hasNext());
        // GIVEN
        final SequencedSet<Integer> reversed = tree.reversed();
        // WHEN
        assertNull(reversed.getLast());
        assertNull(reversed.getFirst());
        assertNull(reversed.removeFirst());
        assertNull(reversed.removeLast());
        assertFalse(reversed.iterator().hasNext());
    }

    private static final class IntSet extends RedBlackSet<Integer> {
        public IntSet() {
            super(Integer::compareTo);
        }

        private void assertValid() {
            assertValid(this);
        }

        private long count() {
            //noinspection ReplaceInefficientStreamCount
            return super.stream().count();
        }
    }
}
