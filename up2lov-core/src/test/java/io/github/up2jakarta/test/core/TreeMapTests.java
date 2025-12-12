package io.github.up2jakarta.test.core;

import io.github.up2jakarta.lov.bst.RedBlackMap;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TreeMapTests {

    private static void randomPut(IntMap tree, List<Integer> source) {
        final List<Integer> keys = shuffle(source);
        var size = tree.size();
        for (Integer key : keys) {
            tree.put(key, String.valueOf(key));
            tree.assertValid();
            assertEquals(++size, tree.size());
        }
        assertEquals(0, tree.firstEntry().getKey());
        assertEquals(source.size() - 1, tree.lastEntry().getKey());
        assertEquals(source.size(), tree.size());
        assertEquals(source.size(), tree.count());
    }

    static List<Integer> random(int size) {
        return IntStream.range(0, size).boxed().toList();
    }

    static List<Integer> shuffle(List<Integer> source) {
        final List<Integer> keys = new ArrayList<>(source);
        Collections.shuffle(keys);
        return keys;
    }

    static int random(List<Integer> keys) {
        final int randomIndex = ThreadLocalRandom.current().nextInt(keys.size());
        return keys.get(randomIndex);
    }

    @Test
    void shouldInsertOrder() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        // THEN
        tree.assertValid();
    }

    @RepeatedTest(10)
    void shouldNotInsertExisting() {
        // GIVEN
        final List<Integer> keys = random(100);
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        // THEN
        final int size = tree.size();
        final int key = random(keys);
        tree.put(key, String.valueOf(key));
        assertEquals(size, tree.size());
    }

    @Test
    void shouldContainsKey() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        final int notFound = keys.getLast() + 1;
        // THEN
        for (Integer key : keys) {
            assertTrue(tree.containsKey(key));
        }
        assertFalse(tree.containsKey(notFound));
    }

    @Test
    void shouldValidWhenDelete1() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        // THEN
        final Iterator<Integer> it = new ArrayList<>(keys).iterator();
        while (it.hasNext()) {
            final Integer key = it.next();
            it.remove();
            // Validate
            assertTrue(tree.containsKey(key), "contains " + key);
            assertNotNull(tree.remove(key), "remove " + key);
            tree.assertValid();
        }
        assertEquals(0, tree.size());
    }

    @Test
    void shouldValidWhenDelete2() {
        // GIVEN
        final List<Integer> keys = random(10_000);
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        // THEN
        final Iterator<Integer> it = shuffle(keys).iterator();
        while (it.hasNext()) {
            final Integer key = it.next();
            it.remove();
            // Validate
            assertTrue(tree.containsKey(key), "contains " + key);
            assertNotNull(tree.remove(key), "remove " + key);
            tree.assertValid();
        }
        assertEquals(0, tree.size());
    }

    @Test
    void shouldNotDeleteNotFound() {
        // GIVEN
        final List<Integer> keys = random(10);
        final Integer highestKey = keys.getLast();
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        final int size = tree.size();
        assertNull(tree.remove(highestKey + 1));
        // THEN
        assertEquals(size, tree.size());
        tree.assertValid();
    }

    @Test
    void testRemove() {
        // GIVEN
        final IntMap tree = new IntMap();
        randomPut(tree, random(1000));
        // WHEN
        for (final Integer key : tree.keySet()) {
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
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        // THEN
        assertArrayEquals(keys.toArray(), tree.keySet().toArray());
        assertArrayEquals(keys.toArray(Integer[]::new), tree.keySet().toArray(Integer[]::new));
        // First
        assertEquals(0, tree.firstEntry().getKey());
        assertNotNull(tree.pollFirstEntry());
        assertEquals(1, tree.firstEntry().getKey());
        // Last
        assertEquals(max, tree.lastEntry().getKey());
        assertNotNull(tree.pollLastEntry());
        assertEquals(max - 1, tree.lastEntry().getKey());
    }

    @Test
    void testReversed() {
        // GIVEN
        final int max = 9;
        final List<Integer> keys = random(max + 1);
        final IntMap tree = new IntMap();
        // WHEN
        randomPut(tree, keys);
        final SequencedMap<Integer, String> reversed = tree.reversed();
        // THEN
        assertArrayEquals(keys.reversed().toArray(), reversed.keySet().toArray());
        assertSame(tree, reversed.reversed());
        assertSame(reversed, tree.reversed());
        // First
        assertEquals(0, tree.firstEntry().getKey());
        assertEquals(0, reversed.lastEntry().getKey());
        assertNotNull(tree.pollFirstEntry());
        assertEquals(1, reversed.lastEntry().getKey());
        assertEquals(1, tree.firstEntry().getKey());
        // Last
        assertEquals(max, tree.lastEntry().getKey());
        assertEquals(max, reversed.firstEntry().getKey());
        assertNotNull(tree.pollLastEntry());
        assertEquals(max - 1, reversed.firstEntry().getKey());
        assertEquals(max - 1, tree.lastEntry().getKey());
    }

    @Test
    void testEmpty() {
        // GIVEN
        final IntMap tree = new IntMap();
        // THEN
        assertNull(tree.lastEntry());
        assertNull(tree.firstEntry());
        assertNull(tree.pollFirstEntry());
        assertNull(tree.pollLastEntry());
        assertTrue(tree.isEmpty());
        // GIVEN
        final SequencedMap<Integer, String> reversed = tree.reversed();
        // WHEN
        assertNull(reversed.lastEntry());
        assertNull(reversed.firstEntry());
        assertNull(tree.pollFirstEntry());
        assertNull(tree.pollLastEntry());
        assertTrue(tree.isEmpty());
    }

    private static final class IntMap extends RedBlackMap<Integer, String> {
        public IntMap() {
            super(Integer::compareTo);
        }

        private void assertValid() {
            assertValid(this);
        }

        private int count() {
            var i = 0;
            for (final Integer ignore : this.keySet()) {
                i++;
            }
            return i;
        }
    }
}
