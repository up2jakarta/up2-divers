package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.CSV;
import io.github.up2jakarta.csv.test.persistence.InputErrorEntity;
import io.github.up2jakarta.csv.test.persistence.InputRowEntity;
import io.github.up2jakarta.csv.test.persistence.SimpleErrorEntity;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiversTest {

    @Test
    void testTrim() {
        // GIVEN
        final String[] data = {null, "", " \t\n", "- \t\n", "\n\t - \t\n", "\n\t DATA \t\n", "DA - TA"};
        // WHEN
        final String[] trim = CSV.trim(data, "", "-");
        // THEN
        assertEquals(data.length, trim.length);
        for (var i = 0; i < 5; i++) {
            assertNull(trim[i]);
        }
        assertEquals("DATA", trim[5]);
        assertEquals("DA - TA", trim[6]);
    }

    @Test
    @SuppressWarnings("ALL")
    void testTrimNull() {
        // WHEN
        final String[] trim = CSV.trim(null);
        // THEN
        assertNull(trim);
    }

    @Test
    void testLazyList() {
        // GIVEN
        final LazyList<InputRowEntity, SimpleErrorEntity> lazyList = new LazyList<>(() -> 3);
        final SimpleErrorEntity error = new SimpleErrorEntity();
        // WHEN
        lazyList.addWithOrder(null);
        assertEquals(0, lazyList.toList().size());
        // WHEN
        lazyList.addWithOrder(error);
        // THEN
        {
            final List<SimpleErrorEntity> errors = lazyList.toList();
            assertEquals(1, errors.size());
            final SimpleErrorEntity rowError = errors.get(0);
            assertSame(error, rowError);
            assertEquals(3, rowError.getKey().getOrder());
        }
        // WHEN
        final List<SimpleErrorEntity> target = new LinkedList<>();
        lazyList.addWithOrder(error); // Duplication
        lazyList.addTo(target);
        // THEN
        {
            assertEquals(2, target.size());
            var rowError = target.get(0);
            assertEquals(4, rowError.getKey().getOrder());
            rowError = target.get(1);
            assertSame(error, rowError);
            assertEquals(4, rowError.getKey().getOrder());
        }
    }

    @Test
    void testLazyListKey() {
        // GIVEN
        final LazyList<InputRowEntity, InputErrorEntity> lazyList = new LazyList<>(() -> 3);
        final InputErrorEntity error = new InputErrorEntity();
        // WHEN null
        lazyList.addWithOrder(null);
        assertEquals(0, lazyList.toList().size());
        // WHEN Key null
        lazyList.addWithOrder(error);
        // THEN
        final List<InputErrorEntity> errors = lazyList.toList();
        assertEquals(1, errors.size());
        final InputErrorEntity rowError = errors.get(0);
        assertSame(error, rowError);
        assertNull(rowError.getKey());
    }


}
