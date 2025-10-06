package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.impl.FastException;
import io.github.up2jakarta.csv.ops.impl.GroupType;
import io.github.up2jakarta.csv.ops.impl.InputErrorEntity;
import io.github.up2jakarta.csv.ops.impl.InputRowEntity;
import io.github.up2jakarta.csv.ops.impl.SegmentType;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.converter.Test1Converter;
import io.github.up2jakarta.csv.test.bean.converter.Test1Resolver;
import io.github.up2jakarta.csv.test.bean.converter.Test1Validator;
import io.github.up2jakarta.csv.test.bean.mapper.ValidBean;
import io.github.up2jakarta.csv.test.bean.processor.Test3Processor;
import io.github.up2jakarta.csv.test.clv.CurrencyConverter;
import io.github.up2jakarta.csv.test.clv.MeasurementUnitConverter;
import io.github.up2jakarta.csv.test.ext.Dummy1Processor;
import io.github.up2jakarta.csv.test.ext.DummyConverter;
import io.github.up2jakarta.csv.test.valid.Up2Warn;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.PropertyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeParseException;

import static io.github.up2jakarta.csv.core.Errors.*;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class FastHandlerTest {

    private final MapperFactory<GroupType> factory;

    @Autowired
    FastHandlerTest(MapperFactory<GroupType> factory) {
        this.factory = factory;
    }

    @Test
    void testNull() throws BeanException {
        // Given
        final Mapper<ValidBean, GroupType> mapper = factory.build(ValidBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "");
        // When
        final EventHandler<InputRowEntity, GroupType, InputErrorEntity> handler = null;
        final NullPointerException npe1 = assertThrows(NullPointerException.class, () -> mapper.map(row, null));
        final NullPointerException npe2 = assertThrows(NullPointerException.class, () -> mapper.map(handler, ""));
        // Then
        assertEquals("handler is required", npe1.getMessage());
        assertEquals("handler is required", npe2.getMessage());
    }

    /**
     * @see ErrorSupportTest#testValidatorWithoutError()
     */
    @Test
    void testValidator() throws BeanException {
        // Given
        final Mapper<Test1Validator, GroupType> mapper = factory.build(Test1Validator.class, GroupType.NONE);
        final EventHandler<InputRowEntity, GroupType, InputErrorEntity> handler = FastHandler.of(WARNING);
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "+1", "1", "1", "1", "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("size must be between 0 and 1", error.getMessage());
            assertEquals(GroupType.NONE, error.getDataType());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "101", "1", "1", "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must be less than or equal to 100", error.getMessage());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "", "1", "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", null, "1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be null", error.getMessage());
            assertNull(error.getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "-1", "1", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must be greater than 0", error.getMessage());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "1", "", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Tests.ERROR_CODE, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "1", "1", "");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertNull(error.getCause());
            assertEquals("must not be empty", error.getMessage());
        }
    }

    /**
     * @see ErrorSupportTest#testResolverWithoutError()
     */
    @Test
    void testResolver() throws BeanException {
        // Given
        final Mapper<Test1Resolver, GroupType> mapper = factory.build(Test1Resolver.class);
        final EventHandler<InputRowEntity, GroupType, InputErrorEntity> handler = FastHandler.of(WARNING);
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "ISL", "KGM", "PT24H");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_CODE_LIST, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [ISL] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "XGM", "PT24H");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [XGM] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "KGM", "XPT24H");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(ERROR_CONVERTER, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(DateTimeParseException.class, error.getCause());
            assertNull(error.getCause().getCause());
            assertEquals("Text cannot be parsed to a Duration", error.getCause().getMessage());
        }
    }

    /**
     * @see ErrorSupportTest#testConverterWithoutError()
     */
    @Test
    void testConverter() throws BeanException {
        // Given
        final Mapper<Test1Converter, GroupType> mapper = factory.build(Test1Converter.class);
        final EventHandler<InputRowEntity, GroupType, InputErrorEntity> handler = FastHandler.of(WARNING);
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "ILS", "1");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(CurrencyConverter.ISO_4217, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "int");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(DummyConverter.TU_P_005, error.getCode());
            assertEquals(ERROR, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("java.lang.NumberFormatException: For input string: \"int\"", error.getCause().getMessage());
            assertNotNull(error.getCause().getCause());
        }
    }

    /**
     * @see ErrorSupportTest#testProcessorWithoutError()
     */
    @Test
    void testProcessor() throws BeanException {
        // Given
        final Mapper<Test3Processor, GroupType> mapper = factory.build(Test3Processor.class);
        final EventHandler<InputRowEntity, GroupType, InputErrorEntity> handler = FastHandler.of(WARNING);
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "property");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("property", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "dummy");
            final FastException error = assertThrows(FastException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toCollection().size());
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getCode());
            assertEquals(WARNING, error.getSeverity());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("io.github.up2jakarta.csv.test.ext.DummyException: dummy", error.getCause().getMessage());
            assertNotNull(error.getCause().getCause());
        }
    }

}
