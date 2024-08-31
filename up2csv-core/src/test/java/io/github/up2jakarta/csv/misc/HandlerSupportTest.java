package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.EventHandler;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.exception.MapperException;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.converter.Test1Converter;
import io.github.up2jakarta.csv.test.bean.converter.Test1Resolver;
import io.github.up2jakarta.csv.test.bean.converter.Test1Validator;
import io.github.up2jakarta.csv.test.bean.mapper.ValidBean;
import io.github.up2jakarta.csv.test.bean.processor.Test3Processor;
import io.github.up2jakarta.csv.test.codelist.CurrencyConverter;
import io.github.up2jakarta.csv.test.codelist.MeasurementUnitConverter;
import io.github.up2jakarta.csv.test.ext.Dummy1Processor;
import io.github.up2jakarta.csv.test.ext.DummyConverter;
import io.github.up2jakarta.csv.test.input.InputRowEntity;
import io.github.up2jakarta.csv.test.input.SegmentType;
import io.github.up2jakarta.csv.test.validation.Up2Warn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.csv.extension.SeverityType.ERROR;
import static io.github.up2jakarta.csv.misc.Errors.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class HandlerSupportTest {

    private final MapperFactory factory;

    @Autowired
    HandlerSupportTest(MapperFactory factory) {
        this.factory = factory;
    }

    @Test
    void testNull() throws BeanException {
        // Given
        final Mapper<ValidBean> mapper = factory.build(ValidBean.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "");
        // When
        final EventHandler<?, ?, ?> handler = null;
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
        final Mapper<Test1Validator> mapper = factory.build(Test1Validator.class);
        //noinspection unchecked
        final EventHandler<InputRowEntity, ?, ?> handler = (EventHandler<InputRowEntity, ?, ?>) EventHandler.failFast();
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "+1", "1", "1", "1", "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("size must be between 0 and 1", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "101", "1", "1", "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must be less than or equal to 100", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "", "1", "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be empty", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", null, "1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_VALIDATOR, error.getErrorCode());
            assertEquals(SeverityType.FATAL, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be null", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "-1", "1", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must be greater than 0", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "1", "", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Tests.TU_V_001, error.getErrorCode());
            assertEquals(SeverityType.FATAL, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be empty", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "1", "1", "1", "1", "1", "1", "");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Up2Warn.TU_P_011, error.getErrorCode());
            assertEquals(SeverityType.WARNING, error.getSeverityType());
            assertNotNull(error.getCause());
            assertEquals("must not be empty", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
    }

    /**
     * @see ErrorSupportTest#testResolverWithoutError()
     */
    @Test
    void testResolver() throws BeanException {
        // Given
        final Mapper<Test1Resolver> mapper = factory.build(Test1Resolver.class);
        //noinspection unchecked
        final EventHandler<InputRowEntity, ?, ?> handler = (EventHandler<InputRowEntity, ?, ?>) EventHandler.failFast();
        {
            // When
            final InputRowEntity row = Tests.create(SegmentType.S00, "ISL", "KGM", "PT24H");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_CODE_LIST, error.getErrorCode());
            assertEquals(ERROR, error.getSeverityType());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [ISL] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "XGM", "PT24H");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(MeasurementUnitConverter.EDI_R_20, error.getErrorCode());
            assertEquals(SeverityType.FATAL, error.getSeverityType());
            assertInstanceOf(CodeListException.class, error.getCause());
            assertEquals("Unknown value [XGM] for CodeList[MeasurementUnitCode]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "KGM", "XPT24H");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_CONVERTER, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertNotNull(error.getCause().getCause());
            assertEquals("Text cannot be parsed to a Duration", error.getCause().getCause().getMessage());
        }
    }

    /**
     * @see ErrorSupportTest#testConverterWithoutError()
     */
    @Test
    void testConverter() throws BeanException {
        // Given
        final Mapper<Test1Converter> mapper = factory.build(Test1Converter.class);
        //noinspection unchecked
        final EventHandler<InputRowEntity, ?, ?> handler = (EventHandler<InputRowEntity, ?, ?>) EventHandler.failFast();
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "ILS", "1");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(CurrencyConverter.ISO_4217, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("Unknown value [ILS] for CodeList[CurrencyCodeType]", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "int");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(DummyConverter.TU_P_005, error.getErrorCode());
            assertEquals(SeverityType.ERROR, error.getSeverityType());
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
        final Mapper<Test3Processor> mapper = factory.build(Test3Processor.class);
        //noinspection unchecked
        final EventHandler<InputRowEntity, ?, ?> handler = (EventHandler<InputRowEntity, ?, ?>) EventHandler.failFast();
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "property");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(Dummy1Processor.TU_P_001, error.getErrorCode());
            assertEquals(SeverityType.FATAL, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("property", error.getCause().getMessage());
            assertNull(error.getCause().getCause());
        }
        {
            final InputRowEntity row = Tests.create(SegmentType.S00, "dummy");
            final MapperException error = assertThrows(MapperException.class, () -> mapper.map(row, handler));
            assertEquals(0, handler.toList().size());
            // Then
            assertEquals(ERROR_PROCESSOR, error.getErrorCode());
            assertEquals(ERROR, error.getSeverityType());
            assertInstanceOf(PropertyException.class, error.getCause());
            assertEquals("io.github.up2jakarta.csv.test.ext.DummyException: dummy", error.getCause().getMessage());
            assertNotNull(error.getCause().getCause());
        }
    }

}
