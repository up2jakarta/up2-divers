package io.github.up2jakarta.test.core;


import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.jpa.Test1Bean;
import io.github.up2jakarta.test.core.misc.jpa.Test2Bean;
import io.github.up2jakarta.test.core.misc.jpa.XML1Enum;
import io.github.up2jakarta.test.core.misc.jpa.XML2Enum;
import io.github.up2jakarta.test.core.misc.jpa.checker.*;
import io.github.up2jakarta.test.core.misc.lov.TestCodeList;
import io.github.up2jakarta.test.core.misc.lov.TestCodeListConverter;
import io.github.up2jakarta.test.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_JPA_ENUM;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.fmt.misc.Tests.record;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class Up2JpaExtensionTest {

    private final Up2Factory<TermType> factory;

    @Autowired
    Up2JpaExtensionTest(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    @Test
    void testValidBean() throws BeanException {
        // Given
        final Up2Mapper<Test1Bean, TermType> mapper = factory.mapper(Test1Bean.class);
        final String[] data = {"ONE", "TWO", "0", "0", "*"};
        // When
        final Test1Bean bean = mapper.map(data);
        // Then
        assertNotNull(bean);
        assertEquals(XML1Enum.ONE, bean.getEnum1());
        assertEquals(XML2Enum.TWO, bean.getEnum2());
        assertEquals(XML1Enum.ONE, bean.getEnum3());
        assertEquals(XML2Enum.TWO, bean.getEnum4());
        assertEquals(TestCodeList.ANY, bean.getAdapter());
        // When Unmapping
        final Up2Flatter<Test1Bean, TermType> format = factory.flatter(Test1Bean.class);
        final String[] out = format.unmap(bean);
        // Then
        assertNotNull(out);
        assertEquals(data.length, out.length);
        for (var i = 0; i < data.length; i++) {
            assertEquals(data[i], out[i]);
        }
    }

    @Test
    void testDefaultErrors() throws BeanException {
        // Given
        final Up2Mapper<Test1Bean, TermType> mapper = factory.mapper(Test1Bean.class);
        final InputRecord row = record(SegmentType.S00, "11", "22", "33", "44", "ANY");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test1Bean bean = mapper.map(row, handler);
        final List<InputError> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(5, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals(Test1Bean.JPA_XXX, error.getCode());
            assertEquals("Unknown input [11] for @Enumerated[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_JPA_ENUM, error.getCode());
            assertEquals("Unknown input [22] for @Enumerated[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }

        {
            final InputError error = errors.get(2);
            assertSame(row, error.getKey().getRecord());
            assertEquals(2, error.getKey().getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals(Test1Bean.JPA_XXX, error.getCode());
            assertEquals("Unknown input [33] for @Enumerated[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(3);
            assertSame(row, error.getKey().getRecord());
            assertEquals(3, error.getKey().getOrder());
            assertEquals(3, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(EC_JPA_ENUM, error.getCode());
            assertEquals("Unknown input [44] for @Enumerated[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(4);
            assertSame(row, error.getKey().getRecord());
            assertEquals(4, error.getKey().getOrder());
            assertEquals(4, error.getOffset());
            assertEquals(ERROR, error.getLevel());
            assertEquals(TestCodeListConverter.TU_001, error.getCode());
            assertEquals("Unknown input [ANY] for CodeList[TestCodeList]", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testOverrideErrors() throws BeanException {
        // Given
        final Up2Mapper<Test2Bean, TermType> mapper = factory.mapper(Test2Bean.class);
        final InputRecord row = record(SegmentType.S00, "11", "22", "ANY");
        // When
        final InputCollector handler = new InputCollector(row);
        final Test2Bean bean = mapper.map(row, handler);
        final List<InputError> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(3, errors.size());
        {
            final InputError error = errors.getFirst();
            assertSame(row, error.getKey().getRecord());
            assertEquals(0, error.getKey().getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals(Test2Bean.XML_001, error.getCode());
            assertEquals("Unknown input [11] for @Enumerated[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(1);
            assertSame(row, error.getKey().getRecord());
            assertEquals(1, error.getKey().getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals(Test2Bean.XML_002, error.getCode());
            assertEquals("Unknown input [22] for @Enumerated[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final InputError error = errors.get(2);
            assertSame(row, error.getKey().getRecord());
            assertEquals(2, error.getKey().getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(WARNING, error.getLevel());
            assertEquals(Test2Bean.XML_003, error.getCode());
            assertEquals("Unknown input [ANY] for CodeList[TestCodeList]", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void test1UniqueOffset() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test1Offset.class));
        // THEN
        assertEquals(Test1Offset.class, error.getSource());
        assertEquals("value", error.getLocator());
        assertEquals("@Position[value] must be unique", error.getMessage());
    }

    @Test
    void test2UniqueOffset() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test2Offset.class));
        // THEN
        assertEquals(Test2Offset.class, error.getSource());
        assertEquals("class", error.getLocator());
        assertEquals("must not have gap on @Position[value]: 1", error.getMessage());
    }

    @Test
    void test3UniqueOffset() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test3Offset.class));
        // THEN
        assertEquals(Test3Offset.class, error.getSource());
        assertEquals("fragment", error.getLocator());
        assertEquals("must not have gap on @Position[value]: 1, 3", error.getMessage());
    }

    @Test
    void test4UniqueOffset() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test4Offset.class));
        // THEN
        assertEquals(Test4Offset.class, error.getSource());
        assertEquals("fragment", error.getLocator());
        assertEquals("must not have gap on @Position[value]: 1, 3", error.getMessage());
    }

    @Test
    void test5UniqueOffset() {
        // GIVEN
        final BeanException error = assertThrows(BeanException.class, () -> factory.mapper(Test5Offset.class));
        // THEN
        assertEquals(Test5Offset.class, error.getSource());
        assertEquals("fragment", error.getLocator());
        assertEquals("must not have gap on @Position[value]: 1", error.getMessage());
    }

}
