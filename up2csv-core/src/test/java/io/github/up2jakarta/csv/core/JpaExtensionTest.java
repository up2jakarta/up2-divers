package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.EventHandler.SimpleHandler;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.input.InputRepository;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.jpa.*;
import io.github.up2jakarta.csv.test.codelist.TestCodeList;
import io.github.up2jakarta.csv.test.codelist.TestCodeListConverter;
import io.github.up2jakarta.csv.test.input.InputRowEntity;
import io.github.up2jakarta.csv.test.input.SegmentType;
import io.github.up2jakarta.csv.test.input.SimpleErrorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class JpaExtensionTest {

    private final InputRepository<InputRowEntity> repository = (r -> 0);
    private final SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> creator;
    private final MapperFactory factory;

    @Autowired
    JpaExtensionTest(MapperFactory factory, SimpleKeyCreator<InputRowEntity, SimpleErrorEntity> creator) {
        this.factory = factory;
        this.creator = creator;
    }

    @Test
    void testValidBean() throws BeanException {
        // Given
        final Mapper<Test1Bean> mapper = factory.build(Test1Bean.class);
        // When
        final Test1Bean bean = mapper.map("ONE", "TWO", "0", "0", "*");
        // Then
        assertNotNull(bean);
        assertEquals(XML1Enum.ONE, bean.getEnum1());
        assertEquals(XML2Enum.TWO, bean.getEnum2());
        assertEquals(XML1Enum.ONE, bean.getEnum3());
        assertEquals(XML2Enum.TWO, bean.getEnum4());
        assertEquals(TestCodeList.ANY, bean.getAdapter());
    }

    @Test
    void testDefaultErrors() throws BeanException {
        // Given
        final Mapper<Test1Bean> mapper = factory.build(Test1Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "11", "22", "33", "44", "ANY");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
        final Test1Bean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(5, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test1Bean.JPA_XXX, error.getCode());
            assertEquals("Unknown value [11] for @Enumerated[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(Errors.ERROR_XML_ENUM, error.getCode());
            assertEquals("Unknown value [22] for @Enumerated[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }

        {
            final SimpleErrorEntity error = errors.get(2);
            assertSame(row, error.getRow());
            assertEquals(2, error.getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test1Bean.JPA_XXX, error.getCode());
            assertEquals("Unknown value [33] for @Enumerated[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(3);
            assertSame(row, error.getRow());
            assertEquals(3, error.getOrder());
            assertEquals(3, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(Errors.ERROR_XML_ENUM, error.getCode());
            assertEquals("Unknown value [44] for @Enumerated[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(4);
            assertSame(row, error.getRow());
            assertEquals(4, error.getOrder());
            assertEquals(4, error.getOffset());
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(TestCodeListConverter.TU_001, error.getCode());
            assertEquals("Unknown value [ANY] for CodeList[TestCodeList]", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testOverrideErrors() throws BeanException {
        // Given
        final Mapper<Test2Bean> mapper = factory.build(Test2Bean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "11", "22", "ANY");
        // When
        final SimpleHandler<InputRowEntity, SimpleErrorEntity> handler = EventHandler.of(row, creator, repository);
        final Test2Bean bean = mapper.map(row, handler);
        final List<SimpleErrorEntity> errors = handler.toList();
        // Then
        assertNotNull(errors);
        assertNotNull(bean);
        assertEquals(3, errors.size());
        {
            final SimpleErrorEntity error = errors.get(0);
            assertSame(row, error.getRow());
            assertEquals(0, error.getOrder());
            assertEquals(0, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Test2Bean.XML_001, error.getCode());
            assertEquals("Unknown value [11] for @Enumerated[XML1Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(1);
            assertSame(row, error.getRow());
            assertEquals(1, error.getOrder());
            assertEquals(1, error.getOffset());
            assertEquals(SeverityType.WARNING, error.getSeverity());
            assertEquals(Test2Bean.XML_002, error.getCode());
            assertEquals("Unknown value [22] for @Enumerated[XML2Enum]", error.getMessage());
            assertNull(error.getTrace());
        }
        {
            final SimpleErrorEntity error = errors.get(2);
            assertSame(row, error.getRow());
            assertEquals(2, error.getOrder());
            assertEquals(2, error.getOffset());
            assertEquals(SeverityType.FATAL, error.getSeverity());
            assertEquals(Test2Bean.XML_003, error.getCode());
            assertEquals("Unknown value [ANY] for CodeList[TestCodeList]", error.getMessage());
            assertNull(error.getTrace());
        }
    }

    @Test
    void testActivation() {
        // Given
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> factory.build(Test3Bean.class));
        // THEN
        assertEquals(Test3Bean.class, thrown.getBeanType());
        assertEquals("enum1", thrown.getAttribute());
        assertEquals("Test3Bean[enum1] - must be annotated with @Converter or one of its shortcuts", thrown.getMessage());
    }

}
