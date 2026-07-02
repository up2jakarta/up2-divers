package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.data.HeaderType;
import io.github.up2jakarta.csv.data.TermResolver;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.cvr.Test3Resolver;
import io.github.up2jakarta.test.core.misc.cvr.TestHeader;
import io.github.up2jakarta.test.core.misc.lov.EnumLike;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.csv.api.IEvent.EC_CONVERTER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2ResolverTests {

    private final Up2Factory<HeaderType> factory;

    @Autowired
    Up2ResolverTests(Container context) {
        this.factory = new Up2Factory<>(context, TermResolver.header());
    }

    @Test
    void test1() throws BeanException {
        // Given
        final Up2Mapper<TestHeader, HeaderType> mapper = factory.mapper(TestHeader.class);
        final FailureException error = assertThrows(FailureException.class, () -> mapper.map("TON", "PT24H"));
        // Then
        assertNotNull(error.getType());
        assertNotNull(error.getType().getName());
        assertEquals(TestHeader.D01, error.getType().getCode());
        assertEquals(0, error.getOffset());
        assertEquals(SeverityType.ERROR, error.getLevel());
        assertEquals(EC_CODE_LIST, error.getCode());
        assertNotNull(error.getCause());
        assertEquals("Unknown input [TON] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
    }

    @Test
    void test2() throws BeanException {
        // Given
        final Up2Mapper<TestHeader, HeaderType> mapper = factory.mapper(TestHeader.class);
        final FailureException error = assertThrows(FailureException.class, () -> mapper.map("KGM", "24H"));
        // Then
        assertNotNull(error.getType());
        assertNotNull(error.getType().getName());
        assertEquals(TestHeader.D02, error.getType().getCode());
        assertEquals(1, error.getOffset());
        assertEquals(SeverityType.ERROR, error.getLevel());
        assertEquals(EC_CONVERTER, error.getCode());
        assertNotNull(error.getCause());
        assertEquals("Text cannot be parsed to a Duration", error.getCause().getMessage());
    }

    @Test
    void testDeprecatedConstant() throws BeanException {
        // Given
        final Up2Mapper<Test3Resolver, HeaderType> mapper = factory.mapper(Test3Resolver.class);
        final FailureException error = assertThrows(FailureException.class, () -> mapper.map("N"));
        // Then
        assertEquals(SeverityType.ERROR, error.getLevel());
        assertEquals(EC_CODE_LIST, error.getCode());
        assertNotNull(error.getCause());
        assertEquals("Unknown input [N] for CodeList[UnitType]", error.getCause().getMessage());
    }

    @Test
    void testValidConstant() throws BeanException {
        // Given
        final Up2Mapper<Test3Resolver, HeaderType> mapper = factory.mapper(Test3Resolver.class);
        final Test3Resolver bean = mapper.map("1");
        // Then
        assertNotNull(bean);
        assertEquals(EnumLike.ONE, bean.getUnit());
    }

}
