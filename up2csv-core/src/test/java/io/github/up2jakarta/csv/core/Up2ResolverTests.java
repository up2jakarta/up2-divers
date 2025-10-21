package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.core.misc.cvr.Test1Definition;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.fmt.hdl.FastException;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_CODE_LIST;
import static io.github.up2jakarta.csv.core.EventHandler.ERROR_CONVERTER;
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
        final FastException error = assertThrows(FastException.class, () -> mapper.map("TON", "PT24H"));
        // Then
        assertNotNull(error.getDataType());
        assertNotNull(error.getDataType().getName());
        assertEquals(Test1Definition.D01, error.getDataType().getCode());
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
        final FastException error = assertThrows(FastException.class, () -> mapper.map("KGM", "24H"));
        // Then
        assertNotNull(error.getDataType());
        assertNotNull(error.getDataType().getName());
        assertEquals(Test1Definition.D02, error.getDataType().getCode());
        assertEquals(1, error.getOffset());
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(ERROR_CONVERTER, error.getCode());
        assertNotNull(error.getCause());
        assertEquals("Text cannot be parsed to a Duration", error.getCause().getMessage());
    }

}
