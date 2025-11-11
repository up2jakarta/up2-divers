package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.misc.cvr.Test1Definition;
import io.github.up2jakarta.csv.core.misc.cvr.Test3Resolver;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_CODE_LIST;
import static io.github.up2jakarta.csv.api.IEvent.ERROR_CONVERTER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class Up2ResolverTests {

    private final Up2Factory<DynamicType> factory;

    @Autowired
    Up2ResolverTests(BeanContext context, Validator validator) {
        this.factory = new Up2Factory<>(context, validator, DataTypeResolver.dynamic());
    }

    @Test
    void test1() throws BeanException {
        // Given
        final Up2Mapper<Test1Definition, DynamicType> mapper = factory.build(Test1Definition.class);
        final FailureException error = assertThrows(FailureException.class, () -> mapper.map("TON", "PT24H"));
        // Then
        assertNotNull(error.getType());
        assertNotNull(error.getType().getName());
        assertEquals(Test1Definition.D01, error.getType().getCode());
        assertEquals(0, error.getOffset());
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(ERROR_CODE_LIST, error.getCode());
        assertNotNull(error.getCause());
        assertEquals("Unknown value [TON] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
    }

    @Test
    void test2() throws BeanException {
        // Given
        final Up2Mapper<Test1Definition, DynamicType> mapper = factory.build(Test1Definition.class);
        final FailureException error = assertThrows(FailureException.class, () -> mapper.map("KGM", "24H"));
        // Then
        assertNotNull(error.getType());
        assertNotNull(error.getType().getName());
        assertEquals(Test1Definition.D02, error.getType().getCode());
        assertEquals(1, error.getOffset());
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(ERROR_CONVERTER, error.getCode());
        assertNotNull(error.getCause());
        assertEquals("Text cannot be parsed to a Duration", error.getCause().getMessage());
    }

    @Test
    void testPrivateConstant() throws BeanException {
        // Given
        final Up2Mapper<Test3Resolver, DynamicType> mapper = factory.build(Test3Resolver.class);
        final FailureException error = assertThrows(FailureException.class, () -> mapper.map("N"));
        // Then
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(ERROR_CODE_LIST, error.getCode());
        assertNotNull(error.getCause());
        assertEquals("Unknown value [N] for CodeList[EnumLike]", error.getCause().getMessage());
    }

    @Test
    void testPublicConstant() throws BeanException {
        // Given
        final Up2Mapper<Test3Resolver, DynamicType> mapper = factory.build(Test3Resolver.class);
        final Test3Resolver bean = mapper.map("1");
        // Then
        assertNotNull(bean);
        assertEquals(Test3Resolver.EnumLike.ONE, bean.getUnit());
    }

}
